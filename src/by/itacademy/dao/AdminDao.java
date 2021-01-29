package by.itacademy.dao;

import by.itacademy.service.user.Person;

import java.util.List;

public interface AdminDao {
   boolean create(Person person);
   Person read(String login, String pass);
   Person update(Integer id, Person person);
   boolean delete(String login);
   List<Person> readAll();
}
