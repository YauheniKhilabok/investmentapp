package com.epam.invpol.controller;

import com.epam.invpol.domain.Employee;
import com.epam.invpol.dto.EmployeeDto;
import com.epam.invpol.service.EmployeeService;
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
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    private final ConversionService conversionService;

    private final Converter<Employee, EmployeeDto> employeeConverter;

    @Autowired
    public EmployeeController(EmployeeService employeeService, ConversionService conversionService, Converter<Employee, EmployeeDto> employeeConverter) {
        this.employeeService = employeeService;
        this.conversionService = conversionService;
        this.employeeConverter = employeeConverter;
    }

    @PostMapping
    public ResponseEntity<EmployeeDto> saveEmployee(@Valid @RequestBody EmployeeDto employeeDto) {
        Employee employee = conversionService.convert(employeeDto, Employee.class);
        Employee createdEmployee = employeeService.save(employee);
        EmployeeDto createdEmployeeDto = conversionService.convert(createdEmployee, EmployeeDto.class);
        return new ResponseEntity<>(createdEmployeeDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable("id") long id, @Valid @RequestBody EmployeeDto employeeDto) {
        employeeDto.setId(id);
        Employee employee = conversionService.convert(employeeDto, Employee.class);
        Employee updatedEmployee = employeeService.update(employee);
        EmployeeDto updatedEmployeeDto = conversionService.convert(updatedEmployee, EmployeeDto.class);
        return new ResponseEntity<>(updatedEmployeeDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity removeEmployee(@PathVariable("id") long id) {
        Employee employee = new Employee();
        employee.setId(id);
        employeeService.remove(employee);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getConcreteEmployee(@PathVariable("id") long id) {
        Employee employee = employeeService.getById(id);
        EmployeeDto employeeDto = conversionService.convert(employee, EmployeeDto.class);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<EmployeeDto>> getEmployees(@RequestParam(name = "page", required = false, defaultValue = "1") int pageNumber,
                                                          @RequestParam(name = "limit", required = false, defaultValue = "50") int limit,
                                                          @RequestParam(name = "surname", required = false) String surname) {
        Page<Employee> employees = employeeService.getEmployees(pageNumber, limit, surname);
        return new ResponseEntity<>(employees.map(employeeConverter), HttpStatus.OK);
    }
}
