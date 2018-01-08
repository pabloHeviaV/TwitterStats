package com.example.yo.twitterstats;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.User;

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

                loadUserData(userID);
            }

            @Override
            public void failure(TwitterException exception) {
                Log.w(TAG, "twitterLogin:failure", exception);

            }
        });
    }

    private void loadUserData(long userID) {
        new MyTwitterApiClient(session).getCustomService().show(userID)
                .enqueue(new Callback<User>() {
                    @Override
                    public void success(Result<User> result) {
                        ((TextView) findViewById(R.id.userData)).setText(
                                        "Name: "          + result.data.name
                                        +"\nScreen name: "+ result.data.screenName
                                        +"\nLocation: "   + result.data.location
                                        +"\nFollowing: "    + result.data.friendsCount
                                        +"\nFollowers: "    + result.data.followersCount
                        );
                        Picasso.with(getBaseContext()).load(result.data.profileImageUrl).
                                resize(250,250)
                                .into((ImageView)findViewById(R.id.profilePic));
                    }

                    @Override
                    public void failure(TwitterException exception) {
                        Log.e("Failed", exception.toString());
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
