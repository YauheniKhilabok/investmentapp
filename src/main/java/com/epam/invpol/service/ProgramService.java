package com.epam.invpol.service;

import com.epam.invpol.domain.Program;
import org.springframework.data.domain.Page;

public interface ProgramService {

    Program create(Program program);

    Program update(Program program);

    void remove(Program program);

    Program getById(long id);

    Page<Program> getPrograms(int pageNumber, int limit, String name);
}
