package by.itacademy;


import by.itacademy.dao.openserver.FilmDAO;
import by.itacademy.dao.openserver.UserDAO;
import by.itacademy.service.film.Film;
import by.itacademy.service.film.FilmService;
import by.itacademy.service.film.Ticket;
import by.itacademy.service.film.TicketService;
import by.itacademy.service.util.Hash;
import by.itacademy.service.user.UserService;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main extends Hash {
    public static void main(String[] args) {

        UserService userService = new UserService(new UserDAO());
        //service.create(new User("Anna@yandex.ru", "1988", new User.Role(1,"admin")));
        //service.update("Anna@yandex.ru", new User("Kirill@yandex.ru", "1988", new User.Role(1,"admin")));
        System.out.println(userService.checkAccess("Kirill@yandex.ru", "1988"));

        //service.create(new User("Vania@mail.ru", "1111"));
        //service.readAll();

/*        Ticket ticket = new Ticket(1,1,1,1,true);
        LocalDateTime today = LocalDateTime.now();

        List<Ticket> tickets = new ArrayList<>((Arrays.asList(ticket)));

        FilmService filmService = new FilmService();

        LocalDateTime dogville = LocalDateTime.of(2003, Month.APRIL, 30, 12, 0);
        LocalDateTime cuckoo = LocalDateTime.of(1975, Month.MARCH, 01, 12, 0);

        //filmService.create(new Film("Titanic", today, tickets));
        filmService.create(new Film("Dogville", dogville, tickets));
        System.out.println(filmService.read("Dogville"));

        if(filmService.read("Titanic").isPresent()){
            Film titanic = filmService.read("Titanic").get();
        }


        filmService.update("Titanic", new Film("One Flew Over the Cuckoo's", cuckoo, tickets));
        filmService.remove("Dogville");
        filmService.readAll();*/

        TicketService ticketService = new TicketService();

/*        ticketService.create(new Ticket(1,1,1,1,true));
        ticketService.create(new Ticket(2,2,2,2,false));
        ticketService.create(new Ticket(3,3,4,5,true));
        ticketService.create(new Ticket(4,4,4,4,true));
        ticketService.create(new Ticket(5,5,5,5,true));*/

        System.out.println(ticketService.read("2"));

        ticketService.remove("2");

        ticketService.update("5", new Ticket(77,77,77,77,true));

        ticketService.readAll();




    }
}
