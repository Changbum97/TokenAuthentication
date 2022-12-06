package com.example.jwttokenreview.controller;

import com.example.jwttokenreview.domain.dto.ReviewCreateRequest;
import com.example.jwttokenreview.domain.dto.UserJoinRequest;
import com.example.jwttokenreview.domain.dto.UserLoginRequest;
import com.example.jwttokenreview.domain.entity.User;
import com.example.jwttokenreview.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
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
        User savedUser = userService.join(request);
        return savedUser.getUsername() + "님 회원가입 성공";
    }

    @PostMapping("/login")
    public String login(@RequestBody UserLoginRequest request) {
        String token = userService.login(request.getUsername(), request.getPassword());
        return "로그인 성공\nToken : " + token;
    }

    @PostMapping("/review")
    public String review(@RequestBody ReviewCreateRequest request, Authentication authentication) {
        return authentication.getName() + "님이 리뷰를 작성하였습니다,\n내용 : " + request.getContent();
    }
}
