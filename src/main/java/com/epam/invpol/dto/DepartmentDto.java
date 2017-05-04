package com.epam.invpol.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

public class DepartmentDto extends DtoObject<Long> implements Serializable {

    @NotNull(message = "{validation.departmentNameNotNull}")
    @Size(min = 1, max = 100, message = "{validation.departmentNameLength}")
    private String name;

    @NotNull(message = "{validation.departmentActivityNotNull}")
    @Size(min = 1, message = "{validation.departmentActivityLength}")
    private String activity;

    public DepartmentDto() {

    }

    public DepartmentDto(Long id, String name, String activity) {
        super(id);
        this.name = name;
        this.activity = activity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DepartmentDto that = (DepartmentDto) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(name, that.name) &&
                Objects.equals(activity, that.activity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), name, activity);
    }

    @Override
    public String toString() {
        return "DepartmentDto{" +
                "id='" + getId() + '\'' +
                "name='" + name + '\'' +
                ", activity='" + activity + '\'' +
                '}';
    }
}
