package com.example.yo.twitterstats;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.User;

public class UserProfileActivity extends AppCompatActivity {

    private TwitterSession session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        session = TwitterCore.getInstance().getSessionManager().getActiveSession();
        loadUserData();

    }

    private void loadUserData() {
        twitter4j.User currentUser = GetData.getInstance().getCurrentUserData();
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
}
