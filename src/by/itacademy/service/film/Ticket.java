package by.itacademy.service.film;

public class Ticket {
    private int id;
    private int userId;
    private int filmId;
    private int place;
    private int price;
    private boolean isSold;

    public Ticket() {
    }

    public Ticket(int userId, int filmId, int place, int price, boolean isSold) {
        this.id = id;
        this.userId = userId;
        this.filmId = filmId;
        this.place = place;
        this.price = price;
        this.isSold = isSold;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int userId() {
        return userId;
    }

    public void setUser(int userId) {
        this.userId = userId;
    }

    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isSold() {
        return isSold;
    }

    public void setSold(boolean sold) {
        isSold = sold;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", user=" + userId +
                ", film=" + filmId +
                ", place=" + place +
                ", price=" + price +
                ", isSold=" + isSold +
                '}';
    }
}
