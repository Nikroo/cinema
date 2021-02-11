package by.itacademy.service.film;

import by.itacademy.exception.DaoException;
import by.itacademy.dao.openserver.TicketDAO;
import by.itacademy.logging.CinemaLogger;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

import static by.itacademy.language.Constant.*;

public class TicketService {
    private TicketDAO dao;

    public TicketService(TicketDAO dao) {
        this.dao = dao;
    }

    public boolean create(Ticket ticket) {
        return dao.create(ticket);
    }

    public Optional<Ticket> read(String id){
        return dao.read(id);
    }

    public boolean buyTicket(int id, int userId){
        Ticket ticket = read(String.valueOf(id)).get();
        if(ticket.userId()<0){
            ticket.setUser(userId);
            try {
                if(dao.update(id, ticket)) {
                    CinemaLogger.LOGGER.log(Level.INFO, TICKET_BUY_SUCCESSFULLY+ " " + ticket.toString());
                }
                return true;
            } catch (DaoException e) {
                CinemaLogger.LOGGER.log(Level.INFO, TICKET_BUY_FILED + " " + ticket.toString());
                e.printStackTrace();
            }
        }else{
            return false;
        }
        return false;
    }

    public boolean returnTicket(int id, int userId){
        Ticket ticket = read(String.valueOf(id)).get();
        if(ticket.userId()==userId){
            ticket.setUser(-1);
            try {
                dao.update(id, ticket);
            } catch (DaoException e) {
                e.printStackTrace();
            }
            return true;
        }else{
            return false;
        }
    }

    public void remove(String place){
        dao.delete(place);
    }

    public List<Ticket> readAll() {
         return dao.readAll();
    }

    public List<Ticket> readByFilm(int filmId){
        return dao.readByFilm(filmId);
    }

    public List<Ticket> readByUser(int userId){
        return dao.readByUser(userId);
    }

    public void deleteByFilm(String filmId){
        dao.deleteByFilm(filmId);
    }

}
