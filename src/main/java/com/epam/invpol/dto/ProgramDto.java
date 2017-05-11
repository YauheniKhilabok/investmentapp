package com.epam.invpol.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ProgramDto extends DtoObject<Long> implements Serializable{

    @NotNull(message = "{validation.programNameNotNull}")
    @Size(min = 1, max = 100, message = "{validation.programNameLength}")
    private String name;

    @NotNull(message = "{validation.programParticipantsNumberNotNull}")
    private int participantsNumber;

    @NotNull(message = "{validation.programParticipantsMaxNumberNotNull}")
    private int participantsMaxNumber;

    @NotNull(message = "{validation.programDescriptionNotNull}")
    @Size(min = 1, message = "{validation.programDescriptionLength}")
    private String description;

    @NotNull(message = "{validation.programDurationNotNull}")
    private int duration;

    private Set<InvestmentDto> investments = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProgramDto that = (ProgramDto) o;
        return Objects.equals(getId(), that.getId()) &&
                participantsNumber == that.participantsNumber &&
                participantsMaxNumber == that.participantsMaxNumber &&
                duration == that.duration &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, participantsNumber, participantsMaxNumber, description, duration);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParticipantsNumber() {
        return participantsNumber;
    }

    public void setParticipantsNumber(int participantsNumber) {
        this.participantsNumber = participantsNumber;
    }

    public int getParticipantsMaxNumber() {
        return participantsMaxNumber;
    }

    public void setParticipantsMaxNumber(int participantsMaxNumber) {
        this.participantsMaxNumber = participantsMaxNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Set<InvestmentDto> getInvestments() {
        return investments;
    }

    public void setInvestments(Set<InvestmentDto> investments) {
        this.investments = investments;
    }

    @Override
    public String toString() {
        return "ProgramDto{" +
                "id=" + getId() +
                ", duration=" + duration +
                ", name='" + name + '\'' +
                ", participantsNumber=" + participantsNumber +
                ", participantsMaxNumber=" + participantsMaxNumber +
                ", description='" + description + '\'' +
                '}';
    }
}
