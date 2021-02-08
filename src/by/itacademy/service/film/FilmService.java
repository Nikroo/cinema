package by.itacademy.service.film;

import by.itacademy.dao.Dao;
import by.itacademy.dao.openserver.FilmDAO;

import java.util.List;
import java.util.Optional;

public class FilmService {

    private Film film;
    private Ticket ticket;
    private TicketService ticketService = new TicketService();
    private Dao dao;

    public FilmService() {
        this.dao = new FilmDAO();
    }

    public boolean create(Film film) {
        return dao.create(film);
    }

    public Film read(String name){
        Optional<Film> optionalFilm = dao.read(name);
        Film film = new Film();
        if(optionalFilm.isPresent()){
            film = optionalFilm.get();
            film.setTickets(ticketService.readByFilm(film.getId()));
        }
        return film;
    }

    public void update(int id, Film film){
        dao.update(id, film);
    }

    public void remove(String name){
        dao.delete(name);
    }

    public List<Film> readAll() {
        List<Film> films = dao.readAll();
        for (Film element : films) {
            element.setTickets(ticketService.readByFilm(element.getId()));
        }
        return films;
    }

}
