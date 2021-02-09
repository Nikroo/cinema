package by.itacademy.view;

import by.itacademy.dao.openserver.UserDAO;
import by.itacademy.service.film.Film;
import by.itacademy.service.film.FilmService;
import by.itacademy.service.film.Ticket;
import by.itacademy.service.film.TicketService;
import by.itacademy.service.user.User;
import by.itacademy.service.user.UserService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Scanner;

public class CommandLineController implements Controller {

    private UserService userService = new UserService(new UserDAO());
    Optional<User> optionalUser;
    private FilmService filmService;
    private TicketService ticketService = new TicketService();
    DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    private Scanner scanner = new Scanner(System.in);
    private static String choice = "";

    public CommandLineController(FilmService service) {
        this.filmService = service;
    }

    public void start() {
        while (true) {
            System.out.println("\nCinema:");
            System.out.println("1. Sign in");
            System.out.println("2. Sign up");
            System.out.println("3. Exit");
            getChoice();
            switch (choice) {
                case "1":
                    signIn();
                    break;
                case "2":
                    signUp();
                    break;
                case "3":
                    scanner.close();
                    return;
                default:
                    System.out.println("Incorrect input: " + choice);
                    break;
            }
        }
    }

    public void signIn() {
        while (true) {
            System.out.println("\nLogin:");
            String login = scanner.next();
            System.out.println("Password:");
            String password = scanner.next();

            optionalUser = userService.checkAccess(login, password);

            switch (optionalUser.get().getRole().getId()) {
                case 1:
                    adminMenu();
                    return;
                case 2:
                    managerMenu();
                    return;
                case 3:
                    userMenu();
                    return;
                default:
                    System.out.println("Incorrect input: " + choice);
                    break;
            }
        }
    }

    public void signUp() {
        while (true) {
            System.out.println("Enter new login:");
            String newLogin = scanner.next();
            System.out.println("Enter password:");
            String newPassword = scanner.next();
            userService.create(new User(newLogin, newPassword, new User.Role(3)));
        }
    }

    public void adminMenu() {
        while (true) {
            System.out.println("\nAdmin menu:");
            System.out.println("1. Show all users");
            System.out.println("2. Update user");
            System.out.println("3. Delete user");
            System.out.println("4. Show all events");
            System.out.println("5. Update events");
            System.out.println("6. Delete events");
            System.out.println("7. Exit");
            getChoice();
            switch (choice) {
                case "1":
                    userService.readAll();
                    break;
                case "2":
                    updateUser();
                    break;
                case "3":
                    System.out.println("Enter films id to be remove!");
                    String login = scanner.next();
                    userService.remove(login);
                    break;
                case "4":
                    for (Film element : filmService.readAll()) {
                        System.out.println(element);
                    }
                    break;
                case "5":
                    updateEvents();
                    break;
                case "6":
                    System.out.println("Enter the events name to be remove!");
                    scanner.nextLine();
                    String events = scanner.nextLine();
                    filmService.remove(events);
                    break;
                case "7":
                    return;
                default:
                    System.out.println("Incorrect input: " + choice);
                    break;
            }
        }
    }

    private void updateUser() {
        System.out.println("\nUpdate user menu:");
        System.out.println("Enter the username to be changed?");
        String login = scanner.next();
        User tempUser = userService.read(login).get();
        User changedUser = new User();
        System.out.println(tempUser);
        System.out.println("Which user parameter should be changed?");
        while (true) {
            System.out.println("\nUser parameters:");
            System.out.println("1. login");
            System.out.println("2. password");
            System.out.println("3. role");
            System.out.println("4. exit");
            getChoice();
            switch (choice) {
                case "1":
                    String changedLogin = "";
                    System.out.println("Enter new login:");
                    changedLogin = scanner.next();
                    changedUser.setLogin(changedLogin);
                    changedUser.setPassword(tempUser.getPassword());
                    changedUser.setRole(tempUser.getRole());
                    userService.update(tempUser.getId(), changedUser);
                    break;
                case "2":
                    String changedPassword = "";
                    System.out.println("Enter new password:");
                    changedPassword = scanner.next();
                    User changedPasswordUser = new User(
                            tempUser.getLogin(),
                            changedPassword,
                            tempUser.getRole());
                    userService.update(tempUser.getId(), changedPasswordUser);
                    break;
                case "3":
                    System.out.println("Enter new role:");
                    int changedRole = scanner.nextInt();
                    changedUser.setLogin(tempUser.getLogin());
                    changedUser.setPassword(tempUser.getPassword());
                    changedUser.setRole(new User.Role(changedRole));
                    userService.update(tempUser.getId(), changedUser);
                    break;
                case "4":
                    return;
                default:
                    System.out.println("Incorrect input: " + choice);
                    break;
            }
        }
    }

