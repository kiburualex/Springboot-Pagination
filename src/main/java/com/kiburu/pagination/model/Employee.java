package com.kiburu.pagination.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author Alex Kiburu
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="EMPLOYEE")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
}
