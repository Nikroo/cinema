package by.itacademy.service.film;

import by.itacademy.dao.openserver.TicketDAO;
import java.util.List;
import java.util.Optional;

public class TicketService {
    private TicketDAO dao;

    public TicketService() {
        this.dao = new TicketDAO();
    }

    public boolean create(Ticket ticket) {
        return dao.create(ticket);
    }

    public Optional<Ticket> read(String id){
        return dao.read(id);
    }

    public void buyTicket(int id, int userId){
        Ticket ticket = read(String.valueOf(id)).get();
        if(ticket.userId()<0){
            ticket.setUser(userId);
            dao.update(id, ticket);
        }else{
            System.out.println("Place already taken");
        }
    }

    public void returnTicket(int id, int userId){
        Ticket ticket = read(String.valueOf(id)).get();
        if(ticket.userId()==userId){
            ticket.setUser(-1);
            dao.update(id, ticket);
        }else{
            System.out.println("Check tickets id!");
        }
    }

    public void remove(String place){
        dao.delete(place);
    }

    public void readAll() {
        List<Ticket> tickets = dao.readAll();
        for (Ticket element : tickets) {
            System.out.println(element);
        }
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
