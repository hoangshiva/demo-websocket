package com.example.demowebsocket.repository;

import com.example.demowebsocket.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findFirstByName(String name);
}
