package com.mgforge.MGForge.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.sql.Date;
import java.time.Instant;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Service
public class JwtService {
    private final SecretKey key;
    private final String issuer;
    private final long accessMinutes;

    public JwtService(@Value("${app.jwt.secret-base64}") String secretBase64,
                      @Value("${app.jwt.issuer}") String issuer,
                      @Value("${app.jwt.access-token-minutes") long accessMinutes){
        this.key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretBase64));
        this.issuer = issuer;
        this.accessMinutes = accessMinutes;
    }

    public String createAccessToken(String userId, String tenantId, List<String> roles){
        Instant now = Instant.now();
        Instant exp = now.plusSeconds(accessMinutes * 60);

        return Jwts.builder()
                .setIssuer(issuer)
                .setSubject(userId)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(exp))
                .addClaims(Map.of(
                        "tid",tenantId,
                        "roles",roles
                ))
                .signWith(key, SignatureAlgorithm.ES256)
                .compact();
    }

    public io.jsonwebtoken.Claims parseClaims (String token) {
        return  Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
