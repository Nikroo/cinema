package by.itacademy.service.user;

import by.itacademy.dao.Dao;
import by.itacademy.dao.openserver.OpenServerSqlPersonDao;
import by.itacademy.service.factory.UserFactory;

public class Person extends User {

    private OpenServerSqlPersonDao personDao;
    private int role;

    public Person(Integer id, String login, String password) {
        super(id, login, password);
    }

    public Person (Integer id, String login, String password, int role) {
        super(id, login, password);
        this.role = role;

    }

    public Person(String login, String password) {
        super(login, password);
    }

}
