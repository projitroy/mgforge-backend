package com.mgforge.MGForge.service;

import com.mgforge.MGForge.auth.JwtService;
import com.mgforge.MGForge.entity.UserEntity;
import com.mgforge.MGForge.enums.UserStatus;
import com.mgforge.MGForge.exception.AccountDisabledException;
import com.mgforge.MGForge.exception.AdminPortalAccessDeniedException;
import com.mgforge.MGForge.exception.InvalidCredentialsException;
import com.mgforge.MGForge.repository.UserRepository;
import com.mgforge.MGForge.repository.UserRoleRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    public AuthService(UserRepository userRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder, JwtService jwtService, RefreshTokenService refreshTokenService) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
    }

    public RefreshTokenService.RefreshTokenPair login(String mobile, String password, boolean adminPortal){
        UserEntity user = userRepository.findByMobile(mobile)
                .orElseThrow(()->new InvalidCredentialsException("Invalid Credentials"));

        if(!(UserStatus.ACTIVE.equals(user.getStatus()))){
            throw new AccountDisabledException("User account is disabled");
        }

        if(!passwordEncoder.matches(password, user.getPasswordHash())){
            throw new InvalidCredentialsException("Invalid Credentials");
        }

        List<String> roles = userRoleRepository.findRolesByUserId(user.getId());

        if(roles.isEmpty()){
            throw new RuntimeException("User has no roles assign");
        }

        if (adminPortal) {
            boolean allowed = roles.stream().anyMatch(r->
                    r.equals("SUPERADMIN") ||
                    r.equals("TENANT_ADMIN") ||
                    r.equals("ADMIN") ||
                    r.equals("COACH")
            );

            if(!allowed){
                throw new AdminPortalAccessDeniedException("Not allowed to access admin portal");
            }
        }

        String clientId = adminPortal ? "admin-web" : "user-web";

        return refreshTokenService.issueNewPair(user.getId(),user.getTenantId(),clientId);
    }
}
