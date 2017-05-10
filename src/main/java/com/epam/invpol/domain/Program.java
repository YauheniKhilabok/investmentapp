package com.epam.invpol.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "programs")
public class Program extends DomainObject<Long> implements Serializable {

    @Column(name = "name", nullable = false, unique = true, length = 100)
    private String name;

    @Column(name = "participants_number", nullable = false)
    private int participantsNumber;

    @Column(name = "participants_max_number", nullable = false)
    private int participantsMaxNumber;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "duration", nullable = false)
    private int duration;

    @OneToMany(mappedBy = "program")
    private Set<Investment> investments = new HashSet<>();

    @OneToMany(mappedBy = "program")
    private Set<Mark> marks = new HashSet<>();

    public Program() {
    }

    public Program(Long id, String name, int participantsNumber, int participantsMaxNumber, String description, LocalDateTime beginningDate, LocalDateTime expirationDate) {
        super(id);
        this.name = name;
        this.participantsNumber = participantsNumber;
        this.participantsMaxNumber = participantsMaxNumber;
        this.description = description;
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

    public Set<Investment> getInvestments() {
        return investments;
    }

    public void setInvestments(Set<Investment> investments) {
        this.investments = investments;
    }

    public Set<Mark> getMarks() {
        return marks;
    }

    public void setMarks(Set<Mark> marks) {
        this.marks = marks;
    }

    @Override
    public String toString() {
        return "Program{" +
                "id='" + getId() + '\'' +
                ", name='" + name + '\'' +
                ", participantsNumber=" + participantsNumber +
                ", participantsMaxNumber=" + participantsMaxNumber +
                ", description='" + description + '\'' +
                ", duration='" + duration + '\'' +
                '}';
    }
}
