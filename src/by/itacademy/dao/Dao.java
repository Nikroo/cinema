package by.itacademy.dao;

import by.itacademy.service.user.User;
import java.util.List;

public interface Dao {
    boolean create(User person);
    User read(String login, String pass);
    User update(Integer id, User person);
    boolean delete(String login);
    List<User> readAll();
}
