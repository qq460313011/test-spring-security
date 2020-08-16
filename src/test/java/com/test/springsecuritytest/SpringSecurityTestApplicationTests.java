package com.test.springsecuritytest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCrypt;


@SpringBootTest
class SpringSecurityTestApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void test_BCrypt() {
        //对原始密码加密
        String hashpwd= BCrypt.hashpw("123",BCrypt.gensalt());
        System.out.println(hashpwd);

        //校验原始密码和BCrypt密码是否一致
        boolean check =BCrypt.checkpw("123","$2a$10$Jw5RfdJPpu04wEpfw.AKl.EGnvMKu0uJ9C8mVutrNPJ7bCVaDBuri");

        System.out.println(check);
    }
}
