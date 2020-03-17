package rogvc.cs340.twitter.presenter.exception;

public class IncorrectCredentialsException extends Exception {
    public IncorrectCredentialsException() {
        super("Credentials were incorrect. Try again.");
    }
}
