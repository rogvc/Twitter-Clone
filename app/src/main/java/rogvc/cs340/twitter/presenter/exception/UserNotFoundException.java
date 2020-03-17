package rogvc.cs340.twitter.presenter.exception;

public class UserNotFoundException extends Exception {
    public UserNotFoundException() {
        super("User not in the database. Try Again.");
    }
}
