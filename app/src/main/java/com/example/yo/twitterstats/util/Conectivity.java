package com.example.yo.twitterstats.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;

public class Conectivity {
    private Context contexto;

    public Conectivity(Context c){
        contexto=c;
    }

    public boolean hayConexion(){
        ConnectivityManager cm=(ConnectivityManager) contexto.getSystemService(contexto.CONNECTIVITY_SERVICE);
        boolean connected = false;
        Network[] networks=cm.getAllNetworks();
        NetworkInfo networkInfo;
        for(Network n:networks){
            networkInfo=cm.getNetworkInfo(n);
            if(networkInfo.getState().equals(NetworkInfo.State.CONNECTED))
                connected=true;
        }
        return connected;
    }
}
