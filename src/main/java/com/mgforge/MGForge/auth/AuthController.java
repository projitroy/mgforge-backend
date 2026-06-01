package com.mgforge.MGForge.auth;

import com.mgforge.MGForge.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/auth/login")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, String> loginUser(@RequestBody LoginRequest req){
        return authService.login(req.mobile(),req.password(),false);
    }

    @PostMapping("/admin/auth/login")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, String> loginAdmin(@RequestBody LoginRequest req) {
        return authService.login(req.mobile(), req.password(), true);
    }

    public record LoginRequest(String mobile, String password) {}
}
