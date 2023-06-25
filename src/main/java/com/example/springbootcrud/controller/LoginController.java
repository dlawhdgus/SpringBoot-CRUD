package com.example.springbootcrud.controller;

import com.example.springbootcrud.Util.ChkEmpty;
import com.example.springbootcrud.Util.Crypto;
import com.example.springbootcrud.data.dto.LoginUserInfoDto;
import com.example.springbootcrud.data.repository.MoreUserInfoRepository;
import com.example.springbootcrud.data.repository.UserInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.lang.reflect.Array;

@Controller
public class LoginController {

    private UserInfoRepository userInfoRepo;

    private Logger LOGGER = LoggerFactory.getLogger(RegisterController.class);

    @Autowired
    public LoginController(UserInfoRepository userInfoRepo) {
        this.userInfoRepo = userInfoRepo;
    }

//    @PostMapping(value = "/login")
//    public String loginLogic(LoginUserInfoDto loginUserInfoDto) {
//    // logic -> getid, id를 db에 조회 후 pw받아서 pwMatch -> jwt or session -> userMypage
//
//        Object[] Duplicate_id = userInfoRepo.SelectUserId(loginUserInfoDto.getId());
//
//        if(!ChkEmpty.isEmpty(Duplicate_id.toString())) {
//            LOGGER.info("{}", loginUserInfoDto.getId());
//
//            LOGGER.info("{}", loginUserInfoDto.getPassword());
////            Crypto.PasswordMatch(loginUserInfoDto, )
//        } else {
//            LOGGER.error("아이디를 확인해주세요.");
//        }
//
//        return null;
//    }
}
