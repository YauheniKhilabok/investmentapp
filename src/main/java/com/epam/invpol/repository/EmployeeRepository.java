package com.epam.invpol.repository;

import com.epam.invpol.domain.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    long countByDepartmentId(long departmentId);

    @Query("SELECT e FROM Employee e WHERE LOWER(e.surname) LIKE LOWER(CONCAT('%', :surname, '%'))")
    Page<Employee> findBySurname(@Param("surname") String surname, Pageable pageRequest);
}
