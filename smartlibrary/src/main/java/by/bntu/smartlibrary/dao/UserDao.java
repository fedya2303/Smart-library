package by.bntu.smartlibrary.dao;

import by.bntu.smartlibrary.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    Optional<List<User>> findAll();

    Optional<User> update(User user);

    Optional<User> save(User user);

    Optional<User> findById(Long id);
}
