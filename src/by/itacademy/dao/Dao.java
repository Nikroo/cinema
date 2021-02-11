package by.itacademy.dao;

import by.itacademy.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    boolean create(T name) throws DaoException;
    Optional<T> read(String element) throws DaoException;
    boolean update(int id, T name) throws DaoException;
    boolean delete(String login) throws DaoException;
    List<T> readAll();
}
