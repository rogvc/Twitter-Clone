package rogvc.cs340.twitter.presenter;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rogvc.cs340.twitter.model.IModel;
import rogvc.cs340.twitter.model.Model;
import rogvc.cs340.twitter.model.domain.Post;
import rogvc.cs340.twitter.model.domain.User;
import rogvc.cs340.twitter.presenter.cache.Cache;
import rogvc.cs340.twitter.presenter.exception.EmptyFieldException;
import rogvc.cs340.twitter.presenter.exception.IncorrectCredentialsException;
import rogvc.cs340.twitter.presenter.exception.ServerErrorException;
import rogvc.cs340.twitter.presenter.exception.UserNotFoundException;
import rogvc.cs340.twitter.view.IView;

public class Presenter implements IPresenter {
    Cache cache = Cache.getInstance();

    IModel model = new Model(this);

    List<IView> views = new ArrayList<>();

    public Presenter() {
        cache.setPresenter(this);
    }

    public void registerView(IView view) {
        views.add(view);
    }

    public void deRegisterView(IView view) {
        views.remove(view);
    }

    public boolean onLoginSubmit(String handle, String password) throws EmptyFieldException, IncorrectCredentialsException {

        if(handle.isEmpty() || password.isEmpty()) {
            throw new EmptyFieldException();
        }

        User loggedIn = model.login(handle, password);

        if (loggedIn == null) {
            throw new IncorrectCredentialsException();
        }

        cache.setActiveUser(loggedIn);

        return true;
    }

    public boolean onRegisterSubmit(String handle, String password,
                                 String firstName, String lastName,
                                 String tag, Bitmap image) throws EmptyFieldException, IncorrectCredentialsException {

        if(handle.isEmpty() || password.isEmpty() ||
                firstName.isEmpty() || lastName.isEmpty() ||
                tag.isEmpty()) {
            throw new EmptyFieldException();
        }

        User loggedIn = model.register(new User(handle, password, firstName, lastName, tag));

        if (loggedIn == null) {
            throw new IncorrectCredentialsException();
        }

        model.addImage(tag, image);

        cache.setActiveUser(loggedIn);

        return true;
    }

    public void onLoginSuccessful(User activeUser) {
        cache.setActiveUser(activeUser);
        cache.setDisplayedUser(activeUser);
        notifyLoginSuccessful();
    }

    void notifyLoginSuccessful() {
        for (IView view : views) {
            view.onLoginSuccessful("Logged in successfully!");
        }
    }

    @Override
    public boolean onRefresh() throws ServerErrorException {

        User refreshed = model.getUser(cache.getActiveUser().getHandle());

        if (refreshed == null) {
            throw new ServerErrorException();
        }

        cache.setActiveUser(refreshed);
        cache.setDisplayedUser(refreshed);

        onRefreshSuccesful();

        return true;
    }

    @Override
    public void onRefreshNeeded() {
        for (IView view : views) {
            view.onRefreshNeeded();
        }
    }

    @Override
    public void onRefreshSuccesful() {
        for (IView view : views) {
            view.onRefreshSuccessful();
        }
    }

    @Override
    public boolean onSignOut() {
        model.signOut(cache.getActiveUser());

        cache.setDisplayedUser(null);
        cache.setActiveUser(null);

        onSignOutSuccessful();

        return true;
    }

    @Override
    public void onSignOutSuccessful() {
        for (IView view : views) {
            view.onSignOutSuccessful("Signed out successfully!");
        }
    }

    @Override
    public boolean onPost(String userHandle, String body, String imageURL, String videoURL, Bitmap image) throws ServerErrorException, EmptyFieldException {
        Post post;

        if (body.isEmpty()) {
            throw new EmptyFieldException();
        }

        if(imageURL.isEmpty()) {
            if (videoURL.isEmpty()) {
                post = new Post(model.getUser(userHandle), body,  new Date().toString(), 0);
            } else {
                post = new Post(model.getUser(userHandle), body,  new Date().toString(), 2);
                post.setMediaURL(videoURL);
            }
        } else {
            post = new Post(model.getUser(userHandle), body,  new Date().toString(), 1);
            post.setMediaURL(imageURL);
        }

        if (image != null) {
            model.addImage(imageURL, image);
        }

        if (model.post(userHandle, post)) {
            return true;
        } else {
            throw new ServerErrorException();
        }
    }

    @Override
    public void onPostSuccessful() {
        for (IView view : views) {
            view.onPostSuccessful("Posted successfully!");
        }
    }

    public boolean onFollow(String newFollowerHandle, String userToBeFollowedHandle) throws ServerErrorException {
        if (model.follow(newFollowerHandle, userToBeFollowedHandle)) {

            onFollowSuccessful();

            return true;
        } else {
            throw new ServerErrorException();
        }
    }

    @Override
    public void onFollowSuccessful() {
        for (IView view : views) {
            view.onFollowSuccessful("You are now following this user!");
        }
    }


    public boolean onUnfollow(String oldFollowerHandle, String userToBeunfollowedHandle) throws ServerErrorException {
        if (model.unfollow(oldFollowerHandle, userToBeunfollowedHandle)) {

            onUnfollowSuccessful();

            return true;
        } else {
            throw new ServerErrorException();
        }
    }

    @Override
    public void onUnfollowSuccessful() {
        for (IView view : views) {
            view.onUnfollowSuccessful("You have now unfollowed this user!");
        }
    }


    @Override
    public User getActiveUser() {
        return cache.getActiveUser();
    }

    @Override
    public User getDisplayedUser() {
        return cache.getDisplayedUser();
    }

    @Override
    public void setDisplayedUser(User user) {
        cache.setDisplayedUser(user);
    }

    @Override
    public Bitmap getImage(String tag) {
        return model.getImage(tag);
    }

    @Override
    public boolean isFollowing(String handle) {

        return cache.getActiveUser().getFollowing().containsKey(handle);

    }

    @Override
    public boolean onSearch(String handle) throws UserNotFoundException {

        User searchedUser = model.getUser(handle);

        if (searchedUser == null) {
            throw new UserNotFoundException();
        } else {
            cache.setDisplayedUser(searchedUser);
            onSearchSuccessful();
            return true;
        }

    }

    @Override
    public void onSearchSuccessful() {
        for (IView view : views) {
            view.onSearchSuccessful();
        }
    }
}
