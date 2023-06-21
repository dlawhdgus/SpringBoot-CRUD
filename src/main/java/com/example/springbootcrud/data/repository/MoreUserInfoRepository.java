package com.example.springbootcrud.data.repository;

import com.example.springbootcrud.data.entity.MoreUserInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface MoreUserInfoRepository extends JpaRepository<MoreUserInfoEntity, Integer> {

    @Modifying
    @Query(value = "INSERT INTO more_user_info (id, email, phone_number, address, reg_date)" +
            "VALUES (:id, :email, :phone_number, :address, :reg_date)", nativeQuery = true)
    void InsertUserInfo(String id, String email, String phone_number, String address, String reg_date);
}
