package by.itacademy.service.film;

import by.itacademy.service.user.User;

public class Ticket {
    private int id;
    private User user;
    private Film film;
    private int place;
    private int price;
    private boolean isSold;

    public Ticket(int id, User user, Film film, int place, int price, boolean isSold) {
        this.id = id;
        this.user = user;
        this.film = film;
        this.place = place;
        this.price = price;
        this.isSold = isSold;
    }
}
