package rogvc.cs340.twitter.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;

import rogvc.cs340.twitter.R;
import rogvc.cs340.twitter.model.domain.User;
import rogvc.cs340.twitter.presenter.IPresenter;
import rogvc.cs340.twitter.presenter.cache.Cache;
import rogvc.cs340.twitter.presenter.exception.EmptyFieldException;
import rogvc.cs340.twitter.presenter.exception.ServerErrorException;
import rogvc.cs340.twitter.presenter.exception.UserNotFoundException;
import rogvc.cs340.twitter.view.IView;
import rogvc.cs340.twitter.view.fragment.Feed;
import rogvc.cs340.twitter.view.fragment.Followers;
import rogvc.cs340.twitter.view.fragment.Following;
import rogvc.cs340.twitter.view.fragment.HomeBar;
import rogvc.cs340.twitter.view.fragment.HomeSearch;
import rogvc.cs340.twitter.view.fragment.Story;

public class HomeActivity extends AppCompatActivity implements IView {
    Cache cache = Cache.getInstance();

    IPresenter presenter = cache.getPresenter();

    String errorMessage;

    FragmentManager fragmentManager;

    TabLayout tabs;

    FloatingActionButton refreshButton;

    Fragment activeFragment;
    Feed feedFragment;
    Story storyFragment;
    Following followingFragment;
    Followers followersFragment;

    Fragment homebar_active;
    HomeBar homebar_normal;
    HomeSearch homebar_search;

    @Override
    protected void onResume() {
        super.onResume();

        setDisplayedUser(getActiveUser());

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        presenter.registerView(this);

        fragmentManager = getSupportFragmentManager();

        /*Tab manager*/

        tabs = findViewById(R.id.home_tab_layout);

        initializeFragments();

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                switch (tab.getPosition()) {
                    case 0:
                        activeFragment = feedFragment;
                        break;
                    case 1:
                        activeFragment = storyFragment;
                        break;
                    case 2:
                        activeFragment = followingFragment;
                        break;
                    case 3:
                        activeFragment = followersFragment;
                        break;
                }

                fragmentManager.beginTransaction().replace(R.id.home_fragment_holder, activeFragment).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        /*Home Bar*/

        initializeSearchHomeBar();


        refreshButton = findViewById(R.id.home_refresh_button);
        refreshButton.setOnClickListener(v -> {
            RefreshTask refreshTask = new RefreshTask();
            refreshTask.execute();
        });

    }

    void initializeFragments() {
        feedFragment = new Feed(this);
        storyFragment = new Story(this);
        followingFragment = new Following(this);
        followersFragment = new Followers(this);

        fragmentManager.beginTransaction().add(R.id.home_fragment_holder, followingFragment);
        fragmentManager.beginTransaction().add(R.id.home_fragment_holder, followersFragment);
        fragmentManager.beginTransaction().add(R.id.home_fragment_holder, storyFragment);
        fragmentManager.beginTransaction().add(R.id.home_fragment_holder, feedFragment).commit();

        activeFragment = feedFragment;
    }

    void initializeSearchHomeBar() {
        homebar_normal = new HomeBar(this, this);

        homebar_search = new HomeSearch(this, this);

        fragmentManager.beginTransaction().add(R.id.home_bar, homebar_search);
        fragmentManager.beginTransaction().add(R.id.home_bar, homebar_normal).commit();

        homebar_active = homebar_normal;
    }

    public void setHomeBar(char option){
        switch (option) {
            case 'n':
                homebar_active = homebar_normal;
                break;
            case 's':
                homebar_active = homebar_search;
                break;
        }

        fragmentManager.beginTransaction().replace(R.id.home_bar, homebar_active).commit();
    }

