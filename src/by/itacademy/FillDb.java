package by.itacademy;

import by.itacademy.service.film.Film;
import by.itacademy.service.film.FilmService;
import by.itacademy.service.film.Ticket;
import by.itacademy.service.film.TicketService;

import java.time.LocalDateTime;
import java.time.Month;

public class FillDb {

    public static void main(String[] args) {


        FilmService filmService = new FilmService();

        LocalDateTime date0 = LocalDateTime.of(2021, Month.FEBRUARY, 27, 9, 0);
        LocalDateTime date1 = LocalDateTime.of(2021, Month.FEBRUARY, 27, 12, 0);
        LocalDateTime date2 = LocalDateTime.of(2021, Month.FEBRUARY, 27, 17, 0);
        LocalDateTime date3 = LocalDateTime.of(2021, Month.FEBRUARY, 28, 9, 0);
        LocalDateTime date4 = LocalDateTime.of(2021, Month.FEBRUARY, 28, 12, 0);
        LocalDateTime date5 = LocalDateTime.of(2021, Month.MARCH, 1, 9, 0);
        LocalDateTime date6 = LocalDateTime.of(2021, Month.MARCH, 1, 12, 0);
        LocalDateTime date7 = LocalDateTime.of(2021, Month.MARCH, 1, 17, 0);
        LocalDateTime date8 = LocalDateTime.of(2021, Month.MARCH, 2, 9, 0);
        LocalDateTime date9 = LocalDateTime.of(2021, Month.MARCH, 2, 12, 0);




        filmService.create(new Film("Grave of the Fireflies", date1));
        setDb(filmService.read("Grave of the Fireflies").getId());

        filmService.create(new Film("My Neighbor Totoro", date2));
        setDb(filmService.read("My Neighbor Totoro").getId());

        filmService.create(new Film("Kiki's Delivery Service", date3));
        setDb(filmService.read("Kiki's Delivery Service").getId());

        filmService.create(new Film("Only Yesterday", date4));
        setDb(filmService.read("Only Yesterday").getId());

        filmService.create(new Film("Porco Rosso", date5));
        setDb(filmService.read("Porco Rosso").getId());

        filmService.create(new Film("Ocean Waves", date6));
        setDb(filmService.read("Ocean Waves").getId());

        filmService.create(new Film("Pom Poko", date7));
        setDb(filmService.read("Pom Poko").getId());

        filmService.create(new Film("Whisper of the Heart", date8));
        setDb(filmService.read("Whisper of the Heart").getId());

        filmService.create(new Film("Princess Mononoke", date9));
        setDb(filmService.read("Princess Mononoke").getId());

        filmService.create(new Film("Castle in the Sky", date0));
        setDb(filmService.read("Castle in the Sky").getId());




    }

    private static void setDb(int i) {

        TicketService ticketService = new TicketService();

        for (int j = 0; j < 10; j++) {
            ticketService.create(new Ticket(-1,i,j, 5));
        }
        for (int j = 10; j < 20; j++) {
            ticketService.create(new Ticket(-1,i,j, 10));
        }
    }
}
