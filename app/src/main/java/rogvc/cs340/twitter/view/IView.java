package rogvc.cs340.twitter.view;

import android.graphics.Bitmap;

import rogvc.cs340.twitter.model.domain.User;

public interface IView {

    boolean doLogin(String handle, String password);

    void onLoginSuccessful(String message);

    boolean doRegister(String handle, String password,
                       String firstName, String lastName,
                       String tag, Bitmap image);

    void onRegisterSuccessful(String message);

    boolean onPost(String userHandle, String body, String imageURL, String videoURL, Bitmap image);

    void onPostSuccessful(String message);

    boolean doSignOut();

    void onSignOutSuccessful(String message);

    void onFollowSuccessful(String message);

    void onUnfollowSuccessful(String message);

    void onTaskFail(String message);

    void onAttemptingTask(String message);

    void onRefreshNeeded();

    String getErrorMessage();

    User getActiveUser();

    User getDisplayedUser();

    void setDisplayedUser(User user);

    Bitmap getImage(String tag);

    boolean doRefresh();

    void onRefreshSuccessful();

    boolean isFollowing(String handle);

    boolean onSearch(String handle);

    void onSearchSuccessful();

    User getSearchedUser();
}
