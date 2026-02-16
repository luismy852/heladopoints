package com.luisdev.heladopoints.service;


import com.luisdev.heladopoints.dto.UserInfoDTO;
import com.luisdev.heladopoints.dto.UserRegistrationDTO;
import com.luisdev.heladopoints.exception.EmailAlreadyExistsException;
import com.luisdev.heladopoints.exception.UserNotFoundException;
import com.luisdev.heladopoints.model.User;
import com.luisdev.heladopoints.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    public UserService(UserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    public User findById(Long id){
        return repository.findById(id)
                .orElseThrow(()-> new UserNotFoundException("User not found with ID: " + id));
    }

    public String encrypt(String clave){
        return encoder.encode(clave);
    }

    public User findByEmail(String email){
        return repository.findOptionalByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with Email: " + email));
    }

    public User createUser(UserRegistrationDTO register) {
        boolean exists = repository.existsByEmail(register.email());
        if (exists){
            throw new EmailAlreadyExistsException("Email is already registered");
        }
        User user = new User(register, encrypt(register.password()));
        repository.save(user);
        return user;
    }

    @Transactional
    public void addPoints (User user){
        user.setPoints(user.getPoints() + 100);
        repository.save(user);
        log.info("Points added to user: {}", user.getEmail());
    }

    public UserInfoDTO getUserInformation(String email){
        User user = findByEmail(email);
        UserInfoDTO userInfoDTO = new UserInfoDTO(user.getPoints(), user.getName());
        return userInfoDTO;
    }

}


