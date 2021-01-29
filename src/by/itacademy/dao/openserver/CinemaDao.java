package by.itacademy.dao.openserver;

import by.itacademy.service.user.Person;
import by.itacademy.service.user.User;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CinemaDao extends AbstractConnection {


    public boolean create(Person person) {
        try {
            PreparedStatement statement = getConnection()
                    .prepareStatement
                            ("INSERT INTO users (login, password) VALUES (?,?)");
            statement.setString(1, person.getLogin());
            statement.setString(2, person.getHash());
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


    public User checkAccess(String login, String password) {
        User user = null;
        try {
            PreparedStatement statement = getConnection()
                    .prepareStatement("SELECT id, login, password, access FROM users where login=?");
            statement.setString(1, login);
            ResultSet rs = statement.executeQuery();
            int id;
            String name;
            String pass;
            int access;
            while (rs.next()) {
                id = rs.getInt("id");
                name = rs.getString("login");
                pass = rs.getString("password");
                access = rs.getInt("access");
                user = new Person(id, name, pass, access);
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
        return user;
    }
}
