package by.itacademy.dao.openserver;

import by.itacademy.dao.Dao;
import by.itacademy.exception.DaoException;
import by.itacademy.service.film.Film;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FilmDAO extends AbstractConnection implements Dao<Film> {

    @Override
    public boolean create(Film film) throws DaoException {
        Timestamp timestamp = Timestamp.valueOf(film.getDateTime());
        try {
            PreparedStatement statement = getConnection()
                    .prepareStatement(SQLFilm.INSERT.QUERY);
            statement.setString(1, film.getName());
            statement.setTimestamp(2, timestamp);
            statement.execute();
            return true;
        } catch (MySQLIntegrityConstraintViolationException e) {
            System.out.println(e.getMessage());
            throw new DaoException(DaoException._FILM_NOT_CREATE);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public Optional<Film> read(String name) throws DaoException {
        Optional<Film> optionalFilm;
        Film film = new Film();
        try {
            PreparedStatement statement = getConnection()
                    .prepareStatement(SQLFilm.GET.QUERY);

            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                film.setId(rs.getInt("id"));
                film.setName(rs.getString("name"));
                film.setDateTime(rs.getTimestamp("timestamp").toLocalDateTime());
            }
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DaoException(DaoException._UPDATE_FILM_FAILED);
        } finally {
            optionalFilm = Optional.of(film);
            try {
                close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return optionalFilm;
    }

    @Override
    public boolean update(int id, Film film) throws DaoException {
        try {
            PreparedStatement statement = getConnection()
                    .prepareStatement(SQLFilm.UPDATE.QUERY);
            statement.setString(1, film.getName());
            statement.setTimestamp(2, Timestamp.valueOf(film.getDateTime()));
            statement.setInt(3, id);
            int count = statement.executeUpdate();
            if (count != 1) {
                throw new DaoException(DaoException._UPDATE_FILM_FAILED);
            }
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DaoException(DaoException._UPDATE_FILM_FAILED);
        } finally {
            try {
                close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @Override
    public boolean delete(String id) throws DaoException {
        try {
            PreparedStatement statement = getConnection()
                    .prepareStatement(SQLFilm.DELETE.QUERY);
            statement.setString(1, id);
            statement.execute();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DaoException(DaoException._DELETE_FILM_FAILED);
        } finally {
            try {
                close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @Override
    public List<Film> readAll() {
        Film film = new Film();
        List<Film> films = new ArrayList<>();
        try {
            PreparedStatement statement = getConnection()
                    .prepareStatement(SQLFilm.GET_ALL.QUERY);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                films.add(
                        new Film(rs.getInt(1),
                                rs.getString(2),
                                rs.getTimestamp(3).toLocalDateTime()));
            }
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return films;
    }

    enum SQLFilm {
        GET_ALL("SELECT id, name, timestamp FROM films"),
        GET("SELECT id, name, timestamp FROM films WHERE name=?"),
        INSERT("INSERT INTO films (name, timestamp) VALUES (?,?)"),
        DELETE("DELETE FROM films WHERE id = ?"),
        UPDATE("UPDATE films SET name=?, timestamp =? WHERE id=?");

        String QUERY;

        SQLFilm(String QUERY) {
            this.QUERY = QUERY;
        }
    }
}
