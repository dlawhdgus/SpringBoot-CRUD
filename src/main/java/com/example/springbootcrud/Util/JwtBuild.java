package com.example.springbootcrud.Util;

import com.example.springbootcrud.config.JwtConfiguration;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtBuild {
    private JwtConfiguration jwtConfig;

    public JwtBuild(JwtConfiguration jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    public String Build(String userId) {

        String secretKey = jwtConfig.getSecret();
        int expiration = jwtConfig.getExpiration();

        Claims claims = Jwts.claims();
        claims.setSubject(userId);
        claims.setIssuer("dlawhdgus");  //jwt 만든사람(issuer)
        claims.setExpiration(new Date(System.currentTimeMillis() + expiration * 1000)); //30m을 밀리초로 기준을 잡았기 때문에 1000을 곱함
        claims.setIssuedAt(new Date());

        String jwt = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        return jwt;
    }
}
