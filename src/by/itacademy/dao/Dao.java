package by.itacademy.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    boolean create(T name);
    Optional<T> read(String name);
    void update(String login, T name);
    boolean delete(String login);
    List<T> readAll();
}
