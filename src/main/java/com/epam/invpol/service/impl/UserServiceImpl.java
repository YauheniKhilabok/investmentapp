package com.epam.invpol.service.impl;

import com.epam.invpol.domain.User;
import com.epam.invpol.repository.UserRepository;
import com.epam.invpol.service.UserService;
import com.epam.invpol.service.exception.EmailAlreadyExistException;
import com.epam.invpol.service.exception.EntityAlreadyExistException;
import com.epam.invpol.service.exception.EntityNotFoundException;
import com.epam.invpol.service.exception.LoginAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User register(User user) {
        if (user.getId() != null) {
            if (isIdExist(user.getId())) {
                throw new EntityAlreadyExistException("User with such id already exists.");
            }
        }
        if (isLoginAlreadyExist(user.getLogin())) {
            throw new LoginAlreadyExistException("Login is already exist.");
        }
        if (isEmailAlreadyExist(user.getEmail())) {
            throw new EmailAlreadyExistException("Email is already exist.");
        }
        User savedUser = userRepository.save(user);
        return userRepository.findOne(savedUser.getId());
    }

    private boolean isIdExist(long id) {
        User user = userRepository.findOne(id);
        return user != null;
    }

    private boolean isLoginAlreadyExist(String login) {
        User user = userRepository.findByLogin(login);
        return user != null;
    }

    private boolean isEmailAlreadyExist(String email) {
        User user = userRepository.findByEmail(email);
        return user != null;
    }

    @Override
    public User logIn(String login, String password) {
        if (!isLoginAndPasswordExist(login, password)) {
            throw new EntityNotFoundException("User with such login and password is not exist.");
        }
        User user = userRepository.findByLoginAndPassword(login, password);
        user.setStatus(true);
        User updatedUser = userRepository.save(user);
        return userRepository.findOne(updatedUser.getId());
    }

    private boolean isLoginAndPasswordExist(String login, String password) {
        User user = userRepository.findByLoginAndPassword(login, password);
        return user != null;
    }

    @Override
    public User logOut(long id) {
        User user = userRepository.findOne(id);
        if (user == null) {
            throw new EntityNotFoundException("User is not exist.");
        }
        boolean status = user.getStatus();
        if (status) {
            user.setStatus(false);
        }
        return user;
    }

    @Override
    public String restorePassword(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new EntityNotFoundException("User is not exist.");
        }
        return user.getPassword();
    }
}
