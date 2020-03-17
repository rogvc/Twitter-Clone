package rogvc.cs340.twitter.view.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import androidx.fragment.app.Fragment;

import java.io.FileNotFoundException;
import java.io.InputStream;

import rogvc.cs340.twitter.R;
import rogvc.cs340.twitter.view.IView;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class Register extends Fragment {

    String username = "";
    String password = "";
    String firstName = "";
    String lastName = "";
    String imageURL = "";
    Bitmap profilePic = null;

    ImageButton profilePic_placeholder;
    Uri imageURI;

    final int GALLERY_REQUEST = 0;

    IView myView;

    public Register(IView myView) {
        this.myView = myView;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_register, container, false);

        final EditText usernameField = view.findViewById(R.id.register_username_field);
        usernameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                username = s.toString();
            }
        });

        final EditText passwordField = view.findViewById(R.id.register_password_field);
        passwordField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                password = s.toString();
            }
        });

        final EditText firstNameField = view.findViewById(R.id.register_first_name_field);
        firstNameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                firstName = s.toString();
            }
        });

        final EditText lastNameField = view.findViewById(R.id.register_last_name_field);
        lastNameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                lastName = s.toString();
            }
        });

        profilePic_placeholder =
                view.findViewById(R.id.register_profile_pic_placeholder);
        profilePic_placeholder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage();
            }
        });

        Button submitButton = view.findViewById(R.id.register_submit_button);
        submitButton.setClickable(true);
        submitButton.setOnClickListener(v -> {
            register(username, password, firstName, lastName, imageURL, profilePic);
        });

        return view;
    }

    void register(String handle, String password, String firstName,
                  String lastName, String imageURL, Bitmap image) {
        RegisterTask registerTask = new RegisterTask();
        registerTask.execute(handle, password, firstName, lastName, imageURL);
    }

    private class RegisterTask extends AsyncTask<String, String, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {

            return myView.doRegister(strings[0], strings[1], strings[2], strings[3],
                    strings[4], profilePic);
        }

        @Override
        protected void onPostExecute(Boolean wasSuccessful) {
            if (!wasSuccessful) {
                myView.onTaskFail(myView.getErrorMessage());
            }
        }
    }

    void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");

        String[] mimeTypes = {"image/jpeg", "image/png"};

        intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);

        startActivityForResult(intent, GALLERY_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch(requestCode) {
            case GALLERY_REQUEST:
                if(resultCode == RESULT_OK){
                    try {
                        imageURI = imageReturnedIntent.getData();
                        imageURL = imageURI.toString();
                        final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageURI);
                        profilePic = BitmapFactory.decodeStream(imageStream);
                        profilePic_placeholder.setImageBitmap(profilePic);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }
        }
    }

}
