package com.epam.invpol.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

public class InvestmentDto extends DtoObject<Long> implements Serializable{

    @NotNull(message = "{validation.investmentAmountNotNull}")
    private float amount;

    @NotNull(message = "{validation.programNotNull}")
    private ProgramDto program;

    public InvestmentDto() {
    }

    public InvestmentDto(Long id, float amount) {
        super(id);
        this.amount = amount;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public ProgramDto getProgram() {
        return program;
    }

    public void setProgram(ProgramDto program) {
        this.program = program;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvestmentDto that = (InvestmentDto) o;
        return Objects.equals(getId(), that.getId()) &&
                Float.compare(that.amount, amount) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), amount);
    }
}
