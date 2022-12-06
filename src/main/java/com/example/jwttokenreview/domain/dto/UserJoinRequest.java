package com.example.jwttokenreview.domain.dto;

import com.example.jwttokenreview.domain.UserRole;
import com.example.jwttokenreview.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
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
