package com.epam.invpol.domain;

import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Objects;

@MappedSuperclass
public abstract class DomainObject<T extends Serializable> implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private T id;

    public DomainObject() {
    }

    DomainObject(T id) {
        this.id = id;
    }

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "DomainObject{" +
                "id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DomainObject<?> that = (DomainObject<?>) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
