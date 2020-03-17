package rogvc.cs340.twitter.view.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import rogvc.cs340.twitter.R;
import rogvc.cs340.twitter.view.IView;
import rogvc.cs340.twitter.view.activity.HomeActivity;

public class HomeBar extends Fragment {


    IView myView;
    HomeActivity home;

    public HomeBar(IView myView, HomeActivity home) {
        this.myView = myView;
        this.home = home;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home_bar, container, false);

        ImageButton searchButton = view.findViewById(R.id.homebar_search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                home.setHomeBar('s');
            }
        });

        final ImageButton newPostButton = view.findViewById(R.id.homebar_newpost_button);
        newPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNewPost();
            }
        });

        ImageButton profilePicButton = view.findViewById(R.id.home_bar_profile_holder);
        profilePicButton.setImageBitmap(myView.getImage(myView.getActiveUser().getImageUrl()));

        profilePicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUserMenu(v);
            }
        });

        return view;
    }

    void showNewPost() {
        DialogFragment newPostFragment = new NewPost(myView, home);
        newPostFragment.show(getActivity().getSupportFragmentManager(), "New Post");
    }

    void showUserMenu(View view) {
        final PopupMenu menu = new PopupMenu(getActivity(), view);
        menu.getMenuInflater().inflate(R.menu.user_popup_menu, menu.getMenu());

        menu.setGravity(Gravity.BOTTOM);

        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getTitle().toString()) {
                    case "Profile" :
                        goToUserProfile();
                        menu.dismiss();
                        break;
                    case "Logout" :
                        doLogout();
                        menu.dismiss();
                        break;
                }

                return true;
            }
        });

        menu.show();
    }

    void goToUserProfile() {
        home.goToUserProfile();
    }

    void doLogout() {
        LogoutTask logoutTask = new LogoutTask();
        logoutTask.execute();
    }

    private class LogoutTask extends AsyncTask<String, String, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {

            return myView.doSignOut();
        }

        @Override
        protected void onPostExecute(Boolean wasSuccessful) {
            if (!wasSuccessful) {
                myView.onTaskFail(myView.getErrorMessage());
            }
        }
    }

}
