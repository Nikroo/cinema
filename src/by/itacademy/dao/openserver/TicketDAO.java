package by.itacademy.dao.openserver;

import by.itacademy.dao.Dao;
import by.itacademy.exception.DaoException;
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
                            (SQLTicket.INSERT.QUERY);
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
                    .prepareStatement(SQLTicket.GET.QUERY);

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

    @Override
    public boolean update(int id, Ticket ticket) throws DaoException {
        try {
            PreparedStatement statement = getConnection()
                    .prepareStatement(SQLTicket.UPDATE.QUERY);
            statement.setInt(1, ticket.userId());
            statement.setInt(2, ticket.getFilmId());
            statement.setInt(3, ticket.getPlace());
            statement.setInt(4, ticket.getPrice());
            statement.setInt(5, id);
            statement.execute();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DaoException(DaoException._UPDATE_TICKET_FAILED);
        } finally {
            try {
                close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @Override
    public boolean delete(String place) {
        try {
            PreparedStatement statement = getConnection()
                    .prepareStatement(SQLTicket.DELETE.QUERY);
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

    @Override
    public List<Ticket> readAll() {
        List<Ticket> tickets = new ArrayList<>();
        try {
            PreparedStatement statement = getConnection()
                    .prepareStatement(SQLTicket.GET_ALL.QUERY);
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
                    .prepareStatement(SQLTicket.READ_BY_FILM.QUERY);
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

    public boolean deleteByFilm(String filmId) {
        try {
            PreparedStatement statement = getConnection()
                    .prepareStatement(SQLTicket.DELETE_BY_FILM.QUERY);
            statement.setString(1, filmId);
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

    public List<Ticket> readByUser(int userId) {
        List<Ticket> tickets = new ArrayList<>();
        try {
            PreparedStatement statement = getConnection()
                    .prepareStatement(SQLTicket.READ_BY_USER.QUERY);
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

    enum SQLTicket {
        GET_ALL("SELECT id, user, film, place, price FROM tickets"),
        GET("SELECT id, user, film, place, price FROM tickets WHERE id=?"),
        INSERT("INSERT INTO tickets (user, film, place, price) VALUES (?,?,?,?)"),
        DELETE("DELETE FROM tickets WHERE place = ?"),
        READ_BY_FILM("SELECT id, user, film, place, price FROM tickets WHERE film=?"),
        DELETE_BY_FILM("DELETE FROM tickets WHERE film = ?"),
        READ_BY_USER("SELECT id, user, film, place, price FROM tickets WHERE user=?"),
        UPDATE("UPDATE tickets SET user=?, film=?, place=?, price=? WHERE id=?");

        String QUERY;

        SQLTicket(String QUERY) {
            this.QUERY = QUERY;
        }
    }
}
