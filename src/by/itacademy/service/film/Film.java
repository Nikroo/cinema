package by.itacademy.service.film;


import java.time.LocalDateTime;
import java.util.List;

public class Film {
    private int id;
    private String name;
    private LocalDateTime dateTime;
    private List<Ticket> tickets;

    public Film() {
    }

    public Film(int id, String name, LocalDateTime dateTime) {
        this.id = id;
        this.name = name;
        this.dateTime = dateTime;
    }

    public Film(String name, LocalDateTime dateTime, List<Ticket> tickets) {
        this.id = id;
        this.name = name;
        this.dateTime = dateTime;
        this.tickets = tickets;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    @Override
    public String toString() {
        return "Film{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dateTime=" + dateTime +
                ", tickets=" + tickets +
                '}';
    }
}
