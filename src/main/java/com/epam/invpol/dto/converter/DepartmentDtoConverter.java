package com.epam.invpol.dto.converter;

import com.epam.invpol.domain.Department;
import com.epam.invpol.dto.DepartmentDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DepartmentDtoConverter implements Converter<DepartmentDto, Department> {

    @Override
    public Department convert(DepartmentDto departmentDto) {
        Department department = new Department();
        department.setId(departmentDto.getId());
        department.setName(departmentDto.getName());
        department.setActivity(departmentDto.getActivity());
        return department;
    }
}
