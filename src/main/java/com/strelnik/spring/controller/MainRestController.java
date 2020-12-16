package com.strelnik.spring.controller;

import com.strelnik.spring.bean.User;
import com.strelnik.spring.config.jwt.JwtProvider;
import com.strelnik.spring.controller.dto.AuthRequest;
import com.strelnik.spring.controller.dto.AuthResponse;
import com.strelnik.spring.controller.dto.RegistrationRequest;
import com.strelnik.spring.controller.dto.UserResponse;
import com.strelnik.spring.exception.ControllerException;
import com.strelnik.spring.exception.ServiceException;
import com.strelnik.spring.service.impl.UserServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainRestController {


    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    private JwtProvider jwtProvider;

    private static final Logger logger = Logger.getLogger(MainRestController.class);
    @PostMapping("/login")
    public ResponseEntity<?> auth(@RequestBody AuthRequest request) throws ControllerException {

        User user = null;
        try {
            user = userServiceImpl.findByLoginAndPassword(request.getLogin(), request.getPassword());
            if (user != null && user.isActive()) {
                String token = jwtProvider.generateToken(user.getLogin());
                AuthResponse response = new AuthResponse(token, user.getUserRole().getName());
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                throw new ControllerException("not such user");
            }
        } catch (ServiceException e) {
            logger.error("in login ");
            throw new ControllerException(e);
        }
    }

    @PostMapping("/authorized")
    public ResponseEntity<?> isAuthorized() throws ControllerException {
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/getUser")
    public UserResponse getUser(@RequestHeader(name = "Authorisation") String jwt) throws ControllerException {

        String userName = jwtProvider.getLoginFromToken(jwt.substring(7));
        User user = null;
        try {
            user = userServiceImpl.findByLogin(userName);
            logger.debug("get user by token");
            return new UserResponse(user.getId(), user.getLogin(), user.getUserRole().getName());
        } catch (ServiceException e) {
            logger.error("in get user ");
            throw new ControllerException(e);
        }


    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegistrationRequest registrationRequest) throws ControllerException {

        try {
            if (!userServiceImpl.existsUserByLogin(registrationRequest.getLogin())) {
                User u = new User();
                u.setPassword(registrationRequest.getPassword());
                u.setLogin(registrationRequest.getLogin());
                u.setEmail(registrationRequest.getEmail());
                userServiceImpl.saveUser(u);
                logger.debug("user reged ");
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.FOUND);
            }
        } catch (ServiceException e) {
            logger.error("in reg ");
            throw new ControllerException(e);
        }
    }
}
