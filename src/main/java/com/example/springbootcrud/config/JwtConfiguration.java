package com.example.springbootcrud.config;

import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Getter
@Configuration
public class JwtConfiguration {

    @Value("${jwt.secret}")
    public String secret;

    @Value("${jwt.expiration}")
    public int expiration;

    @Value("${jwt.prefix}")
    public String prefix;

}
