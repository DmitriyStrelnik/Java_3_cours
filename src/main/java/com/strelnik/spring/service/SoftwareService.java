package com.strelnik.spring.service;

import com.strelnik.spring.bean.Software;
import com.strelnik.spring.exception.RepositoryException;
import com.strelnik.spring.exception.ServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public interface SoftwareService {
    @Transactional
    void setSoftwareById(
            Long id,
            String name,
            String description
           ) throws ServiceException, RepositoryException;
    @Transactional
    void deleteById(long id)throws ServiceException;
    Software create(Software software)throws ServiceException;
    boolean existsByName(String name)throws ServiceException;
    Software getByName(String name)throws ServiceException;
    List<Software> getAll()throws ServiceException;


}
