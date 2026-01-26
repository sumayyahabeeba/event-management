package com.example.project.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.project.model.Employee;
import com.example.project.repository.EmployeeJpaRepository;

@Service
public class EmployeeService {

    private final EmployeeJpaRepository employeeJpaRepository;

    public EmployeeService(EmployeeJpaRepository employeeJpaRepository) {
        this.employeeJpaRepository = employeeJpaRepository;
    }

    // Create Employee
    public Employee createEmployee(Employee employee) {
        return employeeJpaRepository.save(employee);
    }

    // Get all employees
    public List<Employee> getAllEmployees() {
        return employeeJpaRepository.findAll();
    }

    // Get employee by ID
    public Employee getEmployeeById(Long id) {
        return employeeJpaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Employee not found with id: " + id));
    }

    // Get employee by Email
    public Employee getEmployeeByEmail(String email) {
        return employeeJpaRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Employee not found with email: " + email));
    }

    // Update Employee
    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        Employee existingEmployee = getEmployeeById(id);
        existingEmployee.setName(updatedEmployee.getName());
        existingEmployee.setEmail(updatedEmployee.getEmail());
        existingEmployee.setDepartment(updatedEmployee.getDepartment());
        return employeeJpaRepository.save(existingEmployee);
    }

    // Delete Employee
    public void deleteEmployee(Long id) {
        if (!employeeJpaRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Employee not found with id: " + id);
        }
        employeeJpaRepository.deleteById(id);
    }
}
