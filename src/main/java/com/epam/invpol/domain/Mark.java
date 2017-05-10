package com.epam.invpol.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.Column;
import javax.persistence.FetchType;
import java.io.Serializable;

@Entity
@Table(name = "marks")
public class Mark extends DomainObject<Long> implements Serializable{

    @Column(name = "mark", nullable = false)
    private double mark;

    @Column(name = "is_effective", nullable = false)
    private boolean isEffective;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_program", nullable = false)
    private Program program;

    public Mark() {

    }

    public Mark(Long id, double mark, boolean isEffective) {
        super(id);
        this.mark = mark;
        this.isEffective = isEffective;
    }

    public double getMark() {
        return mark;
    }

    public void setMark(double mark) {
        this.mark = mark;
    }

    public boolean isEffective() {
        return isEffective;
    }

    public void setEffective(boolean effective) {
        isEffective = effective;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    @Override
    public String toString() {
        return "Mark{" +
                "id=" + getId() +
                ", mark=" + mark +
                ", isEffective=" + isEffective +
                ", program=" + program +
                '}';
    }
}
