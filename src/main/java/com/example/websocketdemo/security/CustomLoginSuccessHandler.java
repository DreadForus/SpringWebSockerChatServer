package com.example.websocketdemo.security;

import com.example.websocketdemo.model.User;
import com.example.websocketdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        User user = userRepository.findByUsername(authentication.getName());

        response.setStatus(HttpStatus.OK.value());

//        response.setContentType("application/json;charset=UTF-8");
//        response.setHeader("Cache-Control", "no-cache");
//        response.getWriter().write(JsonConverter.convertToJson(user));

        sendJsonResponse(response, "success", "true");
//        response.sendRedirect(request.getContextPath() + "/secured/success");
    }


    public void sendJsonResponse(HttpServletResponse response, String key, String message) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        String JSON_VALUE = "{\"%s\": %s}";

        response.getWriter().write(String.format(JSON_VALUE, key, message));
    }
}