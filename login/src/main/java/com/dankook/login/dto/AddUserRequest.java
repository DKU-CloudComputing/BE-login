package com.dankook.login.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AddUserRequest {
    private String userId;
    private String email;
    private String password;
    private String nickname;
}
