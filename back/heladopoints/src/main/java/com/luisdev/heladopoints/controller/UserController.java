package com.luisdev.heladopoints.controller;

import com.luisdev.heladopoints.infra.security.TokenService;
import com.luisdev.heladopoints.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final UserService userService;
    private final TokenService tokenService;

    public UserController(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @GetMapping("/user")
    public ResponseEntity userInfo(@RequestHeader("Authorization") String token){
        String jwt = token.replace("Bearer ", "");
        String email = tokenService.getSubject(jwt);
        return ResponseEntity.ok(userService.getUserInformation(email));
    }
}
