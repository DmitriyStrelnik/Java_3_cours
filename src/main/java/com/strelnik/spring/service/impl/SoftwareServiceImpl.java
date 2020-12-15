package com.strelnik.spring.service.impl;

import com.strelnik.spring.bean.Software;
import com.strelnik.spring.repository.SoftWareRepository;
import com.strelnik.spring.service.SoftwareService;

public class SoftwareServiceImpl implements SoftwareService {
    private SoftWareRepository softWareRepository;
    @Override
    public void setSoftwareById(Long id, String name, String description) {
        softWareRepository.setSoftwareById(id, name, description);
    }

    @Override
    public void deleteById(int id) {
        softWareRepository.deleteById(id);
    }

    @Override
    public Software create(Software software) {
        return softWareRepository.save(software);
    }
}
