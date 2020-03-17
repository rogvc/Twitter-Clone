package rogvc.cs340.twitter.view.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import rogvc.cs340.twitter.R;
import rogvc.cs340.twitter.model.domain.User;
import rogvc.cs340.twitter.view.IView;
import rogvc.cs340.twitter.view.activity.UserActivity;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {
    private User[] data;
    IView myView;

    public UserListAdapter(User[] data, IView myView) {
        this.data = data;
        this.myView = myView;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View listItem = inflater.inflate(R.layout.fragment_user_list, parent, false);

        ViewHolder viewHolder = new ViewHolder(listItem);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final User user = data[position];

        holder.name_PH.setText(user.getName());

        holder.handle_PH.setText(user.getHandle());

        holder.profilePic_PH.setImageBitmap(myView.getImage(user.getImageUrl()));

        holder.item_PH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(holder.itemView.getContext(), UserActivity.class);
                myView.setDisplayedUser(user);
                holder.itemView.getContext().startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView profilePic_PH;
        public TextView handle_PH;
        public TextView name_PH;
        public RelativeLayout item_PH;

        public ViewHolder(View itemView) {
            super(itemView);
            this.name_PH = itemView.findViewById(R.id.user_list_name_placeholder);
            this.handle_PH = itemView.findViewById(R.id.user_list_handle_placeholder);
            this.profilePic_PH = itemView.findViewById(R.id.user_list_profile_pic_holder);
            this.item_PH = itemView.findViewById(R.id.user_list);
        }
    }
}
