package by.itacademy.service.user;
import by.itacademy.dao.AdminDao;
import by.itacademy.dao.Dao;
import by.itacademy.dao.openserver.CinemaDao;
import by.itacademy.service.util.Hash;

import java.util.List;
import java.util.Optional;

public class UserService extends Hash {
    private CinemaDao dao;

    public UserService(CinemaDao dao) {this.dao = dao;}

    public boolean create(Person person){
        return dao.create(person);
    }

    public Optional<User> checkAccess(String login, String password){
        User user = dao.checkAccess(login, password);
        if(validatePassword(password, user.getPassword())){
            return Optional.of(user);
        };
        return  Optional.empty();
    }
}
