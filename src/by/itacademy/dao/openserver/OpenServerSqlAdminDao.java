package by.itacademy.dao.openserver;

import by.itacademy.dao.AdminDao;
import by.itacademy.dao.Dao;
import by.itacademy.service.user.Person;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OpenServerSqlAdminDao extends AbstractConnection implements Dao {

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

    public Person read(String login, String password) {
        Person person = null;
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
                person = new Person(id, name, pass, access);
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
        return person;
    }


    public Person update(Integer id, Person person) {
        try {
            PreparedStatement statement = getConnection()
                    .prepareStatement("UPDATE users SET login=?, password=? WHERE id=?");
            statement.setString(1, person.getLogin());
            statement.setString(2, person.getHash());
            statement.setString(3, id.toString());
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
        return person;

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

    public List<Person> readAll() {
        List<Person> people = new ArrayList<>();
        try {
            PreparedStatement statement = getConnection()
                    .prepareStatement("SELECT id, login, password, access FROM users");
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                people.add(new Person(set.getInt("id"),
                        set.getString("login"),
                        set.getString("password"),
                        set.getInt("access")));
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
}
