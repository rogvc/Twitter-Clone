package rogvc.cs340.twitter.view.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import rogvc.cs340.twitter.R;
import rogvc.cs340.twitter.view.IView;

public class Login extends Fragment {

    String handle = "";
    String password = "";

    IView myView;

    public Login(IView myView) {
        this.myView = myView;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_login, container, false);

        final EditText usernameField = view.findViewById(R.id.login_username_field);
        usernameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                handle = s.toString();
            }
        });

        final EditText passwordField = view.findViewById(R.id.login_password_field);
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

        Button submitButton = view.findViewById(R.id.login_submit_button);
        submitButton.setClickable(true);
        submitButton.setOnClickListener(v -> {
            login(handle, password);
        });

        return view;
    }

    void login(String handle, String password) {
        LoginTask loginTask = new LoginTask();
        loginTask.execute(handle, password);
    }

    private class LoginTask extends AsyncTask<String, String, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {

            return myView.doLogin(strings[0], strings[1]);
        }

        @Override
        protected void onPostExecute(Boolean wasSuccessful) {
            if (!wasSuccessful) {
                myView.onTaskFail(myView.getErrorMessage());
            }
        }
    }

}
