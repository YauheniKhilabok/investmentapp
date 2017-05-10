package com.epam.invpol.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "departments")
public class Department extends DomainObject<Long> implements Serializable{

    @Column(name = "name", nullable = false, unique = true, length = 100)
    private String name;

    @Column(name = "activity", nullable = false)
    private String activity;

    @OneToMany(mappedBy = "department")
    private Set<Employee> employees = new HashSet<>();

    public Department() {
    }

    public Department(Long id, String name, String activity) {
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

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id='" + getId() + '\'' +
                ", name='" + name + '\'' +
                ", activity='" + activity + '\'' +
                '}';
    }
}
