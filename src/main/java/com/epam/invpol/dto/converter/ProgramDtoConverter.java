package com.epam.invpol.dto.converter;

import com.epam.invpol.domain.Investment;
import com.epam.invpol.domain.Program;
import com.epam.invpol.dto.InvestmentDto;
import com.epam.invpol.dto.ProgramDto;
import org.springframework.core.convert.converter.Converter;

import java.util.HashSet;
import java.util.Set;

public class ProgramDtoConverter implements Converter<ProgramDto, Program>{

    @Override
    public Program convert(ProgramDto programDto) {
        Program program = new Program();
        program.setId(programDto.getId());
        program.setName(programDto.getName());
        program.setParticipantsNumber(programDto.getParticipantsNumber());
        program.setParticipantsMaxNumber(programDto.getParticipantsMaxNumber());
        program.setDescription(programDto.getDescription());
        program.setDuration(programDto.getDuration());
        setInvestmentsDtoToInvestments(programDto, program);
        return program;
    }

    private void setInvestmentsDtoToInvestments(ProgramDto programDto, Program program) {
        Set<InvestmentDto> investmentDtoSet = programDto.getInvestments();
        Set<Investment> investmentSet = new HashSet<>();
        for (InvestmentDto investmentDto : investmentDtoSet) {
            Investment investment = convertInvestmentDtoToInvestment(investmentDto);
            investmentSet.add(investment);
        }
        program.setInvestments(investmentSet);
    }

    private Investment convertInvestmentDtoToInvestment(InvestmentDto investmentDto) {
        Investment investment = new Investment();
        investment.setId(investmentDto.getId());
        return investment;
    }
}
