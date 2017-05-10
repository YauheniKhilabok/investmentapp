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
        setProgramsToProgramsDto(employee, employeeDto);
        return employeeDto;
    }

    private DepartmentDto convertDepartmentToDepartmentDto(Department department) {
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setId(department.getId());
        departmentDto.setName(department.getName());
        return departmentDto;
    }

    private void setProgramsToProgramsDto(Employee employee, EmployeeDto employeeDto) {
        Set<Program> programSet = employee.getPrograms();
        Set<ProgramDto> programDtoSet = new HashSet<>();
        for (Program program : programSet) {
            ProgramDto programDto = convertProgramToProgramDto(program);
            programDtoSet.add(programDto);
        }
        employeeDto.setPrograms(programDtoSet);
    }

    private ProgramDto convertProgramToProgramDto(Program program) {
        ProgramDto programDto = new ProgramDto();
        programDto.setId(program.getId());
        programDto.setName(program.getName());
        return programDto;
    }
}
