package com.example.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestHeader;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import org.springframework.beans.factory.annotation.Value;
import java.util.stream.Collectors;
import java.util.Date;
import io.jsonwebtoken.Jwts;

@RestController
@RequestMapping()
public class TokenController {

    @Value("${rsa.private-key}") RSAPrivateKey privateKey;
    @Value("${rsa.public-key}") RSAPublicKey publicKey;

    @PostMapping("/login")
    public String getToken() {
        // create jwt token with username, roles, and authorities
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String roles = authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","));

        // Calculate token expiration: 24 hours from now
        long currentTimeMillis = System.currentTimeMillis();
        long expirationTimeMillis = currentTimeMillis + 86400000L; // 24 hours in milliseconds

        String token = Jwts.builder()
            .subject(authentication.getName())
            .claim("username", authentication.getName())
            .claim("roles", roles)
            .claim("scope", roles)
            .issuedAt(new Date(currentTimeMillis))
            .expiration(new Date(expirationTimeMillis))
            .signWith(privateKey)
            .compact();

        return token;
    }

}