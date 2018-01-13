package com.example.yo.twitterstats;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import twitter4j.User;

public class UserProfileActivity extends AppCompatActivity {

    private TwitterSession session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        session = TwitterCore.getInstance().getSessionManager().getActiveSession();
        //loadUserData();

        new AsyncCaller().execute();

    }

    private void loadUserData(User currentUser) {

        ((TextView) findViewById(R.id.userData)).setText(
                "Name: "            + currentUser.getName()
                        +"\n"+
                        "\nScreen name: "  + currentUser.getScreenName()
                        +"\n"+
                        "\nLocation: "     + currentUser.getLocation()
                        +"\n"+
                        "\nFollowing: "    + currentUser.getFriendsCount()
                        +"\n"+
                        "\nFollowers: "    + currentUser.getFollowersCount()
        );
        Picasso.with(getBaseContext()).load(currentUser.getBiggerProfileImageURL()).
                resize(250,250)
                .into((ImageView)findViewById(R.id.profilePic));
    }

    /*
      Limpia la sesión de Twitter que está abierta y lanza la activity del login.
     */
    public void cerrarSesion(View v){
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


    private class AsyncCaller extends AsyncTask<Void, Void, User>
    {
        ProgressDialog pdLoading = new ProgressDialog(UserProfileActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pdLoading.setMessage("\tCargando...");
            pdLoading.show();
        }

        @Override
        protected User doInBackground(Void... params) {

            //this method will be running on background thread so don't update UI frome here
            //do your long running http tasks here,you dont want to pass argument and u can access the parent class' variable url over here
            // GetData.getInstance().fetchFollowers();
            User currentUser = GetData.getInstance().getCurrentUserData();

            return currentUser;
        }


        @Override
        protected void onPostExecute(User user) {
            pdLoading.dismiss();
            if(user != null)
                loadUserData(user);
            else {
                AlertDialog.Builder builder = new AlertDialog.Builder(UserProfileActivity.this);
                builder.setTitle("Error al obtener los datos");
                builder.setMessage("Compruebe su conexión a internet");
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                builder.setPositiveButton("Reintentar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        new AsyncCaller().execute();
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                builder.show();
            }
        }
    }

}
