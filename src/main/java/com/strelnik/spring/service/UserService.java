package com.strelnik.spring.service;

import com.strelnik.spring.bean.User;

public interface UserService {
    User saveUser(User user);
    User findByLogin(String login);
    User findByLoginAndPassword(String login, String password);
    User getById(Long id);
    boolean existsUserByLogin(String login);
}
