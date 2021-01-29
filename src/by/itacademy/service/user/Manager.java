package by.itacademy.service.user;

import by.itacademy.dao.Dao;
import by.itacademy.dao.openserver.OpenServerSqlManagerDao;

import java.util.List;

public class Manager extends User {

    private OpenServerSqlManagerDao managerDao;

    public Manager(Integer id, String login, String password) {
        super(id, login, password);
    }

    public List<Person> readAll(){return managerDao.readAll();}

}
