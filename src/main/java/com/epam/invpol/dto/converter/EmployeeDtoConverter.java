package com.epam.invpol.dto.converter;

import com.epam.invpol.domain.Department;
import com.epam.invpol.domain.Employee;
import com.epam.invpol.dto.DepartmentDto;
import com.epam.invpol.dto.EmployeeDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class EmployeeDtoConverter implements Converter<EmployeeDto, Employee> {

    @Override
    public Employee convert(EmployeeDto employeeDto) {
        Employee employee = new Employee();
        employee.setId(employeeDto.getId());
        employee.setName(employeeDto.getName());
        employee.setSurname(employeeDto.getSurname());
        if (employeeDto.getPatronymic() != null) {
            employee.setPatronymic(employeeDto.getPatronymic());
        }
        employee.setPosition(employeeDto.getPosition());
        employee.setDepartment(convertDepartmentDtoToDepartment(employeeDto.getDepartment()));
        return employee;
    }

    private Department convertDepartmentDtoToDepartment(DepartmentDto departmentDto) {
        Department department = new Department();
        department.setId(departmentDto.getId());
        return department;
    }
}
