package by.itacademy.dao.openserver;

import by.itacademy.dao.Dao;
import by.itacademy.exception.DaoException;
import by.itacademy.service.user.User;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDAO extends AbstractConnection implements Dao<User> {

    public boolean create(User user) throws DaoException {
        try {
            PreparedStatement statement = getConnection()
                    .prepareStatement
                            (SQLUser.INSERT.QUERY);
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setInt(3, user.getRole().getId());
            statement.execute();
            return true;
        } catch (MySQLIntegrityConstraintViolationException e) {
            e.getMessage();
            throw new DaoException(DaoException._USER_NOT_CREATE);
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

    public Optional<User> read(String login) throws DaoException {
        Optional<User> optionalUser;
        User user = new User();
        try {
            PreparedStatement statement = getConnection()
                    .prepareStatement(SQLUser.GET.QUERY);

            statement.setString(1, login);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                user.setId(rs.getInt("id"));
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));
                user.setRole(new User.Role(rs.getInt("role_id")));
            }
            statement.execute();

            if(user.getId()==null){
                throw new DaoException(DaoException._FAIL_TO_USER_INSERT);
            }else{
                return Optional.of(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();

        } finally {
            try {
                close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return Optional.empty();
    }


    public boolean update(int id, User user) throws DaoException {
        try {
            PreparedStatement statement = getConnection()
                    .prepareStatement(SQLUser.UPDATE.QUERY);
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setInt(3, user.getRole().getId());
            statement.setInt(4, id);
            int count = statement.executeUpdate();
            if (count != 1) {
                throw new DaoException(DaoException._UPDATE_USER_FAILED);
            }
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DaoException(DaoException._UPDATE_USER_FAILED);
        } finally {
            try {
                close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public boolean delete(String login) throws DaoException {
        try {
            PreparedStatement statement = getConnection()
                    .prepareStatement(SQLUser.DELETE.QUERY);
            statement.setString(1, login);
            int count = statement.executeUpdate();
            if (count != 1) {
                throw new DaoException(DaoException._DELETE_USER_FAILED);
            }
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DaoException(DaoException._DELETE_USER_FAILED);
        } finally {
            try {
                close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public List<User> readAll() {
        List<User> people = new ArrayList<>();
        try {
            PreparedStatement statement = getConnection()
                    .prepareStatement(SQLUser.GET_ALL.QUERY);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                people.add(
                        new User(set.getInt(1),
                                set.getString(2),
                                set.getString(3),
                                new User.Role(set.getInt(4))));
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
        return people;
    }

    enum SQLUser {
        GET_ALL("SELECT users.id, users.login, users.password, roles.role_id, roles.name FROM users LEFT JOIN roles ON users.role = roles.role_id"),
        GET("SELECT users.id, users.login, users.password, roles.role_id, roles.name FROM users LEFT JOIN roles ON users.role = roles.role_id WHERE users.login = (?)"),
        INSERT("INSERT INTO users (login, password, role) VALUES (?,?,?)"),
        DELETE("DELETE FROM users WHERE login = ?"),
        UPDATE("UPDATE users SET login=?, password=?, role=? WHERE id=?");

        String QUERY;

        SQLUser(String QUERY) {
            this.QUERY = QUERY;
        }
    }

}
