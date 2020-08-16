package com.test.springsecuritytest.services;

import com.test.springsecuritytest.model.UserEntity;
import com.test.springsecuritytest.repository.UserRepository;
import java.security.Security;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public UserEntity findByUserName(String name) {
        return userRepository.findByUserName(name);
    }

    @Transactional(readOnly = true)
    public String getCurrentUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.isAuthenticated()) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        String username = null;
        if (principal == null) {
            username = "游客";
        }
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        }
        return username;
    }
}
