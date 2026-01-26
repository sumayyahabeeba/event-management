package com.example.project.repository;

import java.util.List;
import java.util.Optional;

import com.example.project.model.Employee;

public interface  EmployeeRepository{
    List<Employee> findAll();
    Optional<Employee> findById(Long id);
    Optional<Employee> findByEmail(String email);
    Employee save(Employee employee);
    void deleteById(Long id);   
}
