package com.epam.invpol.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.Column;
import javax.persistence.FetchType;
import java.io.Serializable;

@Entity
@Table(name = "investments")
public class Investment extends DomainObject<Long> implements Serializable{

    @Column(name = "amount", nullable = false)
    private float amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_program", nullable = false)
    private Program program;

    public Investment() {
    }

    public Investment(Long id, float amount) {
        super(id);
        this.amount = amount;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    @Override
    public String toString() {
        return "Investment{" +
                "id='" + getId() + '\'' +
                ", amount=" + amount +
                '}';
    }
}
