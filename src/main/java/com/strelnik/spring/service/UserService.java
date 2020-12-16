package com.strelnik.spring.service;

import com.strelnik.spring.bean.User;
import com.strelnik.spring.exception.ServiceException;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User saveUser(User user)throws ServiceException;
    User findByLogin(String login)throws ServiceException;
    User findByLoginAndPassword(String login, String password)throws ServiceException;
    User getById(Long id)throws ServiceException;
    boolean existsUserByLogin(String login)throws ServiceException;
    boolean existsUserByLoginAndPassword(String login, String password)throws ServiceException;
}
