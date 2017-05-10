package com.epam.invpol.dto.converter;

import com.epam.invpol.domain.Investment;
import com.epam.invpol.domain.Program;
import com.epam.invpol.dto.InvestmentDto;
import com.epam.invpol.dto.ProgramDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class InvestmentDtoConverter implements Converter<InvestmentDto, Investment> {

    @Override
    public Investment convert(InvestmentDto investmentDto) {
        Investment investment = new Investment();
        investment.setId(investmentDto.getId());
        investment.setAmount(investmentDto.getAmount());
        investment.setProgram(convertProgramDtoToProgram(investmentDto.getProgram()));
        return investment;
    }

    private Program convertProgramDtoToProgram(ProgramDto programDto) {
        Program program = new Program();
        program.setId(programDto.getId());
        return program;
    }
}
