package by.itacademy.service.factory;

import by.itacademy.dao.Dao;
import by.itacademy.service.user.Admin;
import by.itacademy.service.user.Manager;
import by.itacademy.service.user.Person;
import by.itacademy.service.user.User;

public enum UserFactory {

    PERSON {
         public User getInstance(Integer id, String login, String password){
             return new Person(id, login, password);
         }
    },
    MANAGER {
        public User getInstance(Integer id, String login, String password){
            return new Manager(id, login, password);
        }
    },
    ADMIN {
        public User getInstance(Integer id, String login, String password){
            return new Admin(id, login, password);
        }
    };

    public abstract User getInstance(Integer id, String login, String password);

}
