package com.example.springbootcrud.controller;

import com.example.springbootcrud.Util.JwtDecode;
import com.example.springbootcrud.config.JwtConfiguration;
import com.example.springbootcrud.data.repository.MoreUserInfoRepository;
import com.example.springbootcrud.data.repository.UserInfoRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.aspectj.lang.annotation.Around;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DeleteController {

    private JwtConfiguration jwtConfig;
    private UserInfoRepository userInfoRepository;
    private MoreUserInfoRepository moreUserInfoRepository;

    private Logger LOGGER = LoggerFactory.getLogger(DeleteController.class);

    @Autowired
    public DeleteController(JwtConfiguration jwtConfig, UserInfoRepository userInfoRepository, MoreUserInfoRepository moreUserInfoRepository) {
        this.jwtConfig = jwtConfig;
        this.userInfoRepository = userInfoRepository;
        this.moreUserInfoRepository = moreUserInfoRepository;
    }

    @PostMapping("/delete")
    @Transactional
    public String deleteUser(HttpSession session) {
        Object sessionId = session.getAttribute("jwt");

        JwtDecode jwtDecode = new JwtDecode(jwtConfig);
        String userId = jwtDecode.Decode(sessionId.toString());


        LOGGER.info("id : {}", userId);

        int deleteUserInfo = userInfoRepository.DeleteUser(userId);
        int deleteMoreInfo = moreUserInfoRepository.DeleteMoreInfo(userId);

        session.invalidate();

        return "redirect:/index";
    }
}
