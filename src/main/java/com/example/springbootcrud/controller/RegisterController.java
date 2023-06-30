package com.example.springbootcrud.controller;

import com.example.springbootcrud.Util.ChkEmpty;
import com.example.springbootcrud.Util.Crypto;
import com.example.springbootcrud.data.dto.RegUserInfoDto;
import com.example.springbootcrud.data.entity.MoreUserInfoEntity;
import com.example.springbootcrud.data.repository.MoreUserInfoRepository;
import com.example.springbootcrud.data.repository.UserInfoRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
                              @Valid @RequestParam String phone_number,
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
                boolean regex = phone_number.matches("\\d{2,3}\\d{3,4}\\d{4}$");
                String encodedPassword = Crypto.encode(userInfoDto.getPassword());
                char flag = 'u';
                if(regex) {
                    String regexPhone_num = phone_number.replaceAll("(\\d{2,3})(\\d{3,4})(\\d{4})", "$1-$2-$3");

                    userInfoRepo.InsertUserInfo(userInfoDto.getId(), userInfoDto.getNickname(), encodedPassword, date, flag);
                    moreUserInfoRepo.InsertUserInfo(userInfoDto.getId(), email, regexPhone_num, address,date);

                    return "redirect:/login";
                } else {

                    userInfoRepo.InsertUserInfo(userInfoDto.getId(), userInfoDto.getNickname(), encodedPassword, date, flag);
                    moreUserInfoRepo.InsertUserInfo(userInfoDto.getId(), email, phone_number, address,date);

                    return "redirect:/login";
                }
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
