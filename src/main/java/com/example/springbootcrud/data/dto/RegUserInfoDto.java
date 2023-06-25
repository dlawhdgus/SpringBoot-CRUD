package com.example.springbootcrud.data.dto;

import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.Date;

@Data
public class RegUserInfoDto {

    Date date = new Date();

    @NotNull
    private String id;
    @NotNull
    private String nickname;
    @NotNull
    private String password;
    @NotNull
    private Date reg_date;

}

