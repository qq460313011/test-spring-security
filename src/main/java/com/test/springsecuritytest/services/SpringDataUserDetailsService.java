package com.test.springsecuritytest.services;

import com.test.springsecuritytest.model.UserEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SpringDataUserDetailsService implements UserDetailsService {

    private UserService userService;

    public SpringDataUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        1.通过静态数据
//        UserDetails userDetails = User.withUsername("zhangsan").password("123").authorities("p1").build();
//        return userDetails;
        //2.通过数据库进行认证
        UserEntity user = userService.findByUserName(username);
        if (user == null) {
            return null;
        }
        List<String> permissions = new ArrayList<>();
        user.getRoles().forEach(r -> {
            permissions.addAll(r.getPermissions().stream().map(p -> p.getCode()).collect(Collectors.toList()));
        });
        String[] perArray = new String[permissions.size()];
        permissions.toArray(perArray);

        UserDetails userDetails = User.withUsername(user.getUserName()).password(user.getPassword())
                .authorities(perArray).build();

        return userDetails;
    }
}
