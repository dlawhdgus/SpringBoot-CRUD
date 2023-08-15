package com.example.springbootcrud.data.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserDto {

    private String id;
    private String ninkname;
    private Date reg_date;
    private String email;
    private String phone_number;
    private String address;
}
