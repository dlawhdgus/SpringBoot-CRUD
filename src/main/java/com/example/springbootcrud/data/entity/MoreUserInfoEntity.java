package com.example.springbootcrud.data.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "more_user_info")
@Data
public class MoreUserInfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int idx;
    @NotNull
    private String id;
    @Email
    private String email;
    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$")
    private String phone_number;
    private String address;
    private String reg_date;

}
