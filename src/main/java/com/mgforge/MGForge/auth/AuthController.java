package com.mgforge.MGForge.auth;

import com.mgforge.MGForge.service.AuthService;
import com.mgforge.MGForge.service.RefreshTokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.Arrays;
import java.util.Map;

@RestController
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    public AuthController(AuthService authService, RefreshTokenService refreshTokenService) {
        this.authService = authService;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/auth/login")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, String> loginUser(@RequestBody LoginRequest req, HttpServletResponse res){
        RefreshTokenService.RefreshTokenPair pair = authService.login(req.mobile(),req.password(),false);

        setAccessCookie(res, pair.getAccessToken());
        setRefreshCookie(res, pair.getRefreshToken());

        return Map.of("message","Login successful");
    }

    @PostMapping("/admin/auth/login")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, String> loginAdmin(@RequestBody LoginRequest req,HttpServletResponse res) {
        RefreshTokenService.RefreshTokenPair pair = authService.login(req.mobile(), req.password(), true);

        setAccessCookie(res, pair.getAccessToken());
        setRefreshCookie(res, pair.getRefreshToken());

        return Map.of("message","Admin login successful");
    }

    @PostMapping("/auth/refresh")
    public Map<String,String> refresh(HttpServletRequest request,HttpServletResponse response){
        String rawRefreshToken = extractCookieValue(request, "refresh_token");

        RefreshTokenService.RefreshTokenPair pair = refreshTokenService.rotate(rawRefreshToken);

        setAccessCookie(response, pair.getAccessToken());
        setRefreshCookie(response,pair.getRefreshToken());

        return Map.of("message","Token refreshed");
    }

    @PostMapping("/auth/logout")
    public Map<String,String> logout(HttpServletRequest request,HttpServletResponse response){
        String rawRefreshToken = extractCookieValue(request, "refresh_token");
        if(rawRefreshToken != null){
            refreshTokenService.revoke(rawRefreshToken);
        }

        clearCookie(response,"access_token","/");
        clearCookie(response, "refresh_token", "/auth");

        return Map.of("message", "Logged out");
    }


    public record LoginRequest(String mobile, String password) {}

    private void setAccessCookie(HttpServletResponse response, String accessToken){
        ResponseCookie cookie = ResponseCookie.from("access_token", accessToken)
                .httpOnly(true)
                .secure(false)
                .sameSite("Lax")
                .path("/")
                .maxAge(Duration.ofMinutes(15))
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE,cookie.toString());
    }

    private void setRefreshCookie(HttpServletResponse response,String refreshToken) {
        ResponseCookie cookie = ResponseCookie.from("refresh_token",refreshToken)
                .httpOnly(true)
                .secure(false)
                .sameSite("Lax")
                .path("/auth")
                .maxAge(Duration.ofDays(7))
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE,cookie.toString());
    }

    private void clearCookie(HttpServletResponse response,String name,String path){
        ResponseCookie cookie = ResponseCookie.from(name, "")
                .httpOnly(true)
                .secure(false)
                .sameSite("Lax")
                .path(path)
                .maxAge(0)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE,cookie.toString());
    }

    private String extractCookieValue(HttpServletRequest request,String cookieName){
        if(request.getCookies() == null)
            return null;

        Cookie cookie = Arrays.stream(request.getCookies())
                .filter(c -> cookieName.equals(c.getName()))
                .findFirst()
                .orElse(null);

        return cookie == null ? null : cookie.getValue();
    }

}