    private void updateEvents() {
        System.out.println("\nUpdate events menu:");
        System.out.println("Enter the events name to be changed?");
        scanner.nextLine();
        String film = scanner.nextLine();
        Film tempEvents = filmService.read(film);
        Film changedEvents = new Film();
        System.out.println(tempEvents);
        System.out.println("Which events parameter should be changed?");
        while (true) {
            System.out.println("\nEvents parameters:");
            System.out.println("1. name");
            System.out.println("2. date & time");
            System.out.println("3. exit");
            getChoice();
            switch (choice) {
                case "1":
                    System.out.println("Enter new name:");
                    String changedName = "";
                    scanner.nextLine();
                    changedName = scanner.nextLine();
                    changedEvents.setName(changedName);
                    changedEvents.setDateTime(tempEvents.getDateTime());
                    filmService.update(tempEvents.getId(), changedEvents);
                    break;
                case "2":
                    System.out.println("Enter new date & time, format '2011-12-03T10:15:30':");
                    String changedDateTime = "";
                    scanner.nextLine();
                    changedDateTime = scanner.nextLine();
                    LocalDateTime date = LocalDateTime.parse(changedDateTime, formatter);
                    changedEvents.setName(tempEvents.getName());
                    changedEvents.setDateTime(date);
                    filmService.update(tempEvents.getId(), changedEvents);
                    break;
                case "3":
                    return;
                default:
                    System.out.println("Incorrect input: " + choice);
                    break;
            }
        }
    }

    public void userMenu() {
        while (true) {
            System.out.println("\nUser menu:");
            System.out.println("1. Show all events");
            System.out.println("2. Buy tickets");
            System.out.println("3. Return tickets");
            System.out.println("4. Show purchased tickets");
            getChoice();
            switch (choice) {
                case "1":
                    for (Film element : filmService.readAll()) {
                        System.out.println(element);
                    }
                    break;
                case "2":
                    buyTicket(optionalUser.get().getId());
                    break;
                case "3":
                    returnTicket(optionalUser.get().getId());
                    break;
                case "4":
                    for (Ticket element : ticketService.readByUser(optionalUser.get().getId())) {
                        System.out.print(element);
                    }
                    break;
                case "5":
                    updateEvents();
                    break;
                case "6":
                    System.out.println("Enter the events name to be remove!");
                    scanner.nextLine();
                    String events = scanner.nextLine();
                    filmService.remove(events);
                    break;
                case "7":
                    return;
                default:
                    System.out.println("Incorrect input: " + choice);
                    break;
            }
        }
    }

    private void returnTicket(int userId) {
        String returnTicket = "";
        System.out.println("Enter tickets id to be return!");
        returnTicket = scanner.next();
        ticketService.returnTicket(Integer.parseInt(returnTicket), userId);
    }

    private void buyTicket(int userId) {
        String buyTicket = "";
        System.out.println("Enter tickets id:");
        buyTicket = scanner.next();
        ticketService.buyTicket(Integer.parseInt(buyTicket), userId);
    }

    public void managerMenu() {
        String userId;
        while (true) {
            System.out.println("\nManager menu:");
            System.out.println("1. Show all events");
            System.out.println("2. Update events");
            System.out.println("3. Delete events");
            System.out.println("4. Show users");
            System.out.println("5. Buy a ticket to a user");
            System.out.println("6. Return user ticket");
            System.out.println("7. Exit");
            getChoice();
            switch (choice) {
                case "1":
                    for (Film element : filmService.readAll()) {
                        System.out.println(element);
                    }
                    break;
                case "2":
                    updateEvents();
                    break;
                case "3":
                    System.out.println("Enter the events name to be remove!");
                    scanner.nextLine();
                    String events = scanner.nextLine();
                    filmService.remove(events);
                    break;
                case "4":
                    userService.readAll();
                    break;
                case "5":
                    System.out.println("Enter user id:");
                    userId = scanner.next();
                    buyTicket(Integer.parseInt(userId));
                    break;
                case "6":
                    System.out.println("Enter user id:");
                    userId = scanner.next();
                    returnTicket(Integer.parseInt(userId));
                    break;
                case "7":
                    return;
                default:
                    System.out.println("Incorrect input: " + choice);
                    break;
            }
        }
    }

    public void getChoice() {
        choice = "";
        choice = scanner.next();
    }
}

