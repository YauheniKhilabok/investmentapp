package com.epam.invpol.dto.converter;

import com.epam.invpol.domain.Department;
import com.epam.invpol.dto.DepartmentDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DepartmentConverter implements Converter<Department, DepartmentDto> {

    @Override
    public DepartmentDto convert(Department department) {
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setId(department.getId());
        departmentDto.setName(department.getName());
        departmentDto.setActivity(department.getActivity());
        return departmentDto;
    }
}
