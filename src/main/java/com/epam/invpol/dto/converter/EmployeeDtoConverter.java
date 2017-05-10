package com.epam.invpol.dto.converter;

import com.epam.invpol.domain.Department;
import com.epam.invpol.domain.Employee;
import com.epam.invpol.domain.Program;
import com.epam.invpol.dto.DepartmentDto;
import com.epam.invpol.dto.EmployeeDto;
import com.epam.invpol.dto.ProgramDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

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
        setProgramsDtoToPrograms(employeeDto, employee);
        return employee;
    }

    private Department convertDepartmentDtoToDepartment(DepartmentDto departmentDto) {
        Department department = new Department();
        department.setId(departmentDto.getId());
        return department;
    }

    private void setProgramsDtoToPrograms(EmployeeDto employeeDto, Employee employee) {
        Set<ProgramDto> programDtoSet = employeeDto.getPrograms();
        Set<Program> programSet = new HashSet<>();
        for (ProgramDto programDto : programDtoSet) {
            Program program = convertProgramDtoToProgram(programDto);
            programSet.add(program);
        }
        employee.setPrograms(programSet);
    }

    private Program convertProgramDtoToProgram(ProgramDto programDto) {
        Program program = new Program();
        program.setId(programDto.getId());
        return program;
    }
}
