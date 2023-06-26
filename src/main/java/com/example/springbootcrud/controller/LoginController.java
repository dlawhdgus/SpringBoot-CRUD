package com.example.springbootcrud.controller;

import com.example.springbootcrud.Util.ChkEmpty;
import com.example.springbootcrud.Util.Crypto;
import com.example.springbootcrud.Util.JwtBuild;
import com.example.springbootcrud.Util.JwtDecode;
import com.example.springbootcrud.config.JwtConfiguration;
import com.example.springbootcrud.data.dto.LoginDto;
import com.example.springbootcrud.data.repository.UserInfoRepository;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class LoginController {

    private UserInfoRepository userInfoRepo;
    private JwtConfiguration jwtConfig;

    private Logger LOGGER = LoggerFactory.getLogger(RegisterController.class);

    @Autowired
    public LoginController(UserInfoRepository userInfoRepo, JwtConfiguration jwtConfig) {
        this.userInfoRepo = userInfoRepo;
        this.jwtConfig = jwtConfig;
    }

//    // logic -> getid, id를 db에 조회 후 pw받아서 pwMatch -> jwt or session -> userMypage
    @PostMapping(value = "/login")
    public String loginLogic(LoginDto loginDto, HttpSession session) {

        if(!ChkEmpty.isEmpty(loginDto.id)) {
            //ChkPassword
            String userInfo = userInfoRepo.SelectUserId(loginDto.id);
            String userArray[] = StringUtils.split(userInfo,",");

            if (Crypto.PasswordMatch(loginDto.getPassword(), userArray[1])) {
                JwtBuild jwtBuild = new JwtBuild(jwtConfig);
                JwtDecode jwtDecode = new JwtDecode(jwtConfig);
                //make jwt
                String jwt = jwtBuild.Build(userArray[0]);

                session.setAttribute("jwt", jwt);

                return "redirect:/mypage";
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


