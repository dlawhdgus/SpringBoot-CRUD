package com.example.springbootcrud.controller;

import com.example.springbootcrud.Util.JwtDecode;
import com.example.springbootcrud.config.JwtConfiguration;
import com.example.springbootcrud.data.entity.MoreUserInfoEntity;
import com.example.springbootcrud.data.entity.UserInfoEntity;
import com.example.springbootcrud.data.repository.MoreUserInfoRepository;
import com.example.springbootcrud.data.repository.UserInfoRepository;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.model.IModel;

import java.util.Collection;

@Controller
public class MypageController {

    private UserInfoRepository userInfoRepo;
    private MoreUserInfoRepository moreUserInfoRepo;
    private JwtConfiguration jwtConfig;

    @Autowired
    public MypageController(JwtConfiguration jwtConfig, UserInfoRepository userInfoRepos, MoreUserInfoRepository moreUserInfoRepo) {
        this.jwtConfig = jwtConfig;
        this.userInfoRepo = userInfoRepos;
        this.moreUserInfoRepo = moreUserInfoRepo;
    }

    private Logger LOGGER = LoggerFactory.getLogger(RegisterController.class);

    @GetMapping(value = "/mypage")
    public String Mypage(HttpSession session, Model model) {

        //get JsonWebToken
        Object sessionId = session.getAttribute("jwt");

        JwtDecode jwtDecode = new JwtDecode(jwtConfig);
        String DecodeJwt = jwtDecode.Decode(sessionId.toString());

        Collection<UserInfoEntity> userInfo = userInfoRepo.UserInfo(DecodeJwt);
        Collection<MoreUserInfoEntity> moreUserInfo = moreUserInfoRepo.MoreUserInfo(DecodeJwt);
        LOGGER.info("{}", userInfo);
        LOGGER.info("{}", moreUserInfo);

        UserInfoEntity UserInfo = userInfo.iterator().next();
        MoreUserInfoEntity MoreUserInfo = moreUserInfo.iterator().next();

        String  id = UserInfo.getId();
        String nickname = UserInfo.getNickname();
        String email = MoreUserInfo.getEmail();
        String phone_number = MoreUserInfo.getPhone_number();
        String address = MoreUserInfo.getAddress();

        model.addAttribute("id", id);
        model.addAttribute("nickname", nickname);
        model.addAttribute("email", email);
        model.addAttribute("phone_number", phone_number);
        model.addAttribute("address", address);

        return "mypage.html";
    }

}