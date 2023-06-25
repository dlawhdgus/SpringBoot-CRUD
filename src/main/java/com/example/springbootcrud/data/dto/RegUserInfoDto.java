package com.example.springbootcrud.data.dto;

import lombok.Data;

import java.util.Date;

@Data
public class RegUserInfoDto {
    Date date = new Date();
    private String id;
    private String nickname;
    private String password;
    private Date reg_date;

}

