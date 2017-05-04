package com.epam.invpol.service;

import com.epam.invpol.domain.Employee;
import org.springframework.data.domain.Page;

public interface EmployeeService {

    Employee save(Employee employee);

    Employee update(Employee employee);

    void remove(Employee employee);

    Employee getById(long id);

    Page<Employee> getEmployees(int pageNumber, int limit);
}
