package com.example.websocketdemo.security;

import com.example.websocketdemo.model.CustomUserDetails;
import com.example.websocketdemo.model.User;
import com.example.websocketdemo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service()
public class CustomUserDetailsService implements UserDetailsService {

    Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Autowired
    private UserRepository userRepository;

    public CustomUserDetailsService() {
        super();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Optional<User> optional = userRepository.findOptionalByUsername(username);
            if(optional.isPresent()){
                User user = optional.get();

                return new CustomUserDetails(user, new ArrayList<>());
            }else {
                throw  new UsernameNotFoundException("Not found user with username: " + username);
            }
        } catch (Exception ex) {
            log.error("Exception in CustomUserDetailsService: " + ex);
        }
        return null;
    }
}