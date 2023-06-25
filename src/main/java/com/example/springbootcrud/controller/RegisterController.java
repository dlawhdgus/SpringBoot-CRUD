package com.example.springbootcrud.controller;

import com.example.springbootcrud.Util.ChkEmpty;
import com.example.springbootcrud.Util.Crypto;
import com.example.springbootcrud.data.dto.LoginDto;
import com.example.springbootcrud.data.dto.RegUserInfoDto;
import com.example.springbootcrud.data.entity.UserInfoEntity;
import com.example.springbootcrud.data.repository.MoreUserInfoRepository;
import com.example.springbootcrud.data.repository.UserInfoRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Controller
public class RegisterController {

    private UserInfoRepository userInfoRepo;
    private MoreUserInfoRepository moreUserInfoRepo;

    @Autowired
    public RegisterController(UserInfoRepository userInfoRepo, MoreUserInfoRepository moreUserInfoRepo) {
        this.userInfoRepo = userInfoRepo;
        this.moreUserInfoRepo = moreUserInfoRepo;
    }

    private Logger LOGGER = LoggerFactory.getLogger(RegisterController.class);
    private String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    @PostMapping(value = "/sign_up", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @Transactional
    public String signUpLogic(@ModelAttribute RegUserInfoDto userInfoDto,
                              @RequestParam String email,
                              @RequestParam String phone_number,
                              @RequestParam String address_number,
                              @RequestParam String roadAddress,
                              @RequestParam String detailAddress,
                              @RequestParam String extraAddress) {

        String addressNumber = ChkEmpty.isEmpty(address_number) ? "" : address_number;
        String RoadAddress = ChkEmpty.isEmpty(roadAddress) ? "" : roadAddress;
        String DetailAddress = ChkEmpty.isEmpty(detailAddress) ? "" : detailAddress;
        String ExtraAddress = ChkEmpty.isEmpty(extraAddress) ? "" : extraAddress;

        String address = addressNumber + RoadAddress + DetailAddress + ExtraAddress;

        if (!ChkEmpty.isEmpty(userInfoDto.getId()) || !ChkEmpty.isEmpty(userInfoDto.getNickname()) || !ChkEmpty.isEmpty(userInfoDto.getPassword())) {
            String duplicateId = userInfoRepo.SelectUserId(userInfoDto.getId());
            if (ChkEmpty.isEmpty(duplicateId)) {

                String encodedPassword = Crypto.encode(userInfoDto.getPassword());
                userInfoRepo.InsertUserInfo(userInfoDto.getId(), userInfoDto.getNickname(), encodedPassword, date);
                moreUserInfoRepo.InsertUserInfo(userInfoDto.getId(), email, phone_number, address,date);

                LOGGER.info("id -> {}", userInfoDto.getId());
                LOGGER.info("nickname -> {}", userInfoDto.getNickname());
                LOGGER.info("password -> {}", encodedPassword);
                LOGGER.info("reg_date -> {}", date);
                LOGGER.info("email -> {}", email);
                LOGGER.info("phone_number -> {}", phone_number);
                LOGGER.info("address -> {}", address);

                return "redirect:/login";
            } else {
                LOGGER.error("아이디 중복 \n 다른 아이디를 사용하세요");

                return "redirect:/sign_up";
            }
        } else {
            LOGGER.error("필수항목을 입력해주세요");

            return "redirect:/sign_up";
        }
    }
}
