package com.onegateafrica.controller;

import com.onegateafrica.Entity.UserEntity;
import com.onegateafrica.Utile.JwtUtil;
import com.onegateafrica.dto.UserTokenDto;
import com.onegateafrica.security.auth.AuthenticationRequest;
import com.onegateafrica.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthenticateController {

    @Autowired
    private final UserService userService;
    @Autowired
    private final JwtUtil jwtUtil;

    @Autowired
    private final AuthenticationManager authenticationManager;

    public AuthenticateController(UserService userService, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }


    @PostMapping("/authenticate")
    public ResponseEntity<UserTokenDto> generateToken(@RequestBody AuthenticationRequest request) throws Exception{


            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));


        UserEntity user = userService.getUserByUserName(request.getUsername());

        System.out.println(user);
        return new ResponseEntity(new UserTokenDto(jwtUtil.generateToken(request.getUsername()),user),HttpStatus.OK)  ;
    }
}
