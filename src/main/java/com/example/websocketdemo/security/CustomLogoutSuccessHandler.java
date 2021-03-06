package com.example.websocketdemo.security;


import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        response.setStatus(HttpStatus.OK.value());

        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        String JSON_VALUE = "{\"%s\": %s}";

        response.getWriter().write(String.format(JSON_VALUE, "success", "true"));
    }

    public void sendJsonResponse(HttpServletResponse response, String key, String message) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        String JSON_VALUE = "{\"%s\": %s}";

        response.getWriter().write(String.format(JSON_VALUE, key, message));
    }
}