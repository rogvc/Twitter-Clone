package rogvc.cs340.twitter.view.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.fragment.app.DialogFragment;

import rogvc.cs340.twitter.R;
import rogvc.cs340.twitter.view.IView;
import rogvc.cs340.twitter.view.activity.HomeActivity;

import static android.app.Activity.RESULT_OK;


public class NewPost extends DialogFragment {

    String body = "";
    String imageURL = "";
    String videoURL = "";
    Bitmap image = null;

    Uri imageURI;
    Uri videoURI;

    ImageButton addImageButton;
    ImageButton addVideoButton;

    IView myView;
    HomeActivity home;

    public NewPost(IView myView, HomeActivity home) {
        this.home = home;
        this.myView = myView;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_new_post, container, false);


        Button submitButton = view.findViewById(R.id.new_post_submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newPost();
            }
        });

        EditText postTxt = view.findViewById(R.id.new_post_text_edit);
        postTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 140|| s.length() < 1) {
                    submitButton.setClickable(false);
                    submitButton.setBackgroundColor(getResources().getColor(R.color.colorBackground));
                    postTxt.setTextColor(getResources().getColor(R.color.refreshNeeded, null));
                } else {
                    submitButton.setClickable(true);
                    submitButton.setBackgroundColor(getResources().getColor(R.color.colorSecondaryLight));
                    postTxt.setTextColor(getResources().getColor(R.color.lightTextOnBackground, null));
                }
                body = s.toString();
            }
        });

        addImageButton = view.findViewById(R.id.new_post_add_image_button);


        addVideoButton = view.findViewById(R.id.new_post_add_video_button);

        addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage();
            }
        });

        addVideoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickVideo();
            }
        });

        return view;
    }

    void newPost() {
        PostTask postTask = new PostTask();
        postTask.execute(myView.getActiveUser().getHandle(), body, imageURL, videoURL);
    }

    private class PostTask extends AsyncTask<String, String, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {

            return myView.onPost(strings[0], strings[1], strings[2],
                    strings[3], home.retrieveImage(strings[2]));
        }

        @Override
        protected void onPostExecute(Boolean wasSuccessful) {
            if (!wasSuccessful) {
                myView.onTaskFail(myView.getErrorMessage());
            }
            dismiss();
        }
    }

    void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");

        String[] mimeTypes = {"image/jpeg", "image/png"};

        intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);

        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch(requestCode) {
            case 1:
                if(resultCode == RESULT_OK){
                    imageURI = imageReturnedIntent.getData();
                    imageURL = imageURI.toString();

                    addVideoButton.setBackgroundColor(getResources().getColor(R.color.lightTextOnBackground, null));
                    addVideoButton.setClickable(false);

                }
                break;
            case 2:
                if(resultCode == RESULT_OK){

                    videoURI = imageReturnedIntent.getData();
                    videoURL = videoURI.toString();

                    addImageButton.setBackgroundColor(getResources().getColor(R.color.lightTextOnBackground, null));
                    addImageButton.setClickable(false);

                }
                break;
        }
    }

    void pickVideo() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("video/*");

        String[] mimeTypes = {"video/mp4"};

        intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);

        startActivityForResult(intent, 2);
    }
}
