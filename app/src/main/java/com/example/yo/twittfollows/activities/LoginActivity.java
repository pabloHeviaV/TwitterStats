package com.example.yo.twittfollows.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.yo.twittfollows.R;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

/**
 * Clase que se encarga de iniciar sesión en Twitter.
 */
public class LoginActivity extends AppCompatActivity {

    TwitterSession session;
    TwitterAuthToken authToken;
    private TwitterLoginButton loginButton;

    /**
     * Crea el botón de login y añade una función callback que si tiene
     * éxito lanza la activity central y si falla muestra un mensaje.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = findViewById(R.id.login_button);
        //Añade un callback al botón de login
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                session = TwitterCore.getInstance().getSessionManager().getActiveSession();

                //Te lleva a la activity central
                Intent intent = new Intent(getApplicationContext(), CentralActivity.class);
                intent.putExtra("Login", true);
                startActivity(intent);
                finish();

            }

            @Override
            public void failure(TwitterException exception) {

                if (exception.getMessage().equals("Failed to get request token")) {
                    Context context = getApplicationContext();
                    CharSequence text = getString(R.string.failureLogin);
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }


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
