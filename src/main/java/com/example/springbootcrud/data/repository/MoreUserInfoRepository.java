package com.example.springbootcrud.data.repository;

import com.example.springbootcrud.data.entity.MoreUserInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface MoreUserInfoRepository extends JpaRepository<MoreUserInfoEntity, Integer> {

    @Modifying
    @Query(value = "INSERT INTO more_user_info (id, email, phone_number, address, reg_date)" +
            "VALUES (:id, :email, :phone_number, :address, :reg_date)", nativeQuery = true)
    void InsertUserInfo(String id, String email, String phone_number, String address, String reg_date);

    @Query("select u from MoreUserInfoEntity u where u.id = :id")
    Collection<MoreUserInfoEntity> MoreUserInfo(String id);

    @Modifying
    @Query("update MoreUserInfoEntity u set u.email = :email, u.phone_number = :phone_number, u.address = :address where u.id = :id")
    int UpadteMoreUserInfo(String id, String email, String phone_number, String address);

    @Modifying
    @Query("delete MoreUserInfoEntity u where u.id = :id")
    int DeleteMoreInfo(String id);
}
