package rogvc.cs340.twitter.presenter.exception;

public class EmptyFieldException extends Exception {
    public EmptyFieldException() {
        super("Fields can't be empty. Try again.");
    }
}
