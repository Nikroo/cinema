package by.itacademy.service.film;

import by.itacademy.dao.Dao;
import by.itacademy.dao.openserver.TicketDAO;
import by.itacademy.exception.DaoException;
import by.itacademy.logging.CinemaLogger;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

import static by.itacademy.language.Constant.*;

public class FilmService {
    private TicketService ticketService = new TicketService(new TicketDAO());
    private Dao dao;

    public FilmService(Dao dao) {
        this.dao = dao;
    }

    public boolean create(Film film) {
        try {
            return dao.create(film);
        } catch (DaoException e) {
            e.getExceptionMessage();
        }
        return false;
    }

    public Film read(String name){
        Optional<Film> optionalFilm = null;
        try {
            optionalFilm = dao.read(name);
        } catch (DaoException e) {
            e.getExceptionMessage();
        }
        Film film = new Film();
        if(optionalFilm.isPresent()){
            film = optionalFilm.get();
            film.setTickets(ticketService.readByFilm(film.getId()));
        }
        return film;
    }

    public boolean update(int id, Film film){
        try {
            if(dao.update(id, film)){
                CinemaLogger.LOGGER.log(Level.INFO,FILM_UPDATE_SUCCESSFULLY+" "+film.toString());
            }
            return true;
        } catch (DaoException e) {
            CinemaLogger.LOGGER.log(Level.INFO,FILM_UPDATE_FILED+" "+film.getName());
            e.getExceptionMessage();
        }
        return false;
    }

    public boolean remove(String id){
        ticketService.deleteByFilm(id);
        try {
            return dao.delete(id);
        } catch (DaoException e) {
            e.getExceptionMessage();
        }
        return false;
    }

    public List<Film> readAll() {
        List<Film> films = dao.readAll();
        for (Film element : films) {
            element.setTickets(ticketService.readByFilm(element.getId()));
        }
        return films;
    }

}
