package com.epam.invpol.controller;

import com.epam.invpol.domain.User;
import com.epam.invpol.dto.UserDto;
import com.epam.invpol.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    private final ConversionService conversionService;

    @Autowired
    public UserController(UserService userService, ConversionService conversionService) {
        this.userService = userService;
        this.conversionService = conversionService;
    }

    @PostMapping
    public ResponseEntity<UserDto> saveUser(@Valid @RequestBody UserDto userDto) {
        User user = conversionService.convert(userDto, User.class);
        User savedUser = userService.register(user);
        UserDto savedUserDto = conversionService.convert(savedUser, UserDto.class);
        return new ResponseEntity<>(savedUserDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<UserDto> logIn(@RequestParam(name = "login") String login,
                                         @RequestParam(name = "password") String password) {
        User user = userService.logIn(login, password);
        UserDto userDto = conversionService.convert(user, UserDto.class);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> logOut(@PathVariable("id") long id) {
        User user = userService.logOut(id);
        UserDto userDto = conversionService.convert(user, UserDto.class);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @GetMapping("/password")
    public ResponseEntity<String> restorePassword(@RequestParam(name = "email") String email) {
        String password = userService.restorePassword(email);
        return new ResponseEntity<>(password, HttpStatus.OK);
    }
}
