package com.example.springbootcrud.controller;

import com.example.springbootcrud.Util.JwtDecode;
import com.example.springbootcrud.config.JwtConfiguration;
import com.example.springbootcrud.data.entity.MoreUserInfoEntity;
import com.example.springbootcrud.data.entity.UserInfoEntity;
import com.example.springbootcrud.data.repository.MoreUserInfoRepository;
import com.example.springbootcrud.data.repository.UserInfoRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;

@Controller
public class UpdateController {

    private UserInfoRepository userInfoRepo;
    private MoreUserInfoRepository moreUserInfoRepo;
    private JwtConfiguration jwtConfig;

    private Logger LOGGER = LoggerFactory.getLogger(UpdateController.class);

    @Autowired
    public UpdateController(UserInfoRepository userInfoRepo, MoreUserInfoRepository moreUserInfoRepo, JwtConfiguration jwtConfig) {
        this.userInfoRepo = userInfoRepo;
        this.moreUserInfoRepo = moreUserInfoRepo;
        this.jwtConfig = jwtConfig;
    }

    @GetMapping("/edit")
    public String EditPage(HttpSession session, Model model) {
        Object sessionId = session.getAttribute("jwt");
        JwtDecode jwtDecode = new JwtDecode(jwtConfig);

        String DecodeJwt = jwtDecode.Decode(sessionId.toString());
        LOGGER.info("id : {}", DecodeJwt);

        Collection<UserInfoEntity> userInfo = userInfoRepo.UserInfo(DecodeJwt);
        Collection<MoreUserInfoEntity> moreUserInfo = moreUserInfoRepo.MoreUserInfo(DecodeJwt);

        LOGGER.info("userInfo : {}",userInfo);

        UserInfoEntity UserInfo = userInfo.iterator().next();
        MoreUserInfoEntity MoreUserInfo = moreUserInfo.iterator().next();

        LOGGER.info("UserInfo : {}",UserInfo);

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

        return "user_edit.html";
    }

    @PostMapping("/edit_Logic")
    @Transactional
    public String editLogic(Model model,
                            @RequestParam String id,
                            @RequestParam String nickname,
                            @RequestParam String email,
                            @RequestParam String phone_number,
                            @RequestParam String address) {

        LOGGER.info("edit_logic");
        LOGGER.info("{}", id);
        LOGGER.info("{}", nickname);
        LOGGER.info("{}", email);
        LOGGER.info("{}", phone_number);
        LOGGER.info("{}", address);

        int update_userInfo = userInfoRepo.UpadteUserInfo(id, nickname);
        int update_moreInfo = moreUserInfoRepo.UpadteMoreUserInfo(id, email, phone_number, address);

        Collection<UserInfoEntity> userInfo = userInfoRepo.UserInfo(id);
        Collection<MoreUserInfoEntity> moreUserInfo = moreUserInfoRepo.MoreUserInfo(id);

        UserInfoEntity UserInfo = userInfo.iterator().next();
        MoreUserInfoEntity MoreUserInfo = moreUserInfo.iterator().next();

        id = UserInfo.getId();
        nickname = UserInfo.getNickname();
        email = MoreUserInfo.getEmail();
        phone_number = MoreUserInfo.getPhone_number();
        address = MoreUserInfo.getAddress();

        model.addAttribute("id", id);
        model.addAttribute("nickname", nickname);
        model.addAttribute("email", email);
        model.addAttribute("phone_number", phone_number);
        model.addAttribute("address", address);

        return "mypage.html";
    }

    @GetMapping("/adminEdit")
    public String userEdit(Model model, @RequestParam String id) {
        LOGGER.info("{}",id);
        Collection<UserInfoEntity> userInfo = userInfoRepo.UserInfo(id);
        Collection<MoreUserInfoEntity> moreUserInfo = moreUserInfoRepo.MoreUserInfo(id);

        LOGGER.info("userInfo: {}", userInfo);

        UserInfoEntity userInfoEntity = userInfo.iterator().next();
        MoreUserInfoEntity moreUserInfoEntity = moreUserInfo.iterator().next();

        LOGGER.info("UserInfo: {}", userInfoEntity);

        String nickname = userInfoEntity.getNickname();
        String email = moreUserInfoEntity.getEmail();
        String phoneNumber = moreUserInfoEntity.getPhone_number();
        String address = moreUserInfoEntity.getAddress();

        model.addAttribute("id", id);
        model.addAttribute("nickname", nickname);
        model.addAttribute("email", email);
        model.addAttribute("phone_number", phoneNumber);
        model.addAttribute("address", address);

        return "adminedit";
    }

    @PostMapping("/aEdit_logic")
    @Transactional
    public String adminEditLogic(Model model,
                                 @RequestParam String id,
                                 @RequestParam String nickname,
                                 @RequestParam String email,
                                 @RequestParam String phone_number,
                                 @RequestParam String address) {

        LOGGER.info("{}", id);
        LOGGER.info("{}", nickname);
        LOGGER.info("{}", email);
        LOGGER.info("{}", phone_number);
        LOGGER.info("{}", address);

        String regexPhone_num = phone_number.replaceAll("(\\d{2,3})(\\d{3,4})(\\d{4})", "$1-$2-$3");

        int update_userInfo = userInfoRepo.UpadteUserInfo(id, nickname);
        int update_moreInfo = moreUserInfoRepo.UpadteMoreUserInfo(id, email, regexPhone_num, address);

        Collection<UserInfoEntity> userInfo = userInfoRepo.UserInfo(id);
        Collection<MoreUserInfoEntity> moreUserInfo = moreUserInfoRepo.MoreUserInfo(id);

        UserInfoEntity UserInfo = userInfo.iterator().next();
        MoreUserInfoEntity MoreUserInfo = moreUserInfo.iterator().next();

        id = UserInfo.getId();
        nickname = UserInfo.getNickname();
        email = MoreUserInfo.getEmail();
        phone_number = MoreUserInfo.getPhone_number();
        address = MoreUserInfo.getAddress();

        model.addAttribute("id", id);
        model.addAttribute("nickname", nickname);
        model.addAttribute("email", email);
        model.addAttribute("phone_number", phone_number);
        model.addAttribute("address", address);

        return "redirect:/admin?page=1&pageSize=5";
    }
}
