package com.example.project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project.model.Employee;


public interface  EmployeeJpaRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmail(String email);
}
