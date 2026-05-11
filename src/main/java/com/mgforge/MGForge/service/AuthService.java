package com.mgforge.MGForge.service;

import com.mgforge.MGForge.auth.JwtService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthService {

    private final JwtService jwtService;

    public AuthService(JwtService jwtService){
        this.jwtService = jwtService;
    }

    public Map<String,String> login(String email,String password, boolean adminPortal){

    }
}
