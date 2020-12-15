package com.strelnik.spring.repository;

import com.strelnik.spring.bean.UserRole;
import com.strelnik.spring.bean.dto.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {

    UserRole findByName(Role name);
}
