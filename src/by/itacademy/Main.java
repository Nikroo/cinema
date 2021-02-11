package by.itacademy;

import by.itacademy.dao.openserver.FilmDAO;
import by.itacademy.dao.openserver.TicketDAO;
import by.itacademy.dao.openserver.UserDAO;
import by.itacademy.service.film.FilmService;
import by.itacademy.service.film.TicketService;
import by.itacademy.service.user.UserService;
import by.itacademy.view.CommandLineController;
import by.itacademy.view.Controller;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserService(new UserDAO());
        FilmService filmService = new FilmService(new FilmDAO());
        TicketService ticketService = new TicketService(new TicketDAO());
        Controller controller = new CommandLineController(userService, filmService, ticketService);
        controller.start();
    }
}
