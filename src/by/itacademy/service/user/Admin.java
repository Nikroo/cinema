package by.itacademy.service.user;

import by.itacademy.dao.openserver.OpenServerSqlAdminDao;

import java.util.List;

public class Admin extends User  {

    private OpenServerSqlAdminDao adminDao;

    public Admin(Integer id, String login, String password) {
        super(id, login, password);
    }

    public boolean addUser(Person person){ return adminDao.create(person);}

    public boolean removeUser(Person person){
        return adminDao.delete(person.getLogin());
    }

    public Person selectUser(String login, String password){
        return adminDao.read(login, password);
    }

    public Person updateUser(Integer id, Person person){ return adminDao.update(id, person);}

    public List<Person> readAll(){return adminDao.readAll();}


}
