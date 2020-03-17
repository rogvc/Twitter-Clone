package rogvc.cs340.twitter.presenter.exception;

public class UserAlreadyInTheDatabaseException extends Exception {
    public UserAlreadyInTheDatabaseException() {
        super("User already in the database. Try again.");
    }
}
