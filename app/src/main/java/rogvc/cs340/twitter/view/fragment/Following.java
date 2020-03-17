package rogvc.cs340.twitter.view.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import rogvc.cs340.twitter.R;
import rogvc.cs340.twitter.view.IView;
import rogvc.cs340.twitter.view.adapter.UserListAdapter;

public class Following extends Fragment {

    IView myView;

    public Following(IView myView) {
        this.myView = myView;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_following, container, false);


        UserListAdapter adapter = new UserListAdapter(myView.getDisplayedUser().getFollowingAsArray(), myView);

        RecyclerView followingListRecycler = view.findViewById(R.id.following_recycler);

        followingListRecycler.setHasFixedSize(true);
        followingListRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        followingListRecycler.setAdapter(adapter);

        return view;
    }

}
