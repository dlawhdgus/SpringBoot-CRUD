package com.example.springbootcrud.controller;

import com.example.springbootcrud.Util.ChkEmpty;
import com.example.springbootcrud.Util.Crypto;
import com.example.springbootcrud.Util.JwtBuild;
import com.example.springbootcrud.Util.JwtDecode;
import com.example.springbootcrud.config.JwtConfiguration;
import com.example.springbootcrud.data.dto.LoginDto;
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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collection;


@Controller
public class LoginController {

    private UserInfoRepository userInfoRepo;
    private MoreUserInfoRepository moreUserInfoRepo;
    private JwtConfiguration jwtConfig;


    private Logger LOGGER = LoggerFactory.getLogger(RegisterController.class);

    @Autowired
    public LoginController(UserInfoRepository userInfoRepo, MoreUserInfoRepository moreUserInfoRepo, JwtConfiguration jwtConfig) {
        this.userInfoRepo = userInfoRepo;
        this.moreUserInfoRepo = moreUserInfoRepo;
        this.jwtConfig = jwtConfig;
    }

//    // logic -> getid, id를 db에 조회 후 pw받아서 pwMatch -> jwt or session -> userMypage
    @GetMapping("/login")
    public String LoginView(HttpSession session, Model model) {
        Object sessionId = session.getAttribute("jwt");
        if (sessionId != null) {
            JwtDecode jwtDecode = new JwtDecode(jwtConfig);
            String decodeJwt = jwtDecode.Decode(sessionId.toString());

            Collection<UserInfoEntity> userInfo = userInfoRepo.UserInfo(decodeJwt);
            Collection<MoreUserInfoEntity> moreUserInfo = moreUserInfoRepo.MoreUserInfo(decodeJwt);
            LOGGER.info("{}", userInfo);
            LOGGER.info("{}", moreUserInfo);

            UserInfoEntity userInfoEntity = userInfo.iterator().next();
            MoreUserInfoEntity moreUserInfoEntity = moreUserInfo.iterator().next();

            String id = userInfoEntity.getId();
            String nickname = userInfoEntity.getNickname();
            String email = moreUserInfoEntity.getEmail();
            String phoneNumber = moreUserInfoEntity.getPhone_number();
            String address = moreUserInfoEntity.getAddress();

            model.addAttribute("id", id);
            model.addAttribute("nickname", nickname);
            model.addAttribute("email", email);
            model.addAttribute("phone_number", phoneNumber);
            model.addAttribute("address", address);

            return "mypage.html";
        } else {
            return "login.html";
        }
    }


    @PostMapping(value = "/login")
    public String loginLogic(LoginDto loginDto, HttpSession session) {
        String userInfo = userInfoRepo.SelectUserId(loginDto.id);
        if(!ChkEmpty.isEmpty(userInfo)) {
            //ChkPassword
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


