package com.example.springbootcrud.data.repository;

import com.example.springbootcrud.data.entity.UserInfoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Objects;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfoEntity, Integer> {

    @Modifying
    @Query(value = "INSERT INTO user_info (id, nickname, password, reg_date, flag)" +
            "VALUES (:id, :nickname, :password, :reg_date, :flag)", nativeQuery = true)
    void InsertUserInfo(String id, String nickname, String password, String reg_date, char flag);

    @Query("select u.id, u.password from UserInfoEntity u where u.id = :id")
    String SelectUserId(String id);

    @Query("select u.flag from UserInfoEntity u where u.id = :id")
    String GetUserFlag(String id);

    @Query("select u from UserInfoEntity u where u.id = :id")
    Collection<UserInfoEntity> UserInfo(String id);

    @Modifying
    @Query("update UserInfoEntity u set u.nickname = :nickname where u.id = :id")
    int UpadteUserInfo(String id, String nickname);

    @Modifying
    @Query("delete from UserInfoEntity u where u.id = :id")
    int DeleteUser(String id);

    @Query("select u.id, u.nickname, u.reg_date, m.email, m.phone_number, m.address from UserInfoEntity u inner join MoreUserInfoEntity m on u.id = m.id where u.flag ='u'")
    Page<Objects[]> getUserData(Pageable pageable);

}
