package com.epam.invpol.service.impl;

import com.epam.invpol.domain.Program;
import com.epam.invpol.repository.ProgramRepository;
import com.epam.invpol.service.ProgramService;
import com.epam.invpol.service.exception.EntityAlreadyExistException;
import com.epam.invpol.service.exception.EntityNotFoundException;
import com.epam.invpol.service.exception.InvalidLimitException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProgramServiceImpl implements ProgramService {

    @Autowired
    private ProgramRepository programRepository;

    @Autowired
    private ServiceHelper serviceHelper;

    @Override
    @Transactional
    public Program create(Program program) {
        if (program.getId() != null) {
            if (isIdExist(program.getId())) {
                throw new EntityAlreadyExistException("Program with such id already exists.");
            }
        }
        if (isProgramExist(program.getName())) {
            throw new EntityAlreadyExistException("Program with such name already exists.");
        }
        Program savedProgram = programRepository.save(program);
        return programRepository.findOne(savedProgram.getId());
    }

    private boolean isIdExist(long id) {
        Program program = programRepository.findOne(id);
        return program != null;
    }

    @Override
    @Transactional
    public Program update(Program program) {
        getById(program.getId());
        if (isProgramExist(program.getName())) {
            throw new EntityAlreadyExistException("Program with such name already exists.");
        }
        Program updatedProgram = programRepository.save(program);
        return getById(updatedProgram.getId());
    }

    private boolean isProgramExist(String name) {
        Program program = programRepository.findByName(name);
        return program != null;
    }

    @Override
    @Transactional
    public void remove(Program program) {
        getById(program.getId());
        programRepository.delete(program);
    }

    @Override
    public Program getById(long id) {
        Program program = programRepository.findOne(id);
        if (program == null) {
            throw new EntityNotFoundException("Requested program is not found.");
        }
        return program;
    }

    @Override
    public Page<Program> getPrograms(int pageNumber, int limit, String name) {
        if (serviceHelper.checkLimitAllowableSize(limit)) {
            throw new InvalidLimitException("Limit value is not allowed.");
        }
        int offset = pageNumber - 1;
        PageRequest pageRequest = new PageRequest(offset, limit);
        if (name == null) {
            return programRepository.findAll(pageRequest);
        } else {
            return programRepository.findByName(name, pageRequest);
        }
    }
}
