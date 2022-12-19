package com.example.spring_boot_restcontroller.repository;

import com.example.spring_boot_restcontroller.entity.Role;
import com.example.spring_boot_restcontroller.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}