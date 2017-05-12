package com.epam.invpol.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Column;
import javax.persistence.JoinTable;
import javax.persistence.FetchType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "employees")
public class Employee extends DomainObject<Long> implements Serializable{

    @Column(name = "name", nullable = false, length = 45)
    private String name;

    @Column(name = "surname", nullable = false, length = 45)
    private String surname;

    @Column(name = "patronymic", length = 45)
    private String patronymic;

    @Column(name = "position", nullable = false, length = 100)
    private String position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_department", nullable = false)
    private Department department;

    @ManyToMany
    @JoinTable(name = "employees_programs", joinColumns = @JoinColumn(name = "id_employee"),
            inverseJoinColumns = @JoinColumn(name = "id_program"))
    private Set<Program> programs = new HashSet<>();

    public Employee() {
    }

    public Employee(Long id, String name, String surname, String patronymic, String position) {
        super(id);
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Set<Program> getPrograms() {
        return programs;
    }

    public void setPrograms(Set<Program> programs) {
        this.programs = programs;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id='" + getId() + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", position='" + position + '\'' +
                ", department=" + department +
                '}';
    }
}
