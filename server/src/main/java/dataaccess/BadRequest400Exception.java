package dataaccess;

public class BadRequest400Exception extends DataAccessException {
    public BadRequest400Exception(String message) {
        super(message);
    }
}
