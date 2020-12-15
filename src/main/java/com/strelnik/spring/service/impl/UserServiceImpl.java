package com.strelnik.spring.service.impl;

import com.strelnik.spring.bean.UserRole;
import com.strelnik.spring.bean.User;
import com.strelnik.spring.bean.dto.Role;
import com.strelnik.spring.exception.ServiceException;
import com.strelnik.spring.repository.UserRoleRepository;
import com.strelnik.spring.repository.UserRepository;
import com.strelnik.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MailSenderImpl mailSender;

    public User saveUser(User user) {
        UserRole userRole = userRoleRepository.findByName(Role.ROLE_USER);
        user.setUserRole(userRole);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActivationCode(UUID.randomUUID().toString());

        user.setActive(false);
        String message = String.format(
                "Hello %s!\n" +
                        "activate your code , need to visit http://localhost:8080/activate/%s",
                user.getLogin(),
                user.getActivationCode()
        );
        mailSender.send(user.getEmail(), "Activation code", message);
        return userRepository.save(user);
    }

    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public User findByLoginAndPassword(String login, String password) {
        User user = findByLogin(login);
        if (user != null) {
            if (passwordEncoder.matches(password, user.getPassword())) {
                return user;
            }
        }
        return null;
    }

    @Override
    public User getById(Long id) {
        return userRepository.getById(id);
    }

    @Override
    public boolean existsUserByLogin(String login) {
        return userRepository.existsUserByLogin(login);
    }

    public boolean activateUser(String code) throws ServiceException {
        User user = userRepository.findByActivationCode(code);

        if (user == null) {
            return false;
        }
        user.setActivationCode(null);
        user.setActive(true);
        userRepository.save(user);

        return true;
    }
}
