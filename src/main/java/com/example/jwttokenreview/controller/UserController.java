package com.example.jwttokenreview.controller;

import com.example.jwttokenreview.domain.dto.UserJoinRequest;
import com.example.jwttokenreview.domain.entity.User;
import com.example.jwttokenreview.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public String join(@RequestBody UserJoinRequest request) {
        log.info("request");
        User savedUser = userService.join(request);
        return savedUser.getUsername() + "님 회원가입 성공";
    }
}
