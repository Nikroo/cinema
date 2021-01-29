package by.itacademy.dao.openserver;

import by.itacademy.dao.Dao;
import by.itacademy.service.user.Person;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OpenServerSqlManagerDao extends AbstractConnection {

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
