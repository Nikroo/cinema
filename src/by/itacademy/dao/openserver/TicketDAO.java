package by.itacademy.dao.openserver;

import by.itacademy.dao.Dao;
import by.itacademy.service.film.Ticket;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TicketDAO extends AbstractConnection implements Dao<Ticket> {

    @Override
    public boolean create(Ticket ticket) {
        try {
            PreparedStatement statement = getConnection()
                    .prepareStatement
                            ("INSERT INTO tickets (user, film, place, price) VALUES (?,?,?,?)");
            statement.setInt(1, ticket.userId());
            statement.setInt(2, ticket.getFilmId());
            statement.setInt(3, ticket.getPlace());
            statement.setInt(4, ticket.getPrice());
            statement.execute();
            return true;
        } catch (MySQLIntegrityConstraintViolationException e) {
            System.out.println(e.getMessage());
            return false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }
    @Override
    public Optional<Ticket> read(String id) {
        Optional<Ticket> optionalFilm;
        Ticket ticket = new Ticket();
        try {
            PreparedStatement statement = getConnection()
                    .prepareStatement("SELECT id, user, film, place, price FROM tickets WHERE id=?");

            statement.setString(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                ticket.setId(rs.getInt("id"));
                ticket.setUser(rs.getInt("user"));
                ticket.setFilmId(rs.getInt("film"));
                ticket.setPlace(rs.getInt("place"));
                ticket.setPrice(rs.getInt("price"));
            }
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            optionalFilm = Optional.of(ticket);
            try {
                close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return optionalFilm;
    }


    public void update(int id, Ticket ticket) {
        try {
            PreparedStatement statement = getConnection()
                    .prepareStatement("UPDATE tickets SET user=?, film=?, place=?, price=? WHERE id=?");
            statement.setInt(1, ticket.userId());
            statement.setInt(2, ticket.getFilmId());
            statement.setInt(3, ticket.getPlace());
            statement.setInt(4, ticket.getPrice());
            statement.setInt(5, id);
            statement.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

    }

    public boolean delete(String place) {
        try {
            PreparedStatement statement = getConnection()
                    .prepareStatement("DELETE FROM tickets WHERE place = ?");
            statement.setString(1, place);
            statement.execute();
            return true;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }

    public List<Ticket> readAll() {
        List<Ticket> tickets = new ArrayList<>();
        try {
            PreparedStatement statement = getConnection()
                    .prepareStatement("SELECT id, user, film, place, price FROM tickets");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                tickets.add(
                        new Ticket(rs.getInt(1),
                                rs.getInt(2),
                                rs.getInt(3),
                                rs.getInt(4),
                                rs.getInt(5)));
            }
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return tickets;
    }

    public List<Ticket> readByFilm(int filmId) {
        List<Ticket> tickets = new ArrayList<>();
        try {
            PreparedStatement statement = getConnection()
                    .prepareStatement("SELECT id, user, film, place, price FROM tickets WHERE film=?");
            statement.setInt(1, filmId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                tickets.add(
                        new Ticket(rs.getInt(1),
                                rs.getInt(2),
                                rs.getInt(3),
                                rs.getInt(4),
                                rs.getInt(5)));
            }
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return tickets;
    }

    public List<Ticket> readByUser(int userId) {
        List<Ticket> tickets = new ArrayList<>();
        try {
            PreparedStatement statement = getConnection()
                    .prepareStatement("SELECT id, user, film, place, price FROM tickets WHERE user=?");
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                tickets.add(
                        new Ticket(rs.getInt(1),
                                rs.getInt(2),
                                rs.getInt(3),
                                rs.getInt(4),
                                rs.getInt(5)));
            }
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return tickets;
    }

    enum SQLFilm {
        GET_ALL("SELECT users.id, users.login, users.password, roles.role_id, roles.name FROM users LEFT JOIN roles ON users.role = roles.role_id"),
        GET("SELECT users.id, users.login, users.password, roles.role_id, roles.name FROM users LEFT JOIN roles ON users.role = roles.role_id WHERE users.login = (?)"),
        INSERT("INSERT INTO users (login, password, role) VALUES (?,?,?)"),
        DELETE("DELETE FROM users WHERE id = (?) AND login = (?) AND password = (?) RETURNING id"),
        UPDATE("UPDATE users SET password = (?) WHERE id = (?) RETURNING id");

        String QUERY;

        SQLFilm(String QUERY) {
            this.QUERY = QUERY;
        }
    }
}
