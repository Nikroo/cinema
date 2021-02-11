package by.itacademy.view;

import by.itacademy.logging.CinemaLogger;
import by.itacademy.service.film.Film;
import by.itacademy.service.film.FilmService;
import by.itacademy.service.film.Ticket;
import by.itacademy.service.film.TicketService;
import by.itacademy.service.user.User;
import by.itacademy.service.user.UserService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.Scanner;
import java.util.logging.Level;
import static by.itacademy.language.Constant.*;

public class CommandLineController implements Controller {

    private UserService userService;
    private FilmService filmService;
    private TicketService ticketService;
    Optional<User> optionalUser;
    DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    private Scanner scanner = new Scanner(System.in);
    private static String choice = "";

    public CommandLineController(UserService userService, FilmService filmService, TicketService ticketService){
    this.userService = userService;
    this.filmService = filmService;
    this.ticketService = ticketService;
    }

    public void start() {
        CinemaLogger.LOGGER.log(Level.INFO,CINEMA_START);
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
                case "0":
                    scanner.close();
                    return;
                default:
                    System.out.println(INCORRECT_INPUT + choice);
                    break;
            }
        }
    }

    public void signIn() {
        while (true) {
            System.out.println("\n"+LOGIN);
            String login = scanner.next();
            System.out.println(PASSWORD);
            String password = scanner.next();

            optionalUser = userService.checkAccess(login, password);

            if(optionalUser.isPresent()) {
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
                        System.out.println(INCORRECT_INPUT + choice);
                        break;
                }
            }else{
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
            if(userService.create(new User(newLogin, newPassword, new User.Role(3)))){
                System.out.println(USER_REGISTERED_SUCCESSFULLY);
            }
            break;
        }
    }

    public void adminMenu() {
        CinemaLogger.LOGGER.log(Level.INFO, optionalUser.get().getLogin()+ ENTERED_ADMIN_MENU);
        while (true) {
            System.out.println(ENTERED_ADMIN_MENU);
            System.out.println(_1_SHOW_ALL_USERS);
            System.out.println(_2_UPDATE_USER);
            System.out.println(_3_DELETE_USER);
            System.out.println(_4_SHOW_ALL_EVENTS);
            System.out.println(_5_UPDATE_EVENTS);
            System.out.println(_6_DELETE_EVENTS);
            System.out.println(EXIT);
            getChoice();
            switch (choice) {
                case "1":
                    CinemaLogger.LOGGER.log(Level.INFO, _1_SHOW_ALL_USERS);
                    for (User element : userService.readAll()) {
                        System.out.println(element);
                    }
                    break;
                case "2":
                    CinemaLogger.LOGGER.log(Level.INFO, _2_UPDATE_USER);
                    updateUser();
                    break;
                case "3":
                    CinemaLogger.LOGGER.log(Level.INFO, _3_DELETE_USER);
                    System.out.println(USERNAME_TO_REMOVE);
                    String login = scanner.next();
                    if(userService.remove(login)){
                        System.out.println(USER_DELETED_SUCCESSFULLY);
                    }else{
                        System.out.println(USER_DELETED_FILED);
                    }
                    break;
                case "4":
                    CinemaLogger.LOGGER.log(Level.INFO, _4_SHOW_ALL_EVENTS);
                    for (Film element : filmService.readAll()) {
                        System.out.println(element);
                    }
                    break;
                case "5":
                    CinemaLogger.LOGGER.log(Level.INFO, _5_UPDATE_EVENTS);
                    updateEvents();
                    break;
                case "6":
                    CinemaLogger.LOGGER.log(Level.INFO, _6_DELETE_EVENTS);
                    System.out.println(USERNAME_TO_REMOVE);
                    scanner.nextLine();
                    String events = scanner.nextLine();
                    filmService.remove(events);
                    break;
                case "0":
                    CinemaLogger.LOGGER.log(Level.INFO, EXIT);
                    return;
                default:
                    System.out.println(INCORRECT_INPUT + choice);
                    break;
            }
        }
    }

    private void updateUser() {
        CinemaLogger.LOGGER.log(Level.INFO, UPDATE_USER_MENU);
        System.out.println(UPDATE_USER_MENU);
        System.out.println(USERNAME_TO_BE_CHANGED);
        String login = scanner.next();
        User tempUser;
        optionalUser = userService.read(login);
        if (optionalUser.isEmpty()){
            return;
        }else{
            tempUser = optionalUser.get();
        }

        User changedUser = new User();
        System.out.println(tempUser);
        System.out.println(WHICH_PARAMETER_CHANGED);
        while (true) {
            System.out.println("\n"+USER_PARAMETERS);
            System.out.println(_1_LOGIN);
            System.out.println(_2_PASSWORD);
            System.out.println(_3_ROLE);
            System.out.println(EXIT);
            getChoice();
            switch (choice) {
                case "1":
                    String changedLogin = "";
                    System.out.println(ENTER_NEW_LOGIN);
                    changedLogin = scanner.next();
                    changedUser.setLogin(changedLogin);
                    changedUser.setPassword(tempUser.getPassword());
                    changedUser.setRole(tempUser.getRole());
                    userService.update(tempUser.getId(), changedUser);
                    break;
                case "2":
                    String changedPassword = "";
                    System.out.println(ENTER_NEW_PASSWORD);
                    changedPassword = scanner.next();
                    User changedPasswordUser = new User(
                            tempUser.getLogin(),
                            changedPassword,
                            tempUser.getRole());
                    userService.update(tempUser.getId(), changedPasswordUser);
                    break;
                case "3":
                    System.out.println(ENTER_NEW_ROLE);

                    while (!scanner.hasNextInt()) {
                        scanner.next();
                        System.out.println(_1ADMIN_2_MANAGER_3_USER);
                    }
                    int changedRole = scanner.nextInt();
                    changedUser.setLogin(tempUser.getLogin());
                    changedUser.setPassword(tempUser.getPassword());
                    changedUser.setRole(new User.Role(changedRole));
                    userService.update(tempUser.getId(), changedUser);
                    break;
                case "0":
                    return;
                default:
                    System.out.println(INCORRECT_INPUT + choice);
                    break;
            }
        }
    }

    private void updateEvents() {
        CinemaLogger.LOGGER.log(Level.INFO, UPDATE_EVENTS_MENU);
        System.out.println(UPDATE_EVENTS_MENU);
        System.out.println(EVENTS_TO_CHANGED);
        scanner.nextLine();
        String film = scanner.nextLine();
        Film tempEvents = filmService.read(film);
        Film changedEvents = new Film();
        System.out.println(tempEvents);
        System.out.println(WHICH_EVENTS_PARAMETER_CHANGED);
        while (true) {
            System.out.println(EVENTS_PARAMETERS);
            System.out.println(_1_NAME);
            System.out.println(_2_DATE_TIME);
            System.out.println(EXIT);
            getChoice();
            switch (choice) {
                case "1":
                    System.out.println(ENTER_NEW_NAME);
                    String changedName = "";
                    scanner.nextLine();
                    changedName = scanner.nextLine();
                    changedEvents.setName(changedName);
                    changedEvents.setDateTime(tempEvents.getDateTime());
                    filmService.update(tempEvents.getId(), changedEvents);
                    break;
                case "2":
                    System.out.println(ENTER_DATE_TIME_FORMAT);
                    String changedDateTime = "";
                    try{
                        scanner.nextLine();
                        changedDateTime = scanner.nextLine();
                        LocalDateTime date = LocalDateTime.parse(changedDateTime, formatter);
                        changedEvents.setDateTime(date);
                    }catch(DateTimeParseException e){
                        System.out.println(WRONG_DATE);
                        break;
                    }
                    changedEvents.setName(tempEvents.getName());
                    filmService.update(tempEvents.getId(), changedEvents);
                    break;
                case "0":
                    return;
                default:
                    System.out.println(INCORRECT_INPUT + choice);
                    break;
            }
        }
    }

    public void userMenu() {
        CinemaLogger.LOGGER.log(Level.INFO, optionalUser.get().getLogin()+ ENTERED_USER_MENU);
        while (true) {
            System.out.println(USER_MENU);
            System.out.println(_1_SHOW_ALL_EVENTS);
            System.out.println(_2_BUY_TICKETS);
            System.out.println(_3_RETURN_TICKETS);
            System.out.println(_4_SHOW_PURCHASED_TICKETS);
            System.out.println(EXIT);
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
                case "0":
                    return;
                default:
                    System.out.println(INCORRECT_INPUT + choice);
                    break;
            }
        }
    }

    private void returnTicket(int userId) {
        System.out.println("Enter tickets id to be return!");
        while (!scanner.hasNextInt()) {
            scanner.next();
            System.out.println("Enter tickets id:");
        }
        int returnTicket = scanner.nextInt();
        if(ticketService.returnTicket(returnTicket, userId)){
            System.out.println("Ticket "+returnTicket+" was successfully returned!");
        }else{
            System.out.println("Check tickets id!");
        }
    }

    private void buyTicket(int userId) {
        System.out.println(ENTER_TICKETS_ID);
        while (!scanner.hasNextInt()) {
            scanner.next();
            System.out.println(ENTER_TICKETS_ID);
        }
        int buyTicket = scanner.nextInt();
        if(ticketService.buyTicket(buyTicket, userId)){
            System.out.println(TICKET_BUY_SUCCESSFULLY+buyTicket);
        }else{
            System.out.println(TICKET_BUY_FILED+buyTicket);
        }
    }

    public void managerMenu() {
        CinemaLogger.LOGGER.log(Level.INFO, optionalUser.get().getLogin()+ ENTERED_MANAGER_MENU);
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
                case "0":
                    return;
                default:
                    System.out.println(INCORRECT_INPUT + choice);
                    break;
            }
        }
    }

    public void getChoice() {
        choice = "";
        choice = scanner.next();
    }

}

