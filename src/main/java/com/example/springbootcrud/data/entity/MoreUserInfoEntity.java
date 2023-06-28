package com.example.springbootcrud.data.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "more_user_info")
@Data
public class MoreUserInfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int idx;
    private String id;
    private String email;
    private String phone_number;
    private String address;
    private String reg_date;

}
