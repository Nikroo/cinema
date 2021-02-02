package by.itacademy.service.film;
import java.util.Optional;

public interface Service {

    boolean create(Film film);
    Optional<Film> read(String name);
    void update(String name, Film film);
    void remove(String name);
    void readAll();

}
