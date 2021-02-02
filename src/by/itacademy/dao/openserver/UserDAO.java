package by.itacademy.dao.openserver;

import by.itacademy.dao.Dao;
import by.itacademy.service.user.User;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDAO extends AbstractConnection implements Dao<User> {

    public boolean create(User user) {
        try {
            PreparedStatement statement = getConnection()
                    .prepareStatement
                            ("INSERT INTO users (login, password, role) VALUES (?,?,?)");
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setInt(3, user.getRole().getId());
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

    public Optional<User> read(String login) {
        Optional<User> optionalUser;
        User user = new User();
        try {
            PreparedStatement statement = getConnection()
                    .prepareStatement(SQLUser.GET.QUERY);

            statement.setString(1, login);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                user.setId(Integer.parseInt(rs.getString("id")));
                user.setLogin(login);
                user.setPassword(rs.getString("password"));
                user.setRole(new User.Role(rs.getInt("role_id"), rs.getString("name")));
            }
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            optionalUser = Optional.of(user);
            try {
                close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return optionalUser;
    }


    public void update(String login, User user) {
        try {
            PreparedStatement statement = getConnection()
                    .prepareStatement("UPDATE users SET login=?, password=?, role=? WHERE login=?");
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setInt(3, user.getRole().getId());
            statement.setString(4, login);
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

    public boolean delete(String login) {
        try {
            PreparedStatement statement = getConnection()
                    .prepareStatement("DELETE FROM users WHERE login = ?");
            statement.setString(1, login);
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
                        new User.Role(set.getInt(4), set.getString(5))));
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
        DELETE("DELETE FROM users WHERE id = (?) AND login = (?) AND password = (?) RETURNING id"),
        UPDATE("UPDATE users SET password = (?) WHERE id = (?) RETURNING id");

        String QUERY;

        SQLUser(String QUERY) {
            this.QUERY = QUERY;
        }
    }

}
