package rogvc.cs340.twitter.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import rogvc.cs340.twitter.R;
import rogvc.cs340.twitter.model.domain.Hyperlink;
import rogvc.cs340.twitter.model.domain.Mention;
import rogvc.cs340.twitter.model.domain.Post;
import rogvc.cs340.twitter.model.domain.User;
import rogvc.cs340.twitter.view.IView;
import rogvc.cs340.twitter.view.activity.UserActivity;

public class PostListAdapter extends RecyclerView.Adapter {

    List<Post> posts;
    IView myView;

    public static class PostSimpleViewHolder extends RecyclerView.ViewHolder {
        ImageView profilePic_PH;
        TextView handle_PH;
        TextView name_PH;
        TextView body_PH;
        TextView timestamp_PH;


        public PostSimpleViewHolder(View itemView) {
            super(itemView);
            this.name_PH = itemView.findViewById(R.id.post_name_holder);
            this.handle_PH = itemView.findViewById(R.id.post_handle_holder);
            this.profilePic_PH = itemView.findViewById(R.id.post_profile_holder);
            this.body_PH = itemView.findViewById(R.id.post_text_holder);
            this.timestamp_PH = itemView.findViewById(R.id.post_timestamp_holder);
        }
    }

    public static class PostImageViewHolder extends RecyclerView.ViewHolder {
        ImageView profilePic_PH;
        TextView handle_PH;
        TextView name_PH;
        TextView body_PH;
        TextView timestamp_PH;
        ImageView image_PH;


        public PostImageViewHolder(View itemView) {
            super(itemView);
            this.name_PH = itemView.findViewById(R.id.post_name_holder);
            this.handle_PH = itemView.findViewById(R.id.post_handle_holder);
            this.profilePic_PH = itemView.findViewById(R.id.post_profile_holder);
            this.body_PH = itemView.findViewById(R.id.post_text_holder);
            this.timestamp_PH = itemView.findViewById(R.id.post_timestamp_holder);
            this.image_PH = itemView.findViewById(R.id.post_image_holder);
        }
    }

    public static class PostVideoViewHolder extends RecyclerView.ViewHolder {
        ImageView profilePic_PH;
        TextView handle_PH;
        TextView name_PH;
        TextView body_PH;
        TextView timestamp_PH;
        VideoView video_PH;


        public PostVideoViewHolder(View itemView) {
            super(itemView);
            this.name_PH = itemView.findViewById(R.id.post_name_holder);
            this.handle_PH = itemView.findViewById(R.id.post_handle_holder);
            this.profilePic_PH = itemView.findViewById(R.id.post_profile_holder);
            this.body_PH = itemView.findViewById(R.id.post_text_holder);
            this.timestamp_PH = itemView.findViewById(R.id.post_timestamp_holder);
            this.video_PH = itemView.findViewById(R.id.post_video_holder);
        }
    }

    public PostListAdapter(List<Post> posts, IView myView) {
        this.posts = posts;
        this.myView = myView;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        switch (viewType) {
            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.fragment_post, parent, false);
                return new PostSimpleViewHolder(view);

            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.fragment_post_image, parent, false);
                return new PostImageViewHolder(view);

