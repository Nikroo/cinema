package by.itacademy;


import by.itacademy.dao.openserver.CinemaDao;
import by.itacademy.dao.openserver.OpenServerSqlAdminDao;
import by.itacademy.service.factory.UserFactory;
import by.itacademy.service.user.User;
import by.itacademy.service.util.Hash;
import by.itacademy.service.user.Person;
import by.itacademy.service.user.UserService;

import java.util.Optional;

public class Main extends Hash {
    public static void main(String[] args) {

        UserService service = new UserService(new CinemaDao());

        service.create(new Person("Vania@mail.ru", "1111"));

        Optional<User> userOptional = service.checkAccess("Vania@mail.ru", "1111");

        if(userOptional.isPresent()){
            User user = userOptional.get();
            System.out.println(user);
        }

        //User user = UserFactory.ADMIN.getInstance(1,"Vania@mail.ru", "1111");


        /*System.out.println(service.addUser(new Person("Vania@mail.ru", "1111")));
        System.out.println(service.addUser(new Person("Vania@mail.ru", "1111")));

        service.addUser(new Person("Egor123@yahoo.com", "13698"));
        service.addUser(new Person("Sveta777@mail.ru", "qwerty123"));

        System.out.println(service.removeUser(new Person("Egor123@yahoo.com", "13698")));

        Person person0 = new Person("Makar@tut.by", "0000");
        service.addUser(person0);
        person0 = service.selectUser("Makar@tut.by", "0000");
        service.updateUser(person0.getId(), new Person("Anna@yandex.ru", "1988"));

        person0 = service.selectUser("Anna@yandex.ru", "1988");
        System.out.println(person0.getPassword());

        System.out.println(validatePassword("1988", person0.getPassword()));
        System.out.println(validatePassword("8891", person0.getPassword()));

        for (Person element:service.readAll()) {
            System.out.println(element);
        }*/


    }
}
