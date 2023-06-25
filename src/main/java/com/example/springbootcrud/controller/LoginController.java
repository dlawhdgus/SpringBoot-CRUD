package com.example.springbootcrud.controller;

import com.example.springbootcrud.Util.ChkEmpty;
import com.example.springbootcrud.Util.Crypto;
import com.example.springbootcrud.data.dto.LoginDto;
import com.example.springbootcrud.data.repository.UserInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private UserInfoRepository userInfoRepo;

    private Logger LOGGER = LoggerFactory.getLogger(RegisterController.class);

    @Autowired
    public LoginController(UserInfoRepository userInfoRepo) {
        this.userInfoRepo = userInfoRepo;
    }

//    // logic -> getid, id를 db에 조회 후 pw받아서 pwMatch -> jwt or session -> userMypage
    @PostMapping(value = "/login")
    public Object loginLogic(LoginDto loginUserInfoDto) {
        LoginDto loginDto = new LoginDto();

        if(!ChkEmpty.isEmpty(loginDto.id)) {
            //ChkPassword
            String userInfo = userInfoRepo.SelectUserId(loginDto.id);
            String userArray[] = StringUtils.split(userInfo,",");

            if (Crypto.PasswordMatch(loginDto.getPassword(), userArray[1])) {
                return null;
            } else {
                LOGGER.error("비밀번호를 확인해주세요.");

                return "redirect:/login";
            }
        } else {
            LOGGER.error("아이디를 확인해주세요.");

            return "redirect:/login";
        }
    }

}
