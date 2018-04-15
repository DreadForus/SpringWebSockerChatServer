package com.example.websocketdemo.security;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomLoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {

        response.setStatus(HttpStatus.OK.value());
        sendJsonResponse(response, "success", "false");
//        response.sendRedirect(request.getContextPath() + "/secured/success");
    }


    public void sendJsonResponse(HttpServletResponse response, String key, String message) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        String JSON_VALUE = "{\"%s\": %s}";

        response.getWriter().write(String.format(JSON_VALUE, key, message));
    }
}