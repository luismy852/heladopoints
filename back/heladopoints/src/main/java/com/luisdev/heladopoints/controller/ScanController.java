package com.luisdev.heladopoints.controller;

import com.luisdev.heladopoints.infra.security.TokenService;
import com.luisdev.heladopoints.service.PointService;
import com.luisdev.heladopoints.service.ScanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/scan")
public class ScanController {

    private final TokenService tokenService;
    private final PointService pointService;
    private final ScanService scanService;

    public ScanController(TokenService tokenService, PointService pointService, ScanService scanService) {
        this.tokenService = tokenService;
        this.pointService = pointService;
        this.scanService = scanService;
    }

    @PostMapping
    public ResponseEntity hola(@RequestHeader("Authorization") String token, @RequestParam MultipartFile file) throws IOException {
        String jwt = token.replace("Bearer ", "");
        String email = tokenService.getSubject(jwt);
        pointService.checkPoints(email, file);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity getHistory(@RequestHeader("Authorization") String token){
        String jwt = token.replace("Bearer ", "");
        String email = tokenService.getSubject(jwt);
        return ResponseEntity.ok(scanService.getHistory(email));
    }

}
