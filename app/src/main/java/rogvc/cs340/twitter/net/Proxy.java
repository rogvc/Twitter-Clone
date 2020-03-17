package rogvc.cs340.twitter.net;

import android.graphics.Bitmap;

import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import rogvc.cs340.twitter.model.domain.Post;
import rogvc.cs340.twitter.model.domain.User;

public class Proxy {
    Map<String, User> users = new HashMap<>();
    Map<String, Bitmap> images = new HashMap<>();
    Map<String, String> videos = new HashMap<>();

    public Map<String, User> getUsers() {
        return users;
    }

    public void setUsers(Map<String, User> users) {
        this.users = users;
    }

    public void addToUsers(User user) {
        this.users.put(user.getHandle(), user);
    }

    public void removeFromUsers(String handle) {
        this.users.remove(handle);
    }

    public User getUser(String handle) {
        return users.get(handle);
    }

    public Map<String, Bitmap> getImages() {
        return images;
    }

    public void setImages(Map<String, Bitmap> images) {
        this.images = images;
    }

    public void addToImages(String tag, Bitmap image) {
        this.images.put(tag, image);
    }

    public void removeFromImages(String tag) {
        this.images.remove(tag);
    }

    public Bitmap getImage(String tag) {
        return images.get(tag);
    }

    public Map<String, String> getVideos() {
        return videos;
    }

    public void setVideos(Map<String, String> videos) {
        this.videos = videos;
    }

    public void addToVideos(String tag, String video) {
        this.videos.put(tag, video);
    }

    public void removeFromVideos(String tag) {
        this.videos.remove(tag);
    }

    public String getVideo(String tag) {
        return videos.get(tag);
    }

    //login and Register

    public User login(String handle) {

        return users.get(handle);

    }

    public User register(User userToAdd) {

        users.put(userToAdd.getHandle(), userToAdd);
        return login(userToAdd.getHandle());

    }

    public boolean signOut(User userToSignOut) {
        //Change auth tokens and whatnot once I have the server ready;
        return true;
    }

    //Follow and Unfollow

    public boolean follow(String followerHandle, String userToBeFollowed) {
        User follower = users.get(followerHandle);
        User toBeFollowed = users.get(userToBeFollowed);

        toBeFollowed.getFollowers().put(followerHandle, follower);

        follower.getFollowing().put(userToBeFollowed, toBeFollowed);

        for (Post post : toBeFollowed.getStory()){
            follower.getFeed().add(post);
        }

        return true;
    }

    public boolean unfollow(String oldFollowerHandle, String userToBeUnfollowed) {
        User oldFollower = users.get(oldFollowerHandle);
        User toBeUnfollowed = users.get(userToBeUnfollowed);

        toBeUnfollowed.getFollowers().remove(oldFollowerHandle);

        oldFollower.getFollowing().remove(userToBeUnfollowed);

        return true;
    }

    public boolean post(String handle, Post post) {
        users.get(handle).addToStory(post);
        users.get(handle).addToFeed(post);

        addToFollowersFeed(users.get(handle), post);

        return true;
    }

    //Will get changed/removed when I add server functionality:

    final int NUMBER_OF_FAKE_USERS = 50;

    public Proxy() {
        initializeRandomUsers();
        initializeConstantUsers();
        initializeRandomFollowers();
        initializeRandomFeedAndStory();
    }

    void initializeRandomUsers() {

        for (int i = 0; i < NUMBER_OF_FAKE_USERS; i++) {
            User user = new User("@" + "johndoe" + i, "John",
                    "John", "Doe", "");

            users.put(user.getHandle(), user);
        }
    }

    void initializeConstantUsers() {
        users.put("@admin", new User("@admin", "admin",
                "Admin", "dude", ""));
        users.put("@link", new User("@link", "link",
                "Link", "dude", ""));
    }

    void initializeRandomFollowers() {
        SecureRandom random = new SecureRandom();

        for (Map.Entry<String, User> u : users.entrySet()) {
            if (random.nextBoolean()) {
                if (!u.getValue().getHandle().equals("@admin")) {
                    users.get("@admin").addToFollowers(u.getValue());
                    u.getValue().follow(users.get("@admin"));
                }
                if (!u.getValue().getHandle().equals("@link")) {
                    users.get("@link").addToFollowers(u.getValue());
                    u.getValue().follow(users.get("@link"));
                }
            }

            if (random.nextBoolean()) {
                if (!u.getValue().getHandle().equals("@admin")) {
                    users.get("@admin").follow(u.getValue());
                    u.getValue().addToFollowers(users.get("@admin"));
                }
                if (!u.getValue().getHandle().equals("@link")) {
                    users.get("@link").follow(u.getValue());
                    u.getValue().addToFollowers(users.get("@link"));
                }
            }
        }
    }

    void initializeRandomFeedAndStory() {
        for (Map.Entry<String, User> u : users.entrySet()) {
            Post randomPost = new Post(u.getValue(),
                    "Hi, my name is " + u.getValue().getName(),
                    new Date().toString(), 0);
            u.getValue().addToFeed(randomPost);
            u.getValue().addToStory(randomPost);
            addToFollowersFeed(u.getValue(), randomPost);
        }
    }

    void addToFollowersFeed(User userThatPosted, Post postToAdd) {
        for (Map.Entry<String, User> userEntry : userThatPosted.getFollowers().entrySet()) {
            userEntry.getValue().addToFeed(postToAdd);
        }
    }

}
