package com.epam.invpol.service;

import com.epam.invpol.domain.Department;
import org.springframework.data.domain.Page;

public interface DepartmentService {

    Department create(Department department);

    Department update(Department department);

    void remove(Department department);

    Department getById(long id);

    Page<Department> getDepartments(int pageNumber, int limit);
}
