package com.example.springbootcrud.controller;

import com.example.springbootcrud.data.repository.MoreUserInfoRepository;
import com.example.springbootcrud.data.repository.UserInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UpdateController {

    private UserInfoRepository userInfoRepo;
    private MoreUserInfoRepository moreUserInfoRepo;

    private Logger LOGGER = LoggerFactory.getLogger(UpdateController.class);

    @Autowired
    public UpdateController(UserInfoRepository userInfoRepo, MoreUserInfoRepository moreUserInfoRepo) {
        this.userInfoRepo = userInfoRepo;
        this.moreUserInfoRepo = moreUserInfoRepo;
    }

    @GetMapping("/edit")
    public String EditPage() {
        return null;
    }
}
