package com.epam.invpol.service;

import com.epam.invpol.domain.User;

public interface UserService {

    User register(User user);

    User logIn(String login, String password);

    User logOut(long id);

    String restorePassword(String email);
}
