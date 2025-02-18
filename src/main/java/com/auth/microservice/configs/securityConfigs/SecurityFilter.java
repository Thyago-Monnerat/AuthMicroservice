package com.auth.microservice.configs.securityConfigs;

import com.auth.microservice.services.TokenService;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;

    public SecurityFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    private String getToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null) return null;
        return token.replace("Bearer ", "");
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String tokenReq = getToken(request);

        if (tokenReq != null) {
            String tokenValid = tokenService.validateToken(tokenReq);

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(tokenValid, null, null);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }else{
            throw new JWTVerificationException("Token is missing");
        }

        filterChain.doFilter(request, response);
    }
}
