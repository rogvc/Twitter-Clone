package rogvc.cs340.twitter.presenter;

import android.graphics.Bitmap;

import rogvc.cs340.twitter.model.domain.User;
import rogvc.cs340.twitter.presenter.exception.EmptyFieldException;
import rogvc.cs340.twitter.presenter.exception.IncorrectCredentialsException;
import rogvc.cs340.twitter.presenter.exception.ServerErrorException;
import rogvc.cs340.twitter.presenter.exception.UserNotFoundException;
import rogvc.cs340.twitter.view.IView;

public interface IPresenter {

    void registerView(IView view);

    void deRegisterView(IView view);

    boolean onLoginSubmit(String handle, String password) throws EmptyFieldException, IncorrectCredentialsException;

    boolean onRegisterSubmit(String handle, String password,
                                 String firstName, String lastName,
                                 String tag, Bitmap image) throws EmptyFieldException, IncorrectCredentialsException ;

    void onLoginSuccessful(User user);

    boolean onRefresh() throws ServerErrorException;

    void onRefreshNeeded();

    void onRefreshSuccesful();

    boolean onSignOut();

    void onSignOutSuccessful();

    boolean onPost(String userHandle, String body, String imageURL, String videoURL, Bitmap image) throws ServerErrorException, EmptyFieldException;

    void onPostSuccessful();

    boolean onFollow(String newFollowerHandle, String userToBeFollowedHandle) throws ServerErrorException ;

    void onFollowSuccessful();

    boolean onUnfollow(String oldFollowerHandle, String userToBeunfollowedHandle) throws ServerErrorException;

    void onUnfollowSuccessful();

    User getActiveUser();

    User getDisplayedUser();

    void setDisplayedUser(User user);

    Bitmap getImage(String tag);

    boolean isFollowing(String handle);

    boolean onSearch(String handle) throws UserNotFoundException;

    void onSearchSuccessful();

}
