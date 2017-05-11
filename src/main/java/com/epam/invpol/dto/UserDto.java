package com.epam.invpol.dto;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

public class UserDto extends DtoObject<Long> implements Serializable {

    @NotNull(message = "{validation.userLoginNotNull}")
    @Size(min = 1, max = 100, message = "{validation.userLoginLength}")
    private String login;

    @NotNull(message = "{validation.userEmailNotNull}")
    @Size(min = 1, max = 100, message = "{validation.userEmailLength}")
    @Email(message = "{validation.userEmailRegexp}")
    private String email;

    @NotNull(message = "{validation.userPasswordNotNull}")
    @Size(min = 1, max = 100, message = "{validation.userPasswordLength}")
    private String password;

    private boolean status;

    public UserDto() {
    }

    public UserDto(Long id, String login, String email, String password, boolean status) {
        super(id);
        this.login = login;
        this.email = email;
        this.password = password;
        this.status = status;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(getId(), userDto.getId()) &&
                status == userDto.status &&
                Objects.equals(login, userDto.login) &&
                Objects.equals(email, userDto.email) &&
                Objects.equals(password, userDto.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), login, email, password, status);
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id='" + getId() + '\'' +
                "login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", status=" + status +
                '}';
    }
}
