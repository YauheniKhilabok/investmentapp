package com.epam.invpol.repository;

import com.epam.invpol.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findByLogin(String login);

    User findByEmail(String email);

    User findByLoginAndPassword(String login, String password);
}
