package com.dankook.login.controller;

import com.dankook.login.dto.*;
import com.dankook.login.entity.User;
import com.dankook.login.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserApiController {
    private final UserService userService;

    @PostMapping("/api/signup")
    public Signup signup(AddUserRequest request) {
        log.info("request: /api/signup");
        log.info("dto: {}", request.toString());
        Signup signup = new Signup();
        signup.setId(userService.save(request));
        return signup;
    }

    @PostMapping("/api/user/edit")
    public Signup edit(AddUserRequest request) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        log.info("request: /api/user/edit");
        log.info("dto: {}", request.toString());
        Signup signup = new Signup();
        User editedUser = userService.findById(Long.valueOf(request.getUserId()));
        editedUser.setEmail(request.getEmail());
        editedUser.setNickname(request.getNickname());
        if(!encoder.matches(request.getPassword(), editedUser.getPassword())){
            editedUser.setPassword(encoder.encode(request.getPassword()));
        }
        signup.setId(userService.save(editedUser));
        return signup;
    }

    @GetMapping("/api/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
    }

    @PostMapping("/api/duplication/email")
    public Duplication checkEmail(@RequestBody CheckUserEmail emailDto) {
        log.info("requset: check duplication of email");
        log.debug("emailDto: {}", emailDto.getEmail());
        Duplication duplication = new Duplication();
        User user = userService.findByEmail(emailDto.getEmail());
        duplication.setDuplication(user != null);
        log.debug("duplication: {}", duplication.isDuplication());
        return duplication;
    }

    @PostMapping("/api/duplication/nickname")
    public Duplication checkNickname(@RequestBody CheckUserNickname nicknameDto) {
        log.info("requset: check duplication of nickname");
        log.debug("nicknameDto: {}", nicknameDto.getNickname());
        Duplication duplication = new Duplication();
        User user = userService.findByNickname(nicknameDto.getNickname());
        duplication.setDuplication(user != null);
        log.debug("duplication: {}", duplication.isDuplication());
        return duplication;
    }
}
