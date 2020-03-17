package rogvc.cs340.twitter.model.domain;

public class Mention {
    private User userMentioned;
    private int initialPositionInText;
    private int lastPositionInText;

    public Mention() {
        super();
    }

    public Mention(User userMentioned, int initialPositionInText, int lastPositionInText) {
        this.userMentioned = userMentioned;
        this.initialPositionInText = initialPositionInText;
        this.lastPositionInText = lastPositionInText;
    }

    public User getUserMentioned() {
        return userMentioned;
    }

    public void setUserMentioned(User userMentioned) {
        this.userMentioned = userMentioned;
    }

    public int getInitialPositionInText() {
        return initialPositionInText;
    }

    public void setInitialPositionInText(int initialPositionInText) {
        this.initialPositionInText = initialPositionInText;
    }

    public int getLastPositionInText() {
        return lastPositionInText;
    }

    public void setLastPositionInText(int lastPositionInText) {
        this.lastPositionInText = lastPositionInText;
    }
}
