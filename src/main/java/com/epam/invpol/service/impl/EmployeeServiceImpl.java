package com.epam.invpol.service.impl;

import com.epam.invpol.domain.Employee;
import com.epam.invpol.domain.Program;
import com.epam.invpol.repository.EmployeeRepository;
import com.epam.invpol.repository.ProgramRepository;
import com.epam.invpol.service.EmployeeService;
import com.epam.invpol.service.exception.EntityAlreadyExistException;
import com.epam.invpol.service.exception.EntityNotFoundException;
import com.epam.invpol.service.exception.InvalidLimitException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ProgramRepository programRepository;

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
        checkOnExistence(employee);
        Employee savedEmployee = employeeRepository.save(employee);
        updateParticipantsNumber(true, employee);
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
        checkOnExistence(employee);
        Employee updatedEmployee = employeeRepository.save(employee);
        return getById(updatedEmployee.getId());
    }

    private void checkOnExistence(Employee employee) {
        Set<Program> programs = employee.getPrograms();
        if (!programs.isEmpty()) {
            createExceptionIfEntitiesNotExist(programs);
        }
    }

    private void createExceptionIfEntitiesNotExist(Set<Program> programs) {
        List<String> errorMessages = new ArrayList<>();
        fillProgramsErrorMessages(programs, errorMessages);
        if (!errorMessages.isEmpty()) {
            throw new EntityNotFoundException(errorMessages);
        }
    }

    private void fillProgramsErrorMessages(Set<Program> programs, List<String> errorMessages) {
        for (Program program : programs) {
            Program foundProgram = programRepository.findOne(program.getId());
            boolean flag = foundProgram == null;
            if (flag) {
                String errorMessage = "Program with id #" + program.getId() + " is not exist.";
                errorMessages.add(errorMessage);
            }
        }
    }

    @Override
    @Transactional
    public void remove(Employee employee) {
        getById(employee.getId());
        updateParticipantsNumber(false, employee);
        employeeRepository.delete(employee);
    }

    private void updateParticipantsNumber(boolean flag, Employee employee) {
        Set<Program> programs = employee.getPrograms();
        if (programs.size() == 0) {
            Employee foundEmployee = employeeRepository.getOne(employee.getId());
            programs = foundEmployee.getPrograms();
        }
        for (Program program : programs) {
            Program foundProgram = programRepository.findOne(program.getId());
            int participantsNumber = foundProgram.getParticipantsNumber();
            if (flag) {
                participantsNumber++;
            } else {
                participantsNumber--;
            }
            foundProgram.setParticipantsNumber(participantsNumber);
            programRepository.save(foundProgram);
        }
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
    public Page<Employee> getEmployees(int pageNumber, int limit, String surname) {
        if (serviceHelper.checkLimitAllowableSize(limit)) {
            throw new InvalidLimitException("Limit value is not allowed.");
        }
        int offset = pageNumber - 1;
        PageRequest pageRequest = new PageRequest(offset, limit);
        if (surname == null) {
            return employeeRepository.findAll(pageRequest);
        } else {
            return employeeRepository.findBySurname(surname, pageRequest);
        }
    }
}
