package com.example.yo.twitterstats;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yo on 10/01/2018.
 */

public class MutualsTab extends Fragment{

    private AdaptadorListas al;
    private ListView lista;
    private GetData gd;
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_mutuals, container, false);
        gd= GetData.getInstance();
        List<TwitterUser> mutuals = new ArrayList<TwitterUser>();

        for(TwitterUser tu: gd.getFollowers()){
            if(isMutual(tu)){
                mutuals.add(tu);
            }
        }

        al= new AdaptadorListas(this.getActivity(),mutuals);
        lista = (ListView) rootView.findViewById(R.id.mutuals);
        lista.setAdapter(al);
        return rootView;
    }

    private boolean isMutual(TwitterUser tu) {
        for(TwitterUser t: gd.getFollowing()){
            if(t.equals(tu)){
                return true;
            }
        }
        return false;
    }
}
