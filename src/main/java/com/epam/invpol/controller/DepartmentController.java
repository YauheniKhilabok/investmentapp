package com.epam.invpol.controller;

import com.epam.invpol.domain.Department;
import com.epam.invpol.dto.DepartmentDto;
import com.epam.invpol.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private ConversionService conversionService;

    @Autowired
    private Converter<Department, DepartmentDto> departmentConverter;

    @PostMapping
    public ResponseEntity<DepartmentDto> createDepartment(@Valid @RequestBody DepartmentDto departmentDto) {
        Department department = conversionService.convert(departmentDto, Department.class);
        Department createdDepartment = departmentService.create(department);
        DepartmentDto createdDepartmentDto = conversionService.convert(createdDepartment, DepartmentDto.class);
        return new ResponseEntity<>(createdDepartmentDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartmentDto> updateDepartment(@PathVariable("id") long id, @Valid @RequestBody DepartmentDto departmentDto) {
        departmentDto.setId(id);
        Department department = conversionService.convert(departmentDto, Department.class);
        Department updatedDepartment = departmentService.update(department);
        DepartmentDto updatedDepartmentDto = conversionService.convert(updatedDepartment, DepartmentDto.class);
        return new ResponseEntity<>(updatedDepartmentDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity removeDepartment(@PathVariable("id") long id) {
        Department department = new Department();
        department.setId(id);
        departmentService.remove(department);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDto> getConcreteDepartment(@PathVariable("id") long id) {
        Department department = departmentService.getById(id);
        DepartmentDto departmentDto = conversionService.convert(department, DepartmentDto.class);
        return new ResponseEntity<>(departmentDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<DepartmentDto>> getDepartments(@RequestParam(name = "page", required = false, defaultValue = "1") int pageNumber,
                                                              @RequestParam(name = "limit", required = false, defaultValue = "50") int limit) {
        Page<Department> departments = departmentService.getDepartments(pageNumber, limit);
        return new ResponseEntity<>(departments.map(departmentConverter), HttpStatus.OK);
    }
}
