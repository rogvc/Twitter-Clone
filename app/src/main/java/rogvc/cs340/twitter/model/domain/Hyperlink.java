package rogvc.cs340.twitter.model.domain;

public class Hyperlink {
    private String url;
    private int initialPositionInText;
    private int lastPositionInText;

    Hyperlink(String url, int initialPositionInText, int lastPositionInText) {
        this.url = url;
        this.initialPositionInText = initialPositionInText;
        this.lastPositionInText = lastPositionInText;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