    void refreshFragments() {
        feedFragment = new Feed(this);
        storyFragment = new Story(this);
        followingFragment = new Following(this);
        followersFragment = new Followers(this);

        fragmentManager.beginTransaction().add(R.id.home_fragment_holder, followingFragment);
        fragmentManager.beginTransaction().add(R.id.home_fragment_holder, followersFragment);
        fragmentManager.beginTransaction().add(R.id.home_fragment_holder, storyFragment);
        fragmentManager.beginTransaction().add(R.id.home_fragment_holder, feedFragment);

        switch (tabs.getSelectedTabPosition()) {
            case 0:
                activeFragment = feedFragment;
                break;
            case 1:
                activeFragment = storyFragment;
                break;
            case 2:
                activeFragment = followingFragment;
                break;
            case 3:
                activeFragment = followersFragment;
                break;
        }

        fragmentManager.beginTransaction().replace(R.id.home_fragment_holder, activeFragment).commit();
    }

    @Override
    public boolean doLogin(String handle, String password) {
        return false;
    }

    @Override
    public void onLoginSuccessful(String message) {

    }

    @Override
    public boolean doRegister(String handle, String password, String firstName, String lastName, String tag, Bitmap image) {
        return false;
    }

    @Override
    public void onRegisterSuccessful(String message) {

    }

    @Override
    public boolean onPost(String userHandle, String body, String imageURL, String videoURL, Bitmap image) {

        try {
            presenter.onPost(userHandle, body, imageURL, videoURL, image);
        } catch (ServerErrorException e) {
            errorMessage = e.getMessage();
            return false;
        } catch (EmptyFieldException e) {
            errorMessage = e.getMessage();
            return false;
        }

        return true;
    }

    @Override
    public void onPostSuccessful(String message) {
        refreshFragments();
    }

    @Override
    public boolean doSignOut() {
        return presenter.onSignOut();
    }

    @Override
    public void onSignOutSuccessful(String message) {
        Intent i = new Intent(this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }

    @Override
    public void onFollowSuccessful(String message) {

    }

    @Override
    public void onUnfollowSuccessful(String message) {

    }

    @Override
    public void onTaskFail(String message) {
        Toast.makeText(HomeActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAttemptingTask(String message) {

    }

    @Override
    public void onRefreshNeeded() {
        refreshButton.setBackgroundColor(getColor(R.color.refreshNeeded));
        Toast.makeText(HomeActivity.this, "There's new data!" +
                " Refresh by clicking the button.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public User getActiveUser() {
        return presenter.getActiveUser();
    }

    @Override
    public User getDisplayedUser() {
        return presenter.getDisplayedUser();
    }

    @Override
    public void setDisplayedUser(User user) {
        presenter.setDisplayedUser(user);
    }

    @Override
    public Bitmap getImage(String tag) {
        return presenter.getImage(tag);
    }

    @Override
    public boolean doRefresh() {

        try {
            presenter.onRefresh();
        } catch (ServerErrorException e) {
            errorMessage = e.getMessage();
            return false;
        }

        return true;

    }

    @Override
    public void onRefreshSuccessful() {
        refreshFragments();
    }

    @Override
    public boolean isFollowing(String handle) {
        return presenter.isFollowing(handle);
    }

    @Override
    public boolean onSearch(String handle) {
        try {
            presenter.onSearch(handle);
        } catch (UserNotFoundException e) {
            errorMessage = e.getMessage();
            return false;
        }

        return true;
    }

    @Override
    public void onSearchSuccessful() {
        setDisplayedUser(getSearchedUser());
        Intent i = new Intent(this, UserActivity.class);
        startActivity(i);
    }

    @Override
    public User getSearchedUser() {
        return presenter.getDisplayedUser();
    }

    private class RefreshTask extends AsyncTask<String, String, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {

            return doRefresh();
        }

        @Override
        protected void onPostExecute(Boolean wasSuccessful) {
            if (!wasSuccessful) {
                onTaskFail(getErrorMessage());
            }
        }
    }

    public void goToUserProfile() {
        Intent i = new Intent(this, UserActivity.class);
        setDisplayedUser(getActiveUser());
        startActivity(i);
    }

    public Bitmap retrieveImage(String imageURL) {

        try {
            Bitmap bitmap =  MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(imageURL));
            return bitmap;
        } catch (IOException e) {
            return null;
        }
    }
}
