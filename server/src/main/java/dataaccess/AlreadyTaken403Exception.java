package dataaccess;

public class AlreadyTaken403Exception extends DataAccessException {
    public AlreadyTaken403Exception(String message) {
        super(message);
    }
}
