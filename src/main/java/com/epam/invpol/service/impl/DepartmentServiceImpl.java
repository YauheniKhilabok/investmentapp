package com.epam.invpol.service.impl;

import com.epam.invpol.domain.Department;
import com.epam.invpol.repository.DepartmentRepository;
import com.epam.invpol.repository.EmployeeRepository;
import com.epam.invpol.service.DepartmentService;
import com.epam.invpol.service.exception.DeleteOperationException;
import com.epam.invpol.service.exception.EntityAlreadyExistException;
import com.epam.invpol.service.exception.EntityNotFoundException;
import com.epam.invpol.service.exception.InvalidLimitException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private ServiceHelper serviceHelper;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    @Transactional
    public Department create(Department department) {
        if (department.getId() != null) {
            if (isIdExist(department.getId())) {
                throw new EntityAlreadyExistException("Department with such id already exists.");
            }
        }
        if (isDepartmentExist(department.getName())) {
            throw new EntityAlreadyExistException("Department with such name already exists.");
        }
        Department savedDepartment = departmentRepository.save(department);
        return departmentRepository.findOne(savedDepartment.getId());
    }

    private boolean isIdExist(long id) {
        Department department = departmentRepository.findOne(id);
        return department != null;
    }

    @Override
    @Transactional
    public Department update(Department department) {
        getById(department.getId());
        if (isDepartmentExist(department.getName())) {
            throw new EntityAlreadyExistException("Department with such name already exists.");
        }
        Department updatedDepartment = departmentRepository.save(department);
        return getById(updatedDepartment.getId());
    }

    private boolean isDepartmentExist(String name) {
        Department department = departmentRepository.findByName(name);
        return department != null;
    }

    @Override
    @Transactional
    public void remove(Department department) {
        getById(department.getId());
        if (isNoEmployeesInDepartment(department.getId())) {
            throw new DeleteOperationException("This department has employees. So it can not be deleted.");
        }
        departmentRepository.delete(department);
    }

    private boolean isNoEmployeesInDepartment(long departmentId) {
        int emptyDepartmentSize = 0;
        return employeeRepository.countByDepartmentId(departmentId) > emptyDepartmentSize;
    }

    @Override
    public Department getById(long id) {
        Department department = departmentRepository.findOne(id);
        if (department == null) {
            throw new EntityNotFoundException("Requested department is not found.");
        }
        return department;
    }

    @Override
    public Page<Department> getDepartments(int pageNumber, int limit) {
        if (serviceHelper.checkLimitAllowableSize(limit)) {
            throw new InvalidLimitException("Limit value is not allowed.");
        }
        int offset = pageNumber - 1;
        PageRequest pageRequest = new PageRequest(offset, limit);
        return departmentRepository.findAll(pageRequest);
    }
}
