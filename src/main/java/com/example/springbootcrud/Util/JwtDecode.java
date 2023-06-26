package com.example.springbootcrud.Util;

import com.example.springbootcrud.config.JwtConfiguration;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

public class JwtDecode {

    private JwtConfiguration jwtConfig;

    public JwtDecode(JwtConfiguration jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    public String Decode(String Token) {

        String secretKey = jwtConfig.getSecret();

        Jws<Claims> jwtClaims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(Token);

        Claims claims = jwtClaims.getBody();

        String userId = claims.getSubject();

        return userId;
    }
}
