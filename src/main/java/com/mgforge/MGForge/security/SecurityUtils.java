package com.mgforge.MGForge.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtlis {

    private SecurityUtlis(){

    }

    public static AppPrincipal currentPrincipal(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(auth == null || !(auth.getPrincipal() instanceof AppPrincipal principal){
            throw new AccessDeniedException("No authenticated principal found");
        }

        return principal;
    }
}
