package com.example.springbootcrud;

import com.example.springbootcrud.config.RedisConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication
@Import(RedisConfiguration.class)
@EnableRedisHttpSession
public class SpringBootCurdApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootCurdApplication.class, args);
    }

}
