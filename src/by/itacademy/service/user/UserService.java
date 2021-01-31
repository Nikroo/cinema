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

    public void read(String login, String password){
        User user = dao.read(login, password);
        System.out.println(user);
    }

/*    public int checkAccess(String login, String password) {

        Optional<User> optionalUser = dao.read(login, password);

        if (optionalUser.isPresent()){
            if(validatePassword(password, optionalUser.get().getPassword())){
                return User.Role.getId();
            }
        }

        return -1;
    }*/

    public void readAll(){
        List<User> users = dao.readAll();
        for (User element : users) {
            System.out.println(element);
    }
}
}

//if(validatePassword(password, optionalUser.get().getPassword())){

