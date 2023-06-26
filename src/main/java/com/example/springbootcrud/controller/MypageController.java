package com.example.springbootcrud.controller;

import com.example.springbootcrud.Util.JwtDecode;
import com.example.springbootcrud.config.JwtConfiguration;
import com.example.springbootcrud.data.entity.UserInfoEntity;
import com.example.springbootcrud.data.repository.UserInfoRepository;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;

@Controller
public class MypageController {

    private UserInfoRepository userInfoRepo;
    private JwtConfiguration jwtConfig;

    @Autowired
    public MypageController(JwtConfiguration jwtConfig, UserInfoRepository userInfoRepos) {
        this.jwtConfig = jwtConfig;
        this.userInfoRepo = userInfoRepos;
    }

    private Logger LOGGER = LoggerFactory.getLogger(RegisterController.class);

    @GetMapping(value = "/mypage")
    public String Mypage(HttpSession session) {


        //get JsonWebToken
        Object sessionId = session.getAttribute("jwt");

        JwtDecode jwtDecode = new JwtDecode(jwtConfig);
        String DecodeJwt = jwtDecode.Decode(sessionId.toString());
        LOGGER.info("{}", DecodeJwt);

        Collection<UserInfoEntity> userInfo = userInfoRepo.UserInfo(DecodeJwt);
        LOGGER.info("{}", userInfo);



        return "mypage.html";
    }

}
