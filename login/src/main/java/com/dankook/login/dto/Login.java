package com.dankook.login.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;

@Getter
@Setter
public class Login {
    private boolean loggedIn;
    private Authentication authentication;
    private String jsessionId;
}
