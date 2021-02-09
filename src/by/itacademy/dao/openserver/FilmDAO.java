package by.itacademy.dao.openserver;

import by.itacademy.dao.Dao;
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
    public boolean create(Film film) {
        Timestamp timestamp = Timestamp.valueOf(film.getDateTime());
        try {
            PreparedStatement statement = getConnection()
                    .prepareStatement
                            ("INSERT INTO films (name, timestamp) VALUES (?,?)");
            statement.setString(1, film.getName());
            statement.setTimestamp(2, timestamp);
            statement.execute();
            return true;
        } catch (MySQLIntegrityConstraintViolationException e) {
            System.out.println(e.getMessage());
            return false;
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
    public Optional<Film> read(String name) {
        Optional<Film> optionalFilm;
        Film film = new Film();
        try {
            PreparedStatement statement = getConnection()
                    .prepareStatement("SELECT id, name, timestamp FROM films WHERE name=?");

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


    public void update(int id, Film film) {
        try {
            PreparedStatement statement = getConnection()
                    .prepareStatement("UPDATE films SET name=?, timestamp =? WHERE id=?");
            statement.setString(1, film.getName());
            statement.setTimestamp(2, Timestamp.valueOf(film.getDateTime()));
            statement.setInt(3, id);
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

    }

    public boolean delete(String id) {
        try {
            PreparedStatement statement = getConnection()
                    .prepareStatement("DELETE FROM films WHERE id = ?");
            statement.setString(1, id);
            statement.execute();
            return true;

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

    public List<Film> readAll() {
        Film film = new Film();
        List<Film> films = new ArrayList<>();
        try {
            PreparedStatement statement = getConnection()
                    .prepareStatement("SELECT id, name, timestamp FROM films");
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
        GET_ALL("SELECT users.id, users.login, users.password, roles.role_id, roles.name FROM users LEFT JOIN roles ON users.role = roles.role_id"),
        GET("SELECT users.id, users.login, users.password, roles.role_id, roles.name FROM users LEFT JOIN roles ON users.role = roles.role_id WHERE users.login = (?)"),
        INSERT("INSERT INTO users (login, password, role) VALUES (?,?,?)"),
        DELETE("DELETE FROM users WHERE id = (?) AND login = (?) AND password = (?) RETURNING id"),
        UPDATE("UPDATE users SET password = (?) WHERE id = (?) RETURNING id");

        String QUERY;

        SQLFilm(String QUERY) {
            this.QUERY = QUERY;
        }
    }
}
