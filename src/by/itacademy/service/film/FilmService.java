package by.itacademy.service.film;

import by.itacademy.dao.Dao;
import by.itacademy.dao.openserver.FilmDAO;

import java.util.List;
import java.util.Optional;

public class FilmService implements Service {

    private Dao dao;

    public FilmService() {
        this.dao = new FilmDAO();
    }

    public boolean create(Film film) {
        return dao.create(film);
    }

    public Optional<Film> read(String name){
        return dao.read(name);
    }

    public void update(String name, Film film){
        dao.update(name, film);
    }

    public void remove(String name){
        dao.delete(name);
    }

    public void readAll() {
        List<Film> users = dao.readAll();
        for (Film element : users) {
            System.out.println(element);
        }
    }

}
