package com.kiburu.pagination.model;

import lombok.Data;
import org.springframework.data.domain.Sort;

/**
 * @author Alex Kiburu
 */
@Data
public class EmployeePage {
    private int pageNumber = 0;
    private int pageSize = 10;
    private Sort.Direction sortDirection = Sort.Direction.ASC;
    private String sortBy="lastName";
}
