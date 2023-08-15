package com.example.springbootcrud.controller;

import com.example.springbootcrud.data.repository.MoreUserInfoRepository;
import com.example.springbootcrud.data.repository.UserInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class AdminController {

    private UserInfoRepository userInfoRepository;
    private MoreUserInfoRepository moreUserInfoRepository;

    @Autowired
    public AdminController(UserInfoRepository userInfoRepository, MoreUserInfoRepository moreUserInfoRepository) {
        this.userInfoRepository = userInfoRepository;
        this.moreUserInfoRepository = moreUserInfoRepository;
    }

    private Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

    @GetMapping("/admin")
    public String  adminUserList(Model model,
                                 @RequestParam String page,
                                 @RequestParam String pageSize) {

        int pageNumber = Integer.parseInt(page);
        int pageSizeNumber = Integer.parseInt(pageSize);

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSizeNumber);

        Page<Objects []> userDataPage = userInfoRepository.getUserData(pageable);

        List<Objects []> userData = userDataPage.getContent();
        List<Objects []> limitedUserData = userData.subList(0, Math.min(userData.size(), 10));

        model.addAttribute("userData", limitedUserData);
        LOGGER.info("data : {}", limitedUserData);
        int totalPages = userDataPage.getTotalPages();
        List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
        //InStream.rangeClosed() : 페이지 번호의 목록 생성
        //boxed() : 각 정수를 해당하는 integer 객체로 변환
        //collect(Collectors.toList()) : 스트림 요소를 List<integer>로 수집
        model.addAttribute("pageNumbers", pageNumbers);

        return "adminuserlist";

    }
}
