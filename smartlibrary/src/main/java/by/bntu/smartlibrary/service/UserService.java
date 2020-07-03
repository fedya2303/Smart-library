package by.bntu.smartlibrary.service;

import by.bntu.smartlibrary.dao.UserDao;
import by.bntu.smartlibrary.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public Optional<List<User>> findAll() {
        return userDao.findAll();
    }

    public Optional<User> getById(Long id) {
        return userDao.findById(id);
    }

    public Optional<User> update(User user) {
        return userDao.update(user);
    }

    public Optional<User> save(User user) {
        return userDao.save(user);
    }
}
