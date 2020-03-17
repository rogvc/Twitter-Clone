package rogvc.cs340.twitter.model;

import android.graphics.Bitmap;

import rogvc.cs340.twitter.model.domain.Post;
import rogvc.cs340.twitter.model.domain.User;

public interface IModel {
    User login(String handle, String password);

    User register(User userToAdd);

    boolean signOut(User userToSignOut);

    User getUser(String handle);

    void addImage(String tag, Bitmap image);

    Bitmap getImage(String tag);

    String getVideo(String tag);

    boolean follow(String followerHandle, String userToBeFollowedHandle);

    boolean unfollow(String oldFollowerHandle, String userToBeUnfollowed);

    boolean post(String handle, Post post);
}
