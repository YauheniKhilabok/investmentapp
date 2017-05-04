package com.epam.invpol.service.impl;

import com.epam.invpol.domain.Employee;
import com.epam.invpol.repository.EmployeeRepository;
import com.epam.invpol.service.EmployeeService;
import com.epam.invpol.service.exception.EntityAlreadyExistException;
import com.epam.invpol.service.exception.EntityNotFoundException;
import com.epam.invpol.service.exception.InvalidLimitException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ServiceHelper serviceHelper;

    @Override
    @Transactional
    public Employee save(Employee employee) {
        if (employee.getId() != null) {
            if (isIdExist(employee.getId())) {
                throw new EntityAlreadyExistException("Employee with such id already exists.");
            }
        }
        Employee savedEmployee = employeeRepository.save(employee);
        return employeeRepository.findOne(savedEmployee.getId());
    }

    private boolean isIdExist(long id) {
        Employee employee = employeeRepository.findOne(id);
        return employee != null;
    }

    @Override
    @Transactional
    public Employee update(Employee employee) {
        getById(employee.getId());
        Employee updatedEmployee = employeeRepository.save(employee);
        return getById(updatedEmployee.getId());
    }

    @Override
    @Transactional
    public void remove(Employee employee) {
        getById(employee.getId());
        employeeRepository.delete(employee);
    }

    @Override
    public Employee getById(long id) {
        Employee employee = employeeRepository.findOne(id);
        if (employee == null) {
            throw new EntityNotFoundException("Requested employee is not found.");
        }
        return employee;
    }

    @Override
    public Page<Employee> getEmployees(int pageNumber, int limit) {
        if (serviceHelper.checkLimitAllowableSize(limit)) {
            throw new InvalidLimitException("Limit value is not allowed.");
        }
        int offset = pageNumber - 1;
        PageRequest pageRequest = new PageRequest(offset, limit);
        return employeeRepository.findAll(pageRequest);
    }

}
