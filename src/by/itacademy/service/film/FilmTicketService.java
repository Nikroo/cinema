package by.itacademy.service.film;

import java.util.Optional;

public class FilmTicketService implements Service {

    private Service service = new FilmService();

    @Override
    public boolean create(Film film) {
        return false;
    }

    @Override
    public Optional<Film> read(String name) {
        return Optional.empty();
    }

    @Override
    public void update(String name, Film film) {

    }

    @Override
    public void remove(String name) {

    }

    @Override
    public void readAll() {

    }
}
