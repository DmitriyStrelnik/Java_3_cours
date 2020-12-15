package com.strelnik.spring.repository;

import com.strelnik.spring.bean.Software;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SoftWareRepository extends JpaRepository<Software, Integer> {
    @Modifying
    @Query("update Software s set s.name =:name, s.description=:description where s.id =:id ")
    void setSoftwareById(
            @Param("id") Long id,
            @Param("name") String name,
            @Param("description") String description);
}
