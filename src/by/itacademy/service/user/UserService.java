package by.itacademy.service.user;
import by.itacademy.dao.Dao;
import by.itacademy.exception.DaoException;
import by.itacademy.language.Constant;
import by.itacademy.logging.CinemaLogger;
import by.itacademy.service.util.Hash;
import java.util.List;
import java.util.Optional;
import java.util.logging.*;

import static by.itacademy.language.Constant.*;

public class UserService extends Hash {
    private Dao dao;


    public UserService(Dao dao) {
        this.dao = dao;
    }

    public boolean create(User user) {
        try {
            if(dao.create(user)){
                CinemaLogger.LOGGER.log(Level.INFO,user.getLogin()+ Constant.USER_REGISTERED_SUCCESSFULLY);
               return true;
            }
                return false;
        } catch (DaoException e) {
            CinemaLogger.LOGGER.log(Level.INFO,user.getLogin()+ Constant.USER_IS_NOT_REGISTERED);
            e.getExceptionMessage();
        }
        return false;
    }

    public Optional<User> read(String login){
        Optional<User> optionalUser;
        try {
            optionalUser = dao.read(login);
            return optionalUser;
        } catch (DaoException e) {
            e.getExceptionMessage();
        }
     return Optional.empty();
    }

    public boolean remove(String login){
        try {
            if(dao.delete(login)) {
                CinemaLogger.LOGGER.log(Level.INFO,login+" "+USER_DELETED_SUCCESSFULLY);
                return true;
            }
        } catch (DaoException e) {
            CinemaLogger.LOGGER.log(Level.INFO,login+" "+USER_DELETED_FILED);
            e.getExceptionMessage();
        }
        return false;
    }

    public boolean update(int id, User user){
        try {
            if(dao.update(id, user)){
                CinemaLogger.LOGGER.log(Level.INFO, USER_UPDATE_SUCCESSFULLY+user.toString());
                return true;
            }
        } catch (DaoException e) {
            CinemaLogger.LOGGER.log(Level.INFO, USER_IS_NOT_UPDATED+user.toString());
            e.getExceptionMessage();
        }
        return false;
    }

    public List<User> readAll(){
        return dao.readAll();
    }

    public Optional<User> checkAccess(String login, String password) {
        Optional<User> optionalUser = read(login);

        if (optionalUser.isPresent()){
            if(validatePassword(password, optionalUser.get().getPassword())){
                return optionalUser;
            }
        }
        return Optional.empty();
    }
}

