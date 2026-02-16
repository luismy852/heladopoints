package com.luisdev.heladopoints.controller;


import com.luisdev.heladopoints.dto.UserLoginDTO;
import com.luisdev.heladopoints.dto.UserRegistrationDTO;
import com.luisdev.heladopoints.model.User;
import com.luisdev.heladopoints.dto.JwtTokenDTO;
import com.luisdev.heladopoints.infra.security.TokenService;
import com.luisdev.heladopoints.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class AutenticacionController {

    private final UserService userService;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    public AutenticacionController(UserService userService, TokenService tokenService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }


    @PostMapping("/login")
    public ResponseEntity autenticarUsuario(@RequestBody UserLoginDTO userLoginDTO) {
        Authentication authToken = new UsernamePasswordAuthenticationToken(userLoginDTO.email(),
                userLoginDTO.password());
        var authenticatedUser = authenticationManager.authenticate(authToken);
        User usuario = (User) authenticatedUser.getPrincipal();
        var JWTtoken = tokenService.generateToken((User) authenticatedUser.getPrincipal());
        return ResponseEntity.ok(new JwtTokenDTO(JWTtoken, usuario.getId()));
    }

    @PostMapping("/register")
    public ResponseEntity createUser(@RequestBody UserRegistrationDTO register) throws IllegalAccessException {
         User user = userService.createUser(register);
        Authentication authToken = new UsernamePasswordAuthenticationToken(register.email(),
                register.password());
        var authenticatedUser = authenticationManager.authenticate(authToken);
        User user1 = (User) authenticatedUser.getPrincipal();
        var JWTtoken = tokenService.generateToken((User) authenticatedUser.getPrincipal());
        return ResponseEntity.ok(new JwtTokenDTO(JWTtoken, user1.getId()));
    }


}