            case 2:
                view = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.fragment_post_video, parent, false);
                return new PostVideoViewHolder(view);
        }

        return null;
    }

    @Override
    public int getItemViewType(int position) {

        switch (posts.get(position).getType()) {
            case 0:
                return 0;
            case 1:
                return 1;
            case 2:
                return 2;
            default:
                return -1;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        Post post = posts.get(position);

        if (post == null) {
            return;
        }

        switch (post.getType()) {
            case 0:
                bindPostSimple(post, holder);
                break;
            case 1:
                bindPostImage(post, holder);
                break;
            case 2:
                bindPostVideo(post, holder);
                break;
        }

    }

    void openUserActivity(Context context, User user) {
        Intent i = new Intent(context, UserActivity.class);
        myView.setDisplayedUser(user);
        context.startActivity(i);
    }

    SpannableString setUpMentionsAndHyperlinks(Post post, Context context) {
        SpannableString spannableBody = new SpannableString(post.getBody());

        for (int i = 0; i < post.getMentions().size(); i++) {
            Mention mention = post.getMentions().get(i);
            ClickableSpan clickable = new ClickableSpan() {
                @Override
                public void onClick(@NonNull View widget) {
                    openUserActivity(context, mention.getUserMentioned());
                }
            };

            spannableBody.setSpan(clickable, mention.getInitialPositionInText(),
                    mention.getLastPositionInText(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        for (int i = 0; i < post.getHyperlinks().size(); i++) {
            Hyperlink hyperlink = post.getHyperlinks().get(i);
            ClickableSpan clickable = new ClickableSpan() {
                @Override
                public void onClick(@NonNull View widget) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(hyperlink.getUrl()));
                    context.startActivity(browserIntent);
                }
            };

            spannableBody.setSpan(clickable, hyperlink.getInitialPositionInText(),
                    hyperlink.getLastPositionInText(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        return spannableBody;
    }

    void bindPostSimple(Post post, RecyclerView.ViewHolder OGholder) {
        PostSimpleViewHolder holder = (PostSimpleViewHolder) OGholder;

        holder.name_PH.setText(post.getUser().getName());
        holder.name_PH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUserActivity(holder.itemView.getContext(), post.getUser());
            }
        });

        holder.handle_PH.setText(post.getUser().getHandle());
        holder.handle_PH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUserActivity(holder.itemView.getContext(), post.getUser());
            }
        });

        holder.profilePic_PH.setImageBitmap(myView.getImage(post.getUser().getImageUrl()));
        holder.profilePic_PH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUserActivity(holder.itemView.getContext(), post.getUser());
            }
        });

        SpannableString spannableBody = setUpMentionsAndHyperlinks(post,
                holder.itemView.getContext());

        holder.body_PH.setText(spannableBody);
        holder.body_PH.setMovementMethod(LinkMovementMethod.getInstance());

        holder.timestamp_PH.setText(post.getTimeStamp());
    }

    void bindPostImage(Post post, RecyclerView.ViewHolder OGholder) {
        PostImageViewHolder holder = (PostImageViewHolder) OGholder;

        holder.name_PH.setText(post.getUser().getName());
        holder.name_PH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUserActivity(holder.itemView.getContext(), post.getUser());
            }
        });

        holder.handle_PH.setText(post.getUser().getHandle());
        holder.handle_PH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUserActivity(holder.itemView.getContext(), post.getUser());
            }
        });

        holder.profilePic_PH.setImageBitmap(myView.getImage(post.getUser().getImageUrl()));
        holder.profilePic_PH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUserActivity(holder.itemView.getContext(), post.getUser());
            }
        });

        SpannableString spannableBody = setUpMentionsAndHyperlinks(post,
                holder.itemView.getContext());

        holder.body_PH.setText(spannableBody);
        holder.body_PH.setMovementMethod(LinkMovementMethod.getInstance());

        holder.timestamp_PH.setText(post.getTimeStamp());

        holder.image_PH.setImageBitmap(myView.getImage(post.getMediaURL()));
    }

    void bindPostVideo(Post post, RecyclerView.ViewHolder OGholder) {
        PostVideoViewHolder holder = (PostVideoViewHolder) OGholder;

        holder.name_PH.setText(post.getUser().getName());
        holder.name_PH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUserActivity(holder.itemView.getContext(), post.getUser());
            }
        });

        holder.handle_PH.setText(post.getUser().getHandle());
        holder.handle_PH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUserActivity(holder.itemView.getContext(), post.getUser());
            }
        });

        holder.profilePic_PH.setImageBitmap(myView.getImage(post.getUser().getImageUrl()));
        holder.profilePic_PH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUserActivity(holder.itemView.getContext(), post.getUser());
            }
        });

        SpannableString spannableBody = setUpMentionsAndHyperlinks(post,
                holder.itemView.getContext());

        holder.body_PH.setText(spannableBody);
        holder.body_PH.setMovementMethod(LinkMovementMethod.getInstance());

        holder.timestamp_PH.setText(post.getTimeStamp());

        MediaController mediaController = new MediaController(holder.itemView.getContext());
        mediaController.setAnchorView(holder.video_PH);
        mediaController.setMediaPlayer(holder.video_PH);
        holder.video_PH.setVideoURI(Uri.parse(post.getMediaURL()));
        holder.video_PH.setMediaController(mediaController);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

}
