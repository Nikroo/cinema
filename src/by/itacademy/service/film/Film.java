package by.itacademy.service.film;


import java.time.LocalDateTime;
import java.util.List;

public class Film {
    private int id;
    private String name;
    private LocalDateTime dateTime;
    private List<Ticket> tickets;

    public Film(int id, String name, LocalDateTime dateTime, List<Ticket> tickets) {
        this.id = id;
        this.name = name;
        this.dateTime = dateTime;
        this.tickets = tickets;
    }
}
