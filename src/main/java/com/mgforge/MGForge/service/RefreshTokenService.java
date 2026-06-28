package com.mgforge.MGForge.service;

import com.mgforge.MGForge.auth.JwtService;
import com.mgforge.MGForge.entity.RefreshTokenEntity;
import com.mgforge.MGForge.entity.UserRoleEntity;
import com.mgforge.MGForge.exception.InvalidCredentialsException;
import com.mgforge.MGForge.repository.RefreshTokenRepository;
import com.mgforge.MGForge.repository.UserRoleRepository;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.OffsetDateTime;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRoleRepository userRoleRepository;
    private final JwtService jwtService;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, UserRoleRepository userRoleRepository, JwtService jwtService) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRoleRepository = userRoleRepository;
        this.jwtService = jwtService;
    }

    public RefreshTokenPair issueNewPair(UUID userId, UUID tenantId, String clientId){
        String rawRefreshToken = generateOpaqueRefreshToken();
        String refreshHash = sha256(rawRefreshToken);

        RefreshTokenEntity refreshTokenEntity = new RefreshTokenEntity();
        refreshTokenEntity.setUserId(userId);
        refreshTokenEntity.setTenantId(tenantId);
        refreshTokenEntity.setTokenHash(refreshHash);
        refreshTokenEntity.setExpiresAt(OffsetDateTime.now().plusDays(15));
        refreshTokenEntity.setClientId(clientId);

        refreshTokenRepository.save(refreshTokenEntity);

        List<String> roles = userRoleRepository.findRolesByUserId(userId);

        String accessToken = jwtService.createAccessToken(
                userId.toString(),
                tenantId == null ? null : tenantId.toString(),
                roles
        );

        return new RefreshTokenPair(accessToken, rawRefreshToken);
    }

    public RefreshTokenPair rotate(String rawRefreshToken){
        String currentHash = sha256(rawRefreshToken);

        RefreshTokenEntity existing = refreshTokenRepository.findByTokenHash(currentHash)
                .orElseThrow(()->new InvalidCredentialsException("Invalid refresh token"));

        if(!existing.isActive()){
            throw new InvalidCredentialsException("Refresh token expired or revoked");
        }

        String newRawRefreshToken = generateOpaqueRefreshToken();
        String newHash = sha256(newRawRefreshToken);

        existing.setRevokedAt(OffsetDateTime.now());
        existing.setReplacedByHash(newHash);

        refreshTokenRepository.save(existing);

        RefreshTokenEntity replacement = new RefreshTokenEntity();
        replacement.setUserId(existing.getUserId());
        replacement.setTenantId(existing.getTenantId());
        replacement.setTokenHash(newHash);
        replacement.setExpiresAt(OffsetDateTime.now().plusDays(7));
        replacement.setClientId(existing.getClientId());
        refreshTokenRepository.save(replacement);

        List<String> roles = userRoleRepository.findRolesByUserId(existing.getUserId());

        String newAccessToken = jwtService.createAccessToken(
                existing.getUserId().toString(),
                existing.getTenantId() == null ? null : existing.getTenantId().toString(),
                roles
        );

        return new RefreshTokenPair(newAccessToken,newRawRefreshToken);
    }

    public void revoke(String rawRefreshToken) {
        String hash = sha256(rawRefreshToken);
        refreshTokenRepository.findByTokenHash(hash).ifPresent(entity -> {
            entity.setRevokedAt(OffsetDateTime.now());
            refreshTokenRepository.save(entity);
        });
    }

    private String generateOpaqueRefreshToken(){
        byte[] random = new byte[64];
        new java.security.SecureRandom().nextBytes(random);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(random);
        // opaque random token, not JWT
    }

    private String sha256(String inout){
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashed = digest.digest(inout.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hashed);
        } catch (Exception ex){
            throw new RuntimeException("Unable to hash token", ex);
        }
    }

    public static class RefreshTokenPair{
        private final String accessToken;
        private final String refreshToken;

        public RefreshTokenPair(String accessToken, String refreshToken) {
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
        }

        public String getAccessToken() {
            return accessToken;
        }

        public String getRefreshToken() {
            return refreshToken;
        }
    }
}
