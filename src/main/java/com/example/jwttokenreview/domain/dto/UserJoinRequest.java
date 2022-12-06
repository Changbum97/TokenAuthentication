package com.example.jwttokenreview.domain.dto;

import com.example.jwttokenreview.domain.UserRole;
import com.example.jwttokenreview.domain.entity.User;
import lombok.Getter;

@Getter
public class UserJoinRequest {

    private String username;
    private String password;

    public User toEntity(String encodedPassword) {
        return User.builder()
                .username(this.username)
                .password(encodedPassword)
                .role(UserRole.USER)
                .build();
    }
}
