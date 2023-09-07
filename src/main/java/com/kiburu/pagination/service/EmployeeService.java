package com.kiburu.pagination.service;

import com.kiburu.pagination.model.Employee;
import com.kiburu.pagination.model.EmployeePage;
import com.kiburu.pagination.model.EmployeeSearchCriteria;
import com.kiburu.pagination.repository.EmployeeCriteriaRepository;
import com.kiburu.pagination.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 * @author Alex Kiburu
 */
@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeCriteriaRepository employeeCriteriaRepository;

    public Page<Employee> getEmployees(EmployeePage employeePage,
                                       EmployeeSearchCriteria employeeSearchCriteria){
        return employeeCriteriaRepository.findAllWithFilters(employeePage, employeeSearchCriteria);

    }

    public Employee addEmployee(Employee employee){
        return employeeRepository.save(employee);
    }
}
