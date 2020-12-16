package com.strelnik.spring.controller;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RestController
public class UserRestController {

    private static final Logger logger = Logger.getLogger(SoftwareRestController.class);

    @GetMapping("/user/download/{name}")
    public ResponseEntity<?> download(@PathVariable(name ="name")String name) throws IOException {

        File file = new File("C:\\SoftwareList\\SoftwareList\\files\\"+name);
        if(!file.exists()){
        Files.copy(file.toPath(),new File("C:\\SoftwareList\\SoftwareList\\download"+File.separator,file.getName()).toPath());}
        logger.debug("downloaded");
        return new ResponseEntity(HttpStatus.OK);
    }
}
