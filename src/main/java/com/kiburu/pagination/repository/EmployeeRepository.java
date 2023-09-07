package com.kiburu.pagination.repository;

import com.kiburu.pagination.model.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Alex Kiburu
 */
@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {

}
