package com.example.yo.twitterstats;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "TwitterLogin";

    private TwitterLoginButton loginButton;
    TwitterSession session;
    TwitterAuthToken authToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Inicializa el acceso a la API
        Twitter.initialize(this);

        setContentView(R.layout.activity_main);



        loginButton = findViewById(R.id.login_button);
        //Añade un callback al botón de login
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                Log.d(TAG, "twitterLogin:success" + result);
                session = TwitterCore.getInstance().getSessionManager().getActiveSession();
                authToken = session.getAuthToken();

                String userName = result.data.getUserName();
                long userID = result.data.getId();
            }

            @Override
            public void failure(TwitterException exception) {
                Log.w(TAG, "twitterLogin:failure", exception);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result to the Twitter login button.
       loginButton.onActivityResult(requestCode, resultCode, data);
    }
}
