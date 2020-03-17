package rogvc.cs340.twitter.presenter.exception;

public class ServerErrorException extends Exception {
    public ServerErrorException() {
        super("Something went wrong. Try again.");
    }
}
