package com.epam.invpol.dto.converter;

import com.epam.invpol.domain.Investment;
import com.epam.invpol.domain.Program;
import com.epam.invpol.dto.InvestmentDto;
import com.epam.invpol.dto.ProgramDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class ProgramConverter implements Converter<Program, ProgramDto> {

    @Override
    public ProgramDto convert(Program program) {
        ProgramDto programDto = new ProgramDto();
        programDto.setId(program.getId());
        programDto.setName(program.getName());
        programDto.setParticipantsNumber(program.getParticipantsNumber());
        programDto.setParticipantsMaxNumber(program.getParticipantsMaxNumber());
        programDto.setDescription(program.getDescription());
        programDto.setDuration(program.getDuration());
        setInvestmentsToInvestmentsDto(program, programDto);
        return programDto;
    }

    private void setInvestmentsToInvestmentsDto(Program program, ProgramDto programDto) {
        Set<Investment> investmentSet = program.getInvestments();
        Set<InvestmentDto> investmentDtoSet = new HashSet<>();
        for (Investment investment : investmentSet) {
            InvestmentDto investmentDto = convertInvestmentToInvestmentDto(investment);
            investmentDtoSet.add(investmentDto);
        }
        programDto.setInvestments(investmentDtoSet);
    }

    private InvestmentDto convertInvestmentToInvestmentDto(Investment investment) {
        InvestmentDto investmentDto = new InvestmentDto();
        investmentDto.setId(investment.getId());
        investmentDto.setAmount(investment.getAmount());
        return investmentDto;
    }
}
