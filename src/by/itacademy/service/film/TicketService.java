package by.itacademy.service.film;

import by.itacademy.dao.Dao;
import by.itacademy.dao.openserver.TicketDAO;
import java.util.List;
import java.util.Optional;

public class TicketService {
    private Dao dao;

    public TicketService() {
        this.dao = new TicketDAO();
    }

    public boolean create(Ticket ticket) {
        return dao.create(ticket);
    }

    public Optional<Ticket> read(String place){
        return dao.read(place);
    }

    public void update(String place, Ticket ticket){
        dao.update(place, ticket);
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
}
