package by.itacademy.service.user;

import by.itacademy.service.film.Ticket;
import by.itacademy.service.util.Hash;

import java.util.List;

public class User {
    private Integer id;
    private String login;
    private String password;
    private Role role;
    private List<Ticket> tickets;

    public User() {
    }

    public User(String login, String password, Role role) {
        this.password = Hash.createHash(password);
        this.login = login;
        this.role = role;
    }

    public User(Integer id, String login, String password, Role role) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public void addTicket(Ticket ticket){
        tickets.add(ticket);
    }

    public void removeTicket(Ticket ticket){
        tickets.remove(ticket);
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
    public static class Role {

        private int id;
        private String name;

        public Role(int id) {
            this.id = id;
            switch (id) {
                case 1:
                    name = "admin";
                    break;
                case 2:
                    name = "manager";
                    break;
                case 3:
                    name = "user";
                    break;
            }
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Role{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
