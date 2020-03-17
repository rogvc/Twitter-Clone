package rogvc.cs340.twitter.model;

import android.graphics.Bitmap;

import java.util.Map;
import java.util.Scanner;

import rogvc.cs340.twitter.model.domain.Mention;
import rogvc.cs340.twitter.model.domain.Post;
import rogvc.cs340.twitter.model.domain.User;
import rogvc.cs340.twitter.net.Proxy;
import rogvc.cs340.twitter.presenter.IPresenter;

public class Model implements IModel {
    Proxy proxy = new Proxy();

    IPresenter presenter;

    public Model(IPresenter presenter) {
        this.presenter = presenter;
    }

    public User login(String handle, String password) {
        Map<String, User> userList = proxy.getUsers();

        if (!userList.containsKey(handle)) {
            return null;
        } else if (userList.get(handle).getPassword().equals(password)) {
            presenter.onLoginSuccessful(proxy.login(handle));
            return proxy.login(handle);
        }

        return null;
    }

    public User register (User userToAdd) {
        Map<String, User> userList = proxy.getUsers();

        if (userList.containsKey(userToAdd.getHandle())) {
            return null;
        } else {
            User retMe = proxy.register(userToAdd);
            presenter.onLoginSuccessful(retMe);
            return retMe;
        }
    }

    public boolean signOut(User userToSignOut) {
        return proxy.signOut(userToSignOut);
    }

    public User getUser(String handle) {
        return proxy.getUser(handle);
    }

    public void addImage(String tag, Bitmap image) {
        proxy.addToImages(tag, image);
    }

    public Bitmap getImage(String tag) {
        return proxy.getImage(tag);
    }

    public String getVideo(String tag) {
        return proxy.getVideo(tag);
    }

    public boolean follow(String followerHandle, String userToBeFollowedHandle) {
        return proxy.follow(followerHandle, userToBeFollowedHandle);
    }

    public boolean unfollow(String oldFollowerHandle, String userToBeUnfollowed) {
        return proxy.unfollow(oldFollowerHandle, userToBeUnfollowed);
    }

    public boolean post(String handle, Post post) {

        post = parseMentions(post);

        presenter.onPostSuccessful();

        return proxy.post(handle, post);
    }

    Post parseMentions(Post post) {
        Scanner scannie = new Scanner(post.getBody());

        while (scannie.hasNext()) {
            String word = scannie.next();
            if (word.charAt(0) == '@'){
                User user = getUser(word);
                if (user == null) {
                    continue;
                }
                int initPos = post.getBody().indexOf(word);
                post.addToMentions(new Mention(user, initPos, initPos + word.length()));
            }
        }

        return post;
    }

}
