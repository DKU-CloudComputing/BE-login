package com.dankook.login.config;

import com.dankook.login.dto.Login;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json");
        log.info("request: /api/login");

        Login login = new Login();
        login.setLoggedIn(authentication.getName() != null);
        login.setAuthentication(authentication);
        login.setJsessionId(response.getHeader("Set-Cookie").split(";")[0].split("=")[1]);
        log.info("loginJsessionid: {}", login.getJsessionId());

        Gson gson = new Gson();
        String jsonData = gson.toJson(login);

        log.info("jsonData: {}", jsonData);

        PrintWriter out = response.getWriter();
        out.println(jsonData);
    }
}
