package rogvc.cs340.twitter.view.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import rogvc.cs340.twitter.R;
import rogvc.cs340.twitter.model.domain.User;
import rogvc.cs340.twitter.presenter.IPresenter;
import rogvc.cs340.twitter.presenter.cache.Cache;
import rogvc.cs340.twitter.presenter.exception.ServerErrorException;
import rogvc.cs340.twitter.view.IView;
import rogvc.cs340.twitter.view.fragment.Feed;
import rogvc.cs340.twitter.view.fragment.Followers;
import rogvc.cs340.twitter.view.fragment.Following;
import rogvc.cs340.twitter.view.fragment.Story;

public class UserActivity extends AppCompatActivity implements IView {

    Cache cache = Cache.getInstance();

    IPresenter presenter = cache.getPresenter();

    String errorMessage;

    FragmentManager fragmentManager;

    TabLayout tabs;

    FloatingActionButton followButton;

    Fragment activeFragment;
    Feed feedFragment;
    Story storyFragment;
    Following followingFragment;
    Followers followersFragment;

    boolean isFollowing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        presenter.registerView(this);
        fragmentManager = getSupportFragmentManager();

        /*Tab manager*/

        tabs = findViewById(R.id.user_tab_layout);

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

                fragmentManager.beginTransaction().replace(R.id.user_fragment_holder, activeFragment).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        isFollowing = isFollowing(getDisplayedUser().getHandle());

        followButton = findViewById(R.id.user_follow_button);

        if (getActiveUser() == getDisplayedUser()) {
            followButton.hide();
        } else {
            tabs.getTabAt(2).setText("They Follow");
            tabs.getTabAt(3).setText("Follows them");
        }

        if (isFollowing) {
            followButton.setImageResource(R.drawable.ic_unfollow);

        } else {
            followButton.setImageResource(R.drawable.ic_follow);
        }

        followButton.setOnClickListener(v -> {
            if (isFollowing) {
                unfollow();
            } else {
                follow();
            }
        });

        /*Data*/

        TextView namePH = findViewById(R.id.user_name_placeholder);
        namePH.setText(getDisplayedUser().getName());

        TextView handlePH = findViewById(R.id.user_handle_placeholder);
        handlePH.setText(getDisplayedUser().getHandle());

        ImageView profilePic = findViewById(R.id.user_profile_pic_holder);
        profilePic.setImageBitmap(getImage(getDisplayedUser().getImageUrl()));

    }

    void initializeFragments() {
        feedFragment = new Feed(this);
        storyFragment = new Story(this);
        followingFragment = new Following(this);
        followersFragment = new Followers(this);

        fragmentManager.beginTransaction().add(R.id.user_fragment_holder, followingFragment);
        fragmentManager.beginTransaction().add(R.id.user_fragment_holder, followersFragment);
        fragmentManager.beginTransaction().add(R.id.user_fragment_holder, storyFragment);
        fragmentManager.beginTransaction().add(R.id.user_fragment_holder, feedFragment).commit();

        activeFragment = feedFragment;
    }

    void unfollow() {
        try {
            presenter.onUnfollow(getActiveUser().getHandle(),
                    getDisplayedUser().getHandle());
        } catch (ServerErrorException e) {
            onTaskFail(e.getMessage());
        }
    }

    void follow() {
        try {
            presenter.onFollow(getActiveUser().getHandle(),
                    getDisplayedUser().getHandle());
        } catch (ServerErrorException e) {
            errorMessage = e.getMessage();
        }
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
        return false;
    }

    @Override
    public void onPostSuccessful(String message) {

    }

    @Override
    public boolean doSignOut() {
        return false;
    }

    @Override
    public void onSignOutSuccessful(String message) {

    }

    @Override
    public void onFollowSuccessful(String message) {
        Toast.makeText(UserActivity.this, message, Toast.LENGTH_SHORT).show();
        followButton.setImageResource(R.drawable.ic_unfollow);
        isFollowing = true;
    }

    @Override
    public void onUnfollowSuccessful(String message) {
        Toast.makeText(UserActivity.this, message, Toast.LENGTH_SHORT).show();
        followButton.setImageResource(R.drawable.ic_follow);
        isFollowing = false;
    }

    @Override
    public void onTaskFail(String message) {
        Toast.makeText(UserActivity.this, message,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAttemptingTask(String message) {

    }

    @Override
    public void onRefreshNeeded() {

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
        return false;
    }

    @Override
    public void onRefreshSuccessful() {
        Toast.makeText(UserActivity.this, "There's new data! Go home to refresh.",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean isFollowing(String handle) {
        return presenter.isFollowing(handle);
    }

    @Override
    public boolean onSearch(String handle) {
        return false;
    }

    @Override
    public void onSearchSuccessful() {

    }

    @Override
    public User getSearchedUser() {
        return presenter.getDisplayedUser();
    }

    @Override
    public void onBackPressed()
    {
        presenter.deRegisterView(this);
        super.onBackPressed();
    }
}
