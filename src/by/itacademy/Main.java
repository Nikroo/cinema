package by.itacademy;


import by.itacademy.dao.openserver.FilmDAO;
import by.itacademy.dao.openserver.UserDAO;
import by.itacademy.service.film.Film;
import by.itacademy.service.film.FilmService;
import by.itacademy.service.film.Ticket;
import by.itacademy.service.film.TicketService;
import by.itacademy.service.user.User;
import by.itacademy.service.util.Hash;
import by.itacademy.service.user.UserService;
import by.itacademy.view.CommandLineController;
import by.itacademy.view.Controller;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Main extends Hash {
    public static void main(String[] args) {
        UserService userService = new UserService(new UserDAO());
        userService.create(new User("Anna@yandex.ru", "1988", new User.Role(1)));
        userService.create(new User("Kirill@yandex.ru", "1111", new User.Role(2)));

        Controller controller = new CommandLineController(new FilmService());
        controller.start();

        //service.update("Anna@yandex.ru", new User("Kirill@yandex.ru", "1988", new User.Role(1,"admin")));
        //System.out.println(userService.checkAccess("Kirill@yandex.ru", "1988"));

        //service.create(new User("Vania@mail.ru", "1111"));
        //service.readAll();

        //FilmService filmService = new FilmService();

        //Ticket ticket = filmService.read("Kiki's Delivery Service").getTickets().get(19);
        //User user = userService.read("Kirill@yandex.ru").get();

        //ticket.setUser(user.getId());
        //System.out.println(ticket);

        //TicketService ticketService = new TicketService();
        //System.out.println();
        //ticketService.update(String.valueOf(ticket.getId()), ticket);


        //System.out.println(filmService.readAll());















    }
}
