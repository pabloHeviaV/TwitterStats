package com.example.yo.twitterstats;



/**
 * Created by yo on 09/01/2018.
 */

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.twitter.sdk.android.core.TwitterCore;

import java.util.ArrayList;
import java.util.List;


public class FansTab extends Fragment{

    private AdaptadorListas al;
    private ListView lista;
    private GetData gd;
    private View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.tab_fans, container, false);
        gd = GetData.getInstance();

        Log.e("HOlo", "He pasado por aqui");
        List<TwitterUser> fans = new ArrayList<TwitterUser>();
        for(TwitterUser tu: gd.getFollowers()){
            if(isFan(tu)){
                fans.add(tu);
            }
        }

        al= new AdaptadorListas(this.getActivity(),fans);
        lista = (ListView) rootView.findViewById(R.id.fans);
        lista.setAdapter(al);
        return rootView;
    }

    private boolean isFan(TwitterUser tu) {
        for (TwitterUser t: gd.getFollowing()){
            if(t.equals(tu)) return false;
        }
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();

    }
    }
