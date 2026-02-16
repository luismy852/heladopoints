package com.luisdev.heladopoints.service;

import com.luisdev.heladopoints.dto.ScanDTO;
import com.luisdev.heladopoints.model.Scan;
import com.luisdev.heladopoints.model.User;
import com.luisdev.heladopoints.repository.ScanRepository;
import com.luisdev.heladopoints.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.List;

@Service
public class ScanService {

    private final ScanRepository scanRepository;
    private final UserRepository userRepository;

    public ScanService(ScanRepository scanRepository, UserRepository userRepository) {
        this.scanRepository = scanRepository;
        this.userRepository = userRepository;
    }

    public void registerTry(User user, boolean success, String details){
        Scan scan = new Scan(success, details, user);
        scanRepository.save(scan);
    }

    public List<ScanDTO> getHistory(String email){
        User user = userRepository.findOptionalByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        List<Scan> scans = scanRepository.findByUserId(user.getId());
        List<ScanDTO> scanDTOS = scans.stream().map(s-> new ScanDTO(
                s.getId(),
                s.getDate(),
                s.isSuccess()
        )).toList();
        return scanDTOS;
    }
}
