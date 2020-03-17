package rogvc.cs340.twitter.view.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;

import rogvc.cs340.twitter.R;
import rogvc.cs340.twitter.view.IView;
import rogvc.cs340.twitter.view.activity.HomeActivity;

public class HomeSearch extends Fragment {

    IView myView;
    HomeActivity home;

    public HomeSearch(IView myView, HomeActivity home) {
        this.home = home;
        this.myView = myView;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_home_search, container, false);

        SearchView searchBar = view.findViewById(R.id.homebar_search_bar);

        searchBar.setIconified(false);

        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchUser(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchBar.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                home.setHomeBar('n');
                return true;
            }
        });

        return view;

    }

    void searchUser(String handle) {
        SearchTask searchTask = new SearchTask();
        searchTask.execute(handle);
    }

    private class SearchTask extends AsyncTask<String, String, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {

            return myView.onSearch(strings[0]);
        }

        @Override
        protected void onPostExecute(Boolean wasSuccessful) {
            if (!wasSuccessful) {
                myView.onTaskFail(myView.getErrorMessage());
            }
        }
    }

}
