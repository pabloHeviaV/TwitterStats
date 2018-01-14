package com.example.yo.twitterstats.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.yo.twitterstats.R;
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

    private static final String TAG = "TwitterLogin";

    private TwitterLoginButton loginButton;
    TwitterSession session;
    TwitterAuthToken authToken;

    /**
     * Crea el botón de login y añade una función callback que si tiene
     * éxito lanza la activity central y si falla muestra un mensaje.
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
                startActivity(intent);
                finish();

            }

            @Override
            public void failure(TwitterException exception) {
                Log.w(TAG, "twitterLogin:failure", exception);
                Log.e("twitterLogin:failure", exception.getMessage());

                if(exception.getMessage().equals("Failed to get request token")) {
                    Context context = getApplicationContext();
                    CharSequence text = "Es necesario que tenga instalada la aplicación de Twitter";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                   /* AlertDialog.Builder builder =
                            new AlertDialog.Builder(LoginActivity.this);
                    builder.setTitle("No se ha podido iniciar sesión");
                    builder.setMessage("Es necesario que tenga instalada la aplicación de Twitter");

                    builder.setIcon(android.R.drawable.ic_dialog_alert);
                    builder.setNegativeButton("Salir", null);
                    builder.show();*/
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
