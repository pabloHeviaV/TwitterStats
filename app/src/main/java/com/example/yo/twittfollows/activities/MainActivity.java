package com.example.yo.twittfollows.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;

/**
 * Activity inicial que comprueba si hay una sesión abierta anterior
 * y lanza la ventana de login o la ventana central de la app según ello.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Comprueba si hay una sesión iniciada, y si la hay lanza la
     * CentralActivity.
     * Si no hay una sesión iniciada lanza LoginActivity.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Inicializa el acceso a la API
        Twitter.initialize(this);
        // Si ya hay una sesión abierta se pasa a la activity central
        TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();

        Intent intent = new Intent();

        if (session != null) {
            intent.setClass(this, CentralActivity.class);
        } else {
            intent.setClass(this, LoginActivity.class);
        }

        startActivity(intent);

    }

}
