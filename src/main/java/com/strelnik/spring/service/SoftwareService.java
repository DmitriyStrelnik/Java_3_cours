package com.strelnik.spring.service;

import com.strelnik.spring.bean.Software;
import org.springframework.transaction.annotation.Transactional;

public interface SoftwareService {
    @Transactional
    void setSoftwareById(
            Long id,
            String name,
            String description);
    void deleteById(int id);
    Software create(Software software);

}
