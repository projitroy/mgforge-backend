package com.mgforge.MGForge.auth;

import com.mgforge.MGForge.security.AppPrincipal;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request){
        String path = request.getServletPath();
        return path.equals("/auth/login") || path.equals("/admin/auth/login") || path.equals("/graphql");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String auth = request.getHeader("Authorization");
        if(auth == null || !auth.startsWith("Bearer ")){
            chain.doFilter(request,response);
            return;
        }

        String token = auth.substring("Bearer ".length());

        try {
            Claims claims = jwtService.parseClaims(token);
            UUID userId = UUID.fromString(claims.getSubject());

            Object tidObj = claims.get("tid");
            UUID tenantId = (tidObj == null) ? null : UUID.fromString(tidObj.toString());

            @SuppressWarnings("unchecked")
            List<String> roles = (List<String>) claims.get("roles");

            var authorities = roles.stream()
                    .map(r -> new SimpleGrantedAuthority("ROLE_"+r))
                    .collect(Collectors.toList());

            AppPrincipal principal = new AppPrincipal(userId,tenantId,roles);

            var authentication = new UsernamePasswordAuthenticationToken(principal,null,authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            chain.doFilter(request,response);
        } catch (Exception ex){
            SecurityContextHolder.clearContext();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid or expired token");
        }
    }
}
