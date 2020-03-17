package rogvc.cs340.twitter.model.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Post {
    final int SIMPLE = 0;
    final int PHOTO = 1;
    final int VIDEO = 2;

    private String timeStamp;
    private String body;
    private User user;
    private String mediaURL = "";
    private int type;

    private List<Mention> mentions = new ArrayList<>();
    private List<Hyperlink> hyperlinks = new ArrayList<>();

    public Post(User user, String body, String timeStamp, int type) {
        this.user = user;
        this.body = body;
        this.timeStamp = timeStamp;
        this.type = type;
        parseHyperlinks();
        //parseMentions();
    }

    public Post(User user, String body, String timeStamp, int type, String mediaURL) {
        this.user = user;
        this.body = body;
        this.timeStamp = timeStamp;
        this.type = type;
        this.mediaURL = mediaURL;
        parseHyperlinks();
        //parseMentions();
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getMediaURL() {
        return mediaURL;
    }

    public void setMediaURL(String mediaURL) {
        this.mediaURL = mediaURL;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<Mention> getMentions() {
        return mentions;
    }

    public void setMentions(List<Mention> mentions) {
        this.mentions = mentions;
    }

    public void addToMentions(Mention mention) {
        mentions.add(mention);
    }

    public void removeFromMentions(Mention mention) {
        mentions.remove(mention);
    }

    public List<Hyperlink> getHyperlinks() {
        return hyperlinks;
    }

    public void setHyperlinks(List<Hyperlink> hyperlinks) {
        this.hyperlinks = hyperlinks;
    }

    public void addToHyperlinks(Hyperlink hyperlink) {
        hyperlinks.add(hyperlink);
    }

    public void removeFromHyperlink(Hyperlink hyperlink) {
        hyperlinks.remove(hyperlink);
    }

    private void parseHyperlinks() {
        Scanner scannie = new Scanner(body);

        while (scannie.hasNext()) {
            String word = scannie.next();
            if (word.contains("http://") || word.contains("https://")){
                int initPos = body.indexOf(word);
                hyperlinks.add(new Hyperlink(word, initPos, initPos + word.length()));
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return getType() == post.getType() &&
                getTimeStamp().equals(post.getTimeStamp()) &&
                getBody().equals(post.getBody()) &&
                getUser().equals(post.getUser()) &&
                getMediaURL().equals(post.getMediaURL()) &&
                getMentions().equals(post.getMentions()) &&
                getHyperlinks().equals(post.getHyperlinks());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTimeStamp(), getBody(), getUser(), getMediaURL(), getType(), getMentions(), getHyperlinks());
    }
}
