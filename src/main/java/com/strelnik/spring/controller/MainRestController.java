package com.strelnik.spring.controller;

import com.strelnik.spring.bean.User;
import com.strelnik.spring.config.jwt.JwtProvider;
import com.strelnik.spring.controller.dto.AuthRequest;
import com.strelnik.spring.controller.dto.AuthResponse;
import com.strelnik.spring.controller.dto.RegistrationRequest;
import com.strelnik.spring.controller.dto.UserResponse;
import com.strelnik.spring.exception.ControllerException;
import com.strelnik.spring.service.impl.UserServiceImpl;
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

    @PostMapping("/login")
    public ResponseEntity<?> auth(@RequestBody AuthRequest request) throws ControllerException {

        User user = userServiceImpl.findByLoginAndPassword(request.getLogin(), request.getPassword());
        if (user != null && user.isActive()) {
            String token = jwtProvider.generateToken(user.getLogin());
            AuthResponse response = new AuthResponse(token, user.getUserRole().getName());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            throw new ControllerException("not such user");
        }
    }

    @PostMapping("/authorized")
    public ResponseEntity<?> isAuthorized() throws ControllerException {
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/getUser")
    public UserResponse getUser(@RequestHeader(name = "Authorisation") String jwt) throws ControllerException {

        String userName = jwtProvider.getLoginFromToken(jwt.substring(7));
        User user = userServiceImpl.findByLogin(userName);


        return new UserResponse(user.getId(), user.getLogin(), user.getUserRole().getName());
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegistrationRequest registrationRequest) throws ControllerException {

        if (!userServiceImpl.existsUserByLogin(registrationRequest.getLogin())) {
            User u = new User();
            u.setPassword(registrationRequest.getPassword());
            u.setLogin(registrationRequest.getLogin());
            u.setEmail(registrationRequest.getEmail());
            userServiceImpl.saveUser(u);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FOUND);
        }
    }
}
