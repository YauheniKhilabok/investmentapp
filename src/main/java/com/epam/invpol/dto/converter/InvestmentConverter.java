package com.epam.invpol.dto.converter;

import com.epam.invpol.domain.Investment;
import com.epam.invpol.domain.Program;
import com.epam.invpol.dto.InvestmentDto;
import com.epam.invpol.dto.ProgramDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class InvestmentConverter implements Converter<Investment, InvestmentDto> {

    @Override
    public InvestmentDto convert(Investment investment) {
        InvestmentDto investmentDto = new InvestmentDto();
        investmentDto.setId(investment.getId());
        investmentDto.setAmount(investment.getAmount());
        investmentDto.setProgram(convertProgramToProgramDto(investment.getProgram()));
        return investmentDto;
    }

    private ProgramDto convertProgramToProgramDto(Program program) {
        ProgramDto programDto = new ProgramDto();
        programDto.setId(program.getId());
        programDto.setName(program.getName());
        return programDto;
    }
}
