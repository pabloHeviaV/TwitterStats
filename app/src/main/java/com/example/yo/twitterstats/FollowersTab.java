package com.example.yo.twitterstats;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yo on 10/01/2018.
 */

public class FollowersTab extends Fragment {

    private AdaptadorListas al;
    private ListView lista;
    private GetData gd;
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.tab_followers, container, false);
        gd = GetData.getInstance();
        Log.e("HOlo", "He pasado por aqui followers");
        List<TwitterUser> fans = new ArrayList<TwitterUser>();

        al= new AdaptadorListas(this.getActivity(),gd.getFollowers());
        lista = (ListView) rootView.findViewById(R.id.followers);
        lista.setAdapter(al);
        return rootView;
    }
}
