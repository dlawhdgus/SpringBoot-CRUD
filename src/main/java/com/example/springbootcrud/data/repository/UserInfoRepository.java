package com.example.springbootcrud.data.repository;

import com.example.springbootcrud.data.entity.UserInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfoEntity, Integer> {

    @Modifying
    @Query(value = "INSERT INTO user_info (id, nickname, password, reg_date)" +
            "VALUES (:id, :nickname, :password, :reg_date)", nativeQuery = true)
    void InsertUserInfo(String id, String nickname, String password, String reg_date);

    @Query("select u.id from UserInfoEntity u where u.id = :id")
    String SelectUserId(String id);

}