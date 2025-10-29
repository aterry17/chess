package dataaccess;

public class Unauthorized401Exception extends DataAccessException {
    public Unauthorized401Exception(String message) {
        super(message);
    }
}
