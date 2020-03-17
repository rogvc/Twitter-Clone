package rogvc.cs340.twitter.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.tabs.TabLayout;

import rogvc.cs340.twitter.R;
import rogvc.cs340.twitter.model.domain.User;
import rogvc.cs340.twitter.presenter.Presenter;
import rogvc.cs340.twitter.presenter.exception.EmptyFieldException;
import rogvc.cs340.twitter.presenter.exception.IncorrectCredentialsException;
import rogvc.cs340.twitter.view.IView;
import rogvc.cs340.twitter.view.fragment.Login;
import rogvc.cs340.twitter.view.fragment.Register;

public class MainActivity extends AppCompatActivity implements IView {

    Fragment activeFragment;
    Login loginFragment;
    Register registerFragment;
    TabLayout tabs;

    Presenter presenter = new Presenter();

    String error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter.registerView(this);
        final FragmentManager fragmentManager = getSupportFragmentManager();
        tabs = findViewById(R.id.main_tab_layout);

        initializeFragments(fragmentManager);

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                switch (tab.getPosition()) {
                    case 0:
                        activeFragment = loginFragment;
                        break;
                    case 1:
                        activeFragment = registerFragment;
                        break;
                }

                fragmentManager.beginTransaction().replace(R.id.main_fragment_holder, activeFragment).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    void initializeFragments(FragmentManager fragmentManager) {
        loginFragment = new Login(this);
        registerFragment = new Register(this);

        fragmentManager.beginTransaction().add(R.id.main_fragment_holder, registerFragment);
        fragmentManager.beginTransaction().add(R.id.main_fragment_holder, loginFragment).commit();

        activeFragment = loginFragment;
    }

    @Override
    public boolean doLogin(String handle, String password) {
        try{
           presenter.onLoginSubmit(handle, password);
        } catch (EmptyFieldException e) {
            error = e.getMessage();
            return false;
        } catch (IncorrectCredentialsException e) {
            error = e.getMessage();
            return false;
        }

        return true;
    }

    @Override
    public void onLoginSuccessful(String message) {
        Intent i = new Intent(this, HomeActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TOP);
        presenter.deRegisterView(this);
        startActivity(i);
        finish();
    }

    @Override
    public boolean doRegister(String handle, String password, String firstName, String lastName, String tag, Bitmap image) {
        try{
            presenter.onRegisterSubmit(handle, password, firstName, lastName, tag, image);
        } catch (EmptyFieldException e) {
            error = e.getMessage();
            return false;
        } catch (IncorrectCredentialsException e) {
            error = e.getMessage();
            return false;
        }

        return true;
    }

    @Override
    public void onRegisterSuccessful(String message) {
        Intent i = new Intent(this, HomeActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        presenter.deRegisterView(this);
        finish();
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

    }

    @Override
    public void onUnfollowSuccessful(String message) {

    }

    @Override
    public void onTaskFail(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAttemptingTask(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefreshNeeded() {

    }

    @Override
    public String getErrorMessage() {
        return error;
    }

    @Override
    public User getActiveUser() {
        return null;
    }

    @Override
    public User getDisplayedUser() {
        return null;
    }

    @Override
    public void setDisplayedUser(User user) {

    }

    @Override
    public Bitmap getImage(String tag) {
        return null;
    }

    @Override
    public boolean doRefresh() {
        return false;
    }

    @Override
    public void onRefreshSuccessful() {

    }

    @Override
    public boolean isFollowing(String handle) {
        return false;
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
        return null;
    }
}
