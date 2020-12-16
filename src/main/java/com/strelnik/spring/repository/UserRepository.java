package com.strelnik.spring.repository;

import com.strelnik.spring.bean.User;
import com.strelnik.spring.exception.RepositoryException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByLogin(String login)throws RepositoryException;
    User getById(Long id)throws RepositoryException;
    boolean existsUserByLogin(String login)throws RepositoryException;
    User findByActivationCode(String code)throws RepositoryException;
    boolean existsUserByLoginAndPassword(String login, String password);
}
