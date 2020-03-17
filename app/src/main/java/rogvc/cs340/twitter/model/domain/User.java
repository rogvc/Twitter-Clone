package rogvc.cs340.twitter.model.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class User {
    private final String firstName;
    private final String lastName;
    private final String handle;
    private String imageUrl;
    private String password;

    private Map<String, User> followers = new HashMap<>();
    private Map<String, User> following = new HashMap<>();

    private List<Post> feed = new ArrayList<>();
    private List<Post> story = new ArrayList<>();

    public User(String handle, String password, String firstName, String lastName, String imageURL) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.handle = handle;
        this.imageUrl = imageURL;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getName() {
        return String.format("%s %s", firstName, lastName);
    }

    public String getHandle() {
        return handle;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getPassword() {
        return password;
    }

    public void setImageUrl (String newURL) {
        this.imageUrl = newURL;
    }

    public void follow (User user) {
        following.put(user.getHandle(), user);
    }

    public void addToFollowers (User user) {
        followers.put(user.getHandle(), user);
    }

    public void unfollow (User user) {
        following.remove(user);
    }

    public void removeFromFollowers (User user) {
        followers.remove(user);
    }

    public Map<String, User> getFollowing () {
        return following;
    }

    public User[] getFollowingAsArray() {
        User [] retMe = new User[following.size()];

        int i = 0;

        for (Map.Entry<String, User>  u : following.entrySet()) {
            retMe[i++] = u.getValue();
        }

        return retMe;
    }

    public Map<String, User> getFollowers () {
        return followers;
    }

    public User[] getFollowersAsArray() {
        User [] retMe = new User[followers.size()];

        int i = 0;

        for (Map.Entry<String, User>  u : followers.entrySet()) {
            retMe[i++] = u.getValue();
        }

        return retMe;
    }

    public void addToStory(Post post) {
        story.add(post);
    }

    public List<Post> getStory() {
        return story;
    }

    public List<Post> getFeed() {
        return feed;
    }

    public void addToFeed(Post post) {
        feed.add(post);
    }

    public void setFeed(List<Post> feed) {
        this.feed = feed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return handle.equals(user.handle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(handle);
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", handle='" + handle + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
