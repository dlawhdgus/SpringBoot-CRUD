package com.example.springbootcrud.data.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "user_info")
@Data
public class UserInfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idx;
    private String id;
    private String nickname;
    private String password;
    private String reg_date;
    private char flag;
}
