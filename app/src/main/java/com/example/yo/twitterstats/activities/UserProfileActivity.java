package com.example.yo.twitterstats.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yo.twitterstats.R;
import com.example.yo.twitterstats.util.GetData;
import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;

import twitter4j.User;

public class UserProfileActivity extends AppCompatActivity {

    private TwitterSession session;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        session = TwitterCore.getInstance().getSessionManager().getActiveSession();
        //loadUserData();

        new AsyncCaller().execute();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void loadUserData(User currentUser) {

        ((TextView) findViewById(R.id.userData)).setText(
                new StringBuilder()
                        .append(getString(R.string.nameProfile))
                        .append(currentUser.getName()).append("\n\n")

                        .append(getString(R.string.screenNameProfile))
                        .append(currentUser.getScreenName()).append("\n\n")

                        .append(getString(R.string.locationProfile))
                        .append(currentUser.getLocation()).append("\n\n")

                        .append(getString(R.string.followingProfile))
                        .append(currentUser.getFriendsCount()).append("\n\n")

                        .append(getString(R.string.followersProfile))
                        .append(currentUser.getFollowersCount()).toString()
        );
        Picasso.with(getBaseContext()).load(currentUser.getBiggerProfileImageURL()).
                resize(250, 250)
                .into((ImageView) findViewById(R.id.profilePic));
    }

    /*
      Limpia la sesión de Twitter que está abierta y lanza la activity del login.
     */

    /**
     * Manda un mensaje a la ActivityCentral para que finalice,
     * cierra la sesión de Twitter y
     * lanza {@link LoginActivity}.
     *
     * @param v
     */
    public void cerrarSesion(View v) {
        //Avisa a la actividad central para que termine
        Intent intent = new Intent("finish_activity");
        sendBroadcast(intent);

        //Cierra la sesión de twitter
        TwitterCore.getInstance().getSessionManager().clearActiveSession();

        //Lanza la actividad de login y termina esta
        intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Llamada asíncrona que obtiene los datos del user.
     */
    private class AsyncCaller extends AsyncTask<Void, Void, User> {
        ProgressDialog pdLoading = new ProgressDialog(UserProfileActivity.this);

        /**
         * Muestra un {@link ProgressDialog} mientras se ejecuta la llamada.
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pdLoading.setMessage(getString(R.string.loadingAsyncProfile));
            pdLoading.show();
        }

        /**
         * Llama a la API de Twitter para obtener los datos del User.
         * @param params
         * @return
         */
        @Override
        protected User doInBackground(Void... params) {
            User currentUser = GetData.getInstance().getCurrentUserData();

            return currentUser;
        }

        /**
         * Carga los datos del User si no hay errores,
         * muestra un cuadro de diálogo si los hay.
         * @param user el User actual
         */
        @Override
        protected void onPostExecute(User user) {
            pdLoading.dismiss();
            if (user != null)
                loadUserData(user);
            else {
                AlertDialog.Builder builder = new AlertDialog.Builder(UserProfileActivity.this);
                builder.setTitle(R.string.errorObtainDataMessageTitle);
                builder.setMessage(R.string.errorMsgCheckInternet);
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                builder.setPositiveButton(R.string.retry, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        new AsyncCaller().execute();
                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                builder.show();
            }
        }
    }

}
