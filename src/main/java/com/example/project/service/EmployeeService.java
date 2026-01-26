package com.example.project.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.project.model.Employee;
import com.example.project.repository.EmployeeRepository;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
     // Create / Save Employee
    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }
    // Get all employees
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
    // Get employee by ID (safe way)
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
    }
    
    // Get employee by Email
    public Employee getEmployeeByEmail(String email) {
        return employeeRepository.findByEmail(email)
                .orElseThrow(() -> 
                    new RuntimeException("Employee not found with email: " + email)
                );
    }
    // Update Employee
    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        Employee existingEmployee = getEmployeeById(id);
        existingEmployee.setName(updatedEmployee.getName());
        existingEmployee.setEmail(updatedEmployee.getEmail());
        existingEmployee.setDepartment(updatedEmployee.getDepartment());
        return employeeRepository.save(existingEmployee);
    }
    // Delete Employee
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
}