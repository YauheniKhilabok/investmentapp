package com.epam.invpol.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

public class EmployeeDto extends DtoObject<Long> implements Serializable {

    @NotNull(message = "{validation.employeeNameNotNull}")
    @Size(min = 1, max = 45, message = "{validation.employeeNameLength}")
    private String name;

    @NotNull(message = "{validation.employeeSurnameNotNull}")
    @Size(min = 1, max = 45, message = "{validation.employeeSurnameLength}")
    private String surname;

    private String patronymic;

    @NotNull(message = "{validation.employeePositionNotNull}")
    @Size(min = 1, max = 100, message = "{validation.employeePositionLength}")
    private String position;

    @NotNull(message = "{validation.departmentNotNull}")
    private DepartmentDto department;

    public EmployeeDto() {
    }

    public EmployeeDto(Long id, String name, String surname, String patronymic, String position) {
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

    public DepartmentDto getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentDto department) {
        this.department = department;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeDto that = (EmployeeDto) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(name, that.name) &&
                Objects.equals(surname, that.surname) &&
                Objects.equals(patronymic, that.patronymic) &&
                Objects.equals(position, that.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), name, surname, patronymic, position);
    }

    @Override
    public String toString() {
        return "EmployeeDto{" +
                "id='" + getId() + '\'' +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", position='" + position + '\'' +
                '}';
    }
}
