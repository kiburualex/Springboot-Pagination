package com.kiburu.pagination.repository;

import com.kiburu.pagination.model.Employee;
import com.kiburu.pagination.model.EmployeePage;
import com.kiburu.pagination.model.EmployeeSearchCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Alex Kiburu
 */
@Repository
public class EmployeeCriteriaRepository {
    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public EmployeeCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<Employee> findAllWithFilters(EmployeePage employeePage,
                                             EmployeeSearchCriteria employeeSearchCriteria){

        // create query using builder for employee entity
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
        // specify from which entity you want to retrieve the result
        Root<Employee> employeeRoot = criteriaQuery.from(Employee.class);
        // define predicate, which filter the results
        Predicate predicate = getPredicate(employeeSearchCriteria, employeeRoot);
        criteriaQuery.where(predicate);
        setOrder(employeePage, criteriaQuery, employeeRoot);
        TypedQuery<Employee> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(employeePage.getPageNumber() * employeePage.getPageSize());
        typedQuery.setMaxResults(employeePage.getPageSize());

        Pageable pageable = getPageable(employeePage);

        // now return count using query
        long employeesCount = getEmployeesCount(predicate);
        return new PageImpl<>(typedQuery.getResultList(), pageable, employeesCount);
    }

    private Predicate getPredicate(EmployeeSearchCriteria employeeSearchCriteria,
                                   Root<Employee> employeeRoot) {
        List<Predicate> predicates = new ArrayList<>();
        if(Objects.nonNull(employeeSearchCriteria.getFirstName())){
            predicates.add(
                    criteriaBuilder.like(
                            criteriaBuilder.lower( employeeRoot.get("firstName") ),
                            "%" +employeeSearchCriteria.getFirstName().toLowerCase() + "%"
                    )
            );
        }


        if(Objects.nonNull(employeeSearchCriteria.getLastName())){
            predicates.add(
                    criteriaBuilder.like(
                            criteriaBuilder.lower( employeeRoot.get("lastName")),
                            "%" +employeeSearchCriteria.getLastName().toLowerCase() + "%"
                    )
            );
        }

        // merge the predicates into one predicate with size 1
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private void setOrder(EmployeePage employeePage,
                          CriteriaQuery<Employee> criteriaQuery,
                          Root<Employee> employeeRoot) {
        if(employeePage.getSortDirection().equals(Sort.Direction.ASC)){
            criteriaQuery.orderBy(
                    criteriaBuilder.asc(
                            employeeRoot.get( employeePage.getSortBy() )
                    )
            );
        }else{
            criteriaQuery.orderBy(
                    criteriaBuilder.desc(
                            employeeRoot.get( employeePage.getSortBy() )
                    )
            );
        }
    }

    private Pageable getPageable(EmployeePage employeePage) {
        Sort sort = Sort.by(employeePage.getSortDirection(), employeePage.getSortBy());
        return PageRequest.of(employeePage.getPageNumber(), employeePage.getPageSize(), sort);
    }

    private long getEmployeesCount(Predicate predicate) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Employee> countRoot = countQuery.from(Employee.class);

        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);

        return entityManager.createQuery(countQuery).getSingleResult();
    }
}
