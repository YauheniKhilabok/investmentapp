package com.epam.invpol.controller;

import com.epam.invpol.domain.Program;
import com.epam.invpol.dto.ProgramDto;
import com.epam.invpol.service.ProgramService;
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

import javax.validation.Valid;

@RestController
@RequestMapping("/programs")
public class ProgramController {

    @Autowired
    private ProgramService programService;

    @Autowired
    private ConversionService conversionService;

    @Autowired
    private Converter<Program, ProgramDto> programConverter;

    @PostMapping
    public ResponseEntity<ProgramDto> createProgram(@Valid @RequestBody ProgramDto programDto) {
        Program program = conversionService.convert(programDto, Program.class);
        Program createdProgram = programService.create(program);
        ProgramDto createdProgramDto = conversionService.convert(createdProgram, ProgramDto.class);
        return new ResponseEntity<>(createdProgramDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProgramDto> updateProgram(@PathVariable("id") long id, @Valid @RequestBody ProgramDto programDto) {
        programDto.setId(id);
        Program program = conversionService.convert(programDto, Program.class);
        Program updatedProgram = programService.update(program);
        ProgramDto updatedProgramDto = conversionService.convert(updatedProgram, ProgramDto.class);
        return new ResponseEntity<>(updatedProgramDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity removeProgram(@PathVariable("id") long id) {
        Program program = new Program();
        program.setId(id);
        programService.remove(program);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProgramDto> getConcreteProgram(@PathVariable("id") long id) {
        Program program = programService.getById(id);
        ProgramDto programDto = conversionService.convert(program, ProgramDto.class);
        return new ResponseEntity<>(programDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<ProgramDto>> getPrograms(@RequestParam(name = "page", required = false, defaultValue = "1") int pageNumber,
                                                        @RequestParam(name = "limit", required = false, defaultValue = "50") int limit,
                                                        @RequestParam(name = "name", required = false) String name) {
        Page<Program> programs = programService.getPrograms(pageNumber, limit, name);
        return new ResponseEntity<>(programs.map(programConverter), HttpStatus.OK);
    }
}
