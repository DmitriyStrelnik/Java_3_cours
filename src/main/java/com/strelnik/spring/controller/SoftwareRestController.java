package com.strelnik.spring.controller;

import com.strelnik.spring.bean.Software;
import com.strelnik.spring.controller.dto.NameRequest;
import com.strelnik.spring.controller.dto.FileSoftRequest;
import com.strelnik.spring.controller.dto.SoftwareRequest;
import com.strelnik.spring.exception.ControllerException;
import com.strelnik.spring.exception.ServiceException;
import com.strelnik.spring.repository.SoftWareRepository;
import com.strelnik.spring.service.impl.SoftwareServiceImpl;
import com.strelnik.spring.validator.SoftwareValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.List;

@RestController
public class SoftwareRestController {
    @Autowired
    private SoftwareServiceImpl softwareService;
    @Autowired
    private SoftwareValidator softwareValidator;
    @Autowired
    private SoftWareRepository softwareRepository;

    private static final Logger logger = Logger.getLogger(SoftwareRestController.class);


    @PutMapping("/admin/updateSoftware")
    public ResponseEntity<?> update(@RequestBody FileSoftRequest fileSoftRequest) throws ControllerException {
        try {
            softwareService.setSoftwareById(
                    fileSoftRequest.getId(),
                    fileSoftRequest.getName(),
                    fileSoftRequest.getDescription()
            );

            return new ResponseEntity<>(
                   softwareRepository.getById(fileSoftRequest.getId()), HttpStatus.OK);
        } catch (ServiceException e) {
            logger.error("admin update");
            throw new ControllerException(e);
        }
    }

    @DeleteMapping("/admin/deleteSoftware")
    public ResponseEntity<?> delete(@RequestBody NameRequest nameRequest) throws ControllerException {
        try {
            Software software = softwareService.getByName(nameRequest.getName());


            if (softwareService.existsByName(nameRequest.getName())) {
            softwareService.deleteById(software.getId());
            return new ResponseEntity<>(software, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(software, HttpStatus.FOUND);
        }
        } catch (ServiceException e) {
            logger.error("admin delkete");
            throw new ControllerException(e);
    }
    }

    @PostMapping("/admin/createSoftware")
    public ResponseEntity<?> create(@RequestBody @Validated SoftwareRequest softwareRequest, BindingResult bindingResult) throws ControllerException {
        try {
            File file = softwareRequest.getFile();
            System.out.println(file.getName());
            if (!bindingResult.hasErrors()) {

               Software soft = softwareService.create(new Software(
                        softwareRequest.getName(),
                        softwareRequest.getDescription(),
                        softwareRequest.getFile().getName()
                ));

                  softwareValidator.validate(soft, bindingResult);
                if (!bindingResult.hasErrors()) {
                    return new ResponseEntity<>(soft,HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(soft,HttpStatus.BAD_REQUEST);
                }
            } else {
                logger.error("bad data");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (ServiceException e) {
            logger.error("admin create");
            throw new ControllerException(e);
        }
    }

    @PostMapping("/admin/isSoftwareExist")
    public ResponseEntity<?> isExist(@RequestBody NameRequest nameRequest) throws ControllerException, ServiceException {


        Software soft = softwareService.getByName(nameRequest.getName());
        try {
            if (softwareService.existsByName(nameRequest.getName())) {
                return new ResponseEntity<>(soft,HttpStatus.FOUND);
            } else {
                return new ResponseEntity<>(soft,HttpStatus.OK);
            }
        } catch (ServiceException e) {
            logger.error("admin exist");
            throw new ControllerException(e);
        }
    }


    @GetMapping("/admin/getSoftware/{name}")
    public ResponseEntity<?> get(@PathVariable(name = "name") String name) throws ControllerException {
        Software soft = null;
        try {
            soft = softwareService.getByName(name);

            return new ResponseEntity<>(soft, HttpStatus.OK);
        } catch (ServiceException e) {
            logger.error("admin get");
            throw new ControllerException(e);
        }
    }

    @GetMapping("/getAllSoftware")
    public ResponseEntity<?> getAll() throws ControllerException {
        List<Software> softwareList = null;
        try {
            softwareList = softwareService.getAll();

            return new ResponseEntity<>(softwareList, HttpStatus.OK);
        } catch (ServiceException e) {
            logger.error("admin get all");
            throw new ControllerException(e);
        }
    }
}
