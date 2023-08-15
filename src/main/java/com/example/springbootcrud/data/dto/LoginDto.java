package com.example.springbootcrud.data.dto;

import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

@Data
public class LoginDto {
    @NotNull
    public String id;
    @NotNull
    public String password;
}
