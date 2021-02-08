package by.itacademy.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    boolean create(T name);
    Optional<T> read(String element);
    void update(int id, T name);
    boolean delete(String login);
    List<T> readAll();
}
