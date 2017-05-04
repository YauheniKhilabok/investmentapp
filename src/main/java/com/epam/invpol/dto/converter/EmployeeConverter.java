package com.epam.invpol.dto.converter;

import com.epam.invpol.domain.Department;
import com.epam.invpol.domain.Employee;
import com.epam.invpol.dto.DepartmentDto;
import com.epam.invpol.dto.EmployeeDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
public class EmployeeConverter implements Converter<Employee, EmployeeDto> {

    @Override
    public EmployeeDto convert(Employee employee) {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(employee.getId());
        employeeDto.setName(employee.getName());
        employeeDto.setSurname(employee.getSurname());
        if (employee.getPatronymic() != null) {
            employeeDto.setPatronymic(employee.getPatronymic());
        }
        employeeDto.setPosition(employee.getPosition());
        employeeDto.setDepartment(convertDepartmentToDepartmentDto(employee.getDepartment()));
        return employeeDto;
    }

    private DepartmentDto convertDepartmentToDepartmentDto(Department department) {
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setId(department.getId());
        departmentDto.setName(department.getName());
        return departmentDto;
    }
}
