package com.kiburu.pagination.controller;

import com.kiburu.pagination.model.Employee;
import com.kiburu.pagination.model.EmployeePage;
import com.kiburu.pagination.model.EmployeeSearchCriteria;
import com.kiburu.pagination.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Alex Kiburu
 */
@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<Page<Employee>> getEmployees(EmployeePage employeePage,
                                                       EmployeeSearchCriteria employeeSearchCriteria){
        return new ResponseEntity<>(
                employeeService.getEmployees(employeePage, employeeSearchCriteria),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee){
        return new ResponseEntity<>(employeeService.addEmployee(employee), HttpStatus.OK);
    }
}
