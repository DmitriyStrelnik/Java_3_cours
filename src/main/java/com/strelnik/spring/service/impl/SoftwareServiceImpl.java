package com.strelnik.spring.service.impl;

import com.strelnik.spring.bean.Software;
import com.strelnik.spring.exception.RepositoryException;
import com.strelnik.spring.exception.ServiceException;
import com.strelnik.spring.repository.SoftWareRepository;
import com.strelnik.spring.service.SoftwareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SoftwareServiceImpl implements SoftwareService {
    @Autowired
    private SoftWareRepository softWareRepository;
    @Override
    public void setSoftwareById(Long id, String name, String description) throws ServiceException {
        try {
            softWareRepository.setSoftwareById(id, name, description);
        } catch (RepositoryException e) {
            throw new  ServiceException(e);
        }
    }

    @Override
    public void deleteById(long id) throws ServiceException {
        try {
            softWareRepository.deleteById(id);
        } catch (RepositoryException e) {
            throw new  ServiceException(e);
        }
    }

    @Override
    public Software create(Software software) throws ServiceException{
        return softWareRepository.save(software);
    }

    @Override
    public boolean existsByName(String name) throws ServiceException {
        try {
            return softWareRepository.existsByName(name);
        } catch (RepositoryException e) {
            throw new  ServiceException(e);
        }
    }

    @Override
    public Software getByName(String name) throws ServiceException {
        try {
            return softWareRepository.getByName(name);
        } catch (RepositoryException e) {
            throw new  ServiceException(e);
        }
    }

    @Override
    public List<Software> getAll() throws ServiceException{
        return softWareRepository.findAll();
    }
}
