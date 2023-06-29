package com.example.springbootcrud.data.repository;

import com.example.springbootcrud.data.entity.UserInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfoEntity, Integer> {

    @Modifying
    @Query(value = "INSERT INTO user_info (id, nickname, password, reg_date, flag)" +
            "VALUES (:id, :nickname, :password, :reg_date, :flag)", nativeQuery = true)
    void InsertUserInfo(String id, String nickname, String password, String reg_date, char flag);

    @Query("select u.id, u.password from UserInfoEntity u where u.id = :id")
    String SelectUserId(String id);

    @Query("select u from UserInfoEntity u where u.id = :id")
    Collection<UserInfoEntity> UserInfo(String id);

    @Modifying
    @Query("update UserInfoEntity u set u.nickname = :nickname where u.id = :id")
    int UpadteUserInfo(String id, String nickname);
}
