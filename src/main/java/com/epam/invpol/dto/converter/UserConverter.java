package com.epam.invpol.dto.converter;

import com.epam.invpol.domain.User;
import com.epam.invpol.dto.UserDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserConverter implements Converter<User, UserDto> {

    @Override
    public UserDto convert(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setLogin(user.getLogin());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setStatus(user.getStatus());
        return userDto;
    }
}
