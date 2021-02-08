package by.itacademy.service.user;
import by.itacademy.dao.Dao;
import by.itacademy.service.util.Hash;

import java.util.List;
import java.util.Optional;

public class UserService extends Hash {
    private Dao dao;

    public UserService(Dao dao) {
        this.dao = dao;
    }

    public boolean create(User user) {
        return dao.create(user);
    }

    public Optional<User> read(String login){
        return dao.read(login);
    }

    public void remove(String login){
        dao.delete(login);
    }

    public void update(int id, User user){
        dao.update(id, user);
    }

    public void readAll(){
        List<User> users = dao.readAll();
        for (User element : users) {
            System.out.println(element);
        }
    }

    public Optional<User> checkAccess(String login, String password) {
        Optional<User> optionalUser = dao.read(login);

        if (optionalUser.isPresent()){
            if(validatePassword(password, optionalUser.get().getPassword())){
                return optionalUser;
            }
        }
        return optionalUser;
    }
}

//if(validatePassword(password, optionalUser.get().getPassword())){

