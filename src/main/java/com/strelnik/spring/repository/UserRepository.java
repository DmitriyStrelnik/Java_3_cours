package com.strelnik.spring.repository;

import com.strelnik.spring.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByLogin(String login);
    User getById(Long id);
    boolean existsUserByLogin(String login);
    User findByActivationCode(String code);
}
