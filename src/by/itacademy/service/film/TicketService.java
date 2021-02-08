package by.itacademy.service.film;

import by.itacademy.dao.openserver.TicketDAO;
import by.itacademy.service.user.User;

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

    public void update(int id, User user){

        Ticket ticket = read(String.valueOf(id)).get();
        System.out.println(ticket);

        if(ticket.userId()<0){
            ticket.setUser(user.getId());
            dao.update(id, ticket);
        }else{
            System.out.println("Place already taken");
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

}
