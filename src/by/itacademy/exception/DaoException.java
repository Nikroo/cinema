package by.itacademy.exception;

public class DaoException extends Exception {

    public final static String _FAIL_TO_USER_INSERT = "Wrong login or password!";
    public final static String _UPDATE_USER_FAILED = "User does not update!";
    public final static String _USER_NOT_CREATE = "User does not create!";
    public final static String _FILM_NOT_CREATE = "Film does not create!";
    public final static String _UPDATE_FILM_FAILED = "Film does not update!";
    public final static String _DELETE_FILM_FAILED = "Film does not delete!";
    public final static String _DELETE_USER_FAILED = "User does not delete!";
    public final static String _UPDATE_TICKET_FAILED = "Ticket does not update!";
    private String errorCode;

    public DaoException(String errorCode) {
        this.errorCode = errorCode;
    }

    public void getExceptionMessage() {
        System.out.println(errorCode);
    }
}

