package com.test.springsecuritytest.repository;


import com.test.springsecuritytest.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends
        JpaRepository<UserEntity, Long> {

    UserEntity findByUserName(String username);
}
