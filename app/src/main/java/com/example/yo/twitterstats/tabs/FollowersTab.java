package com.example.yo.twitterstats.tabs;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.yo.twitterstats.AdaptadorListas;
import com.example.yo.twitterstats.GetData;
import com.example.yo.twitterstats.R;
import com.example.yo.twitterstats.TwitterUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yo on 10/01/2018.
 */

public class FollowersTab extends Fragment {

    private AdaptadorListas adaptadorListasFollowers;
    private ListView lista;
    private GetData gd;
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.tab_followers, container, false);
        gd = GetData.getInstance();

//        List<TwitterUser> fans = new ArrayList<TwitterUser>();

        adaptadorListasFollowers = new AdaptadorListas(this.getActivity(),gd.getFollowers());
        Log.e("Followers", "Adapter followers creado");
        lista = (ListView) rootView.findViewById(R.id.followers);
        lista.setAdapter(adaptadorListasFollowers);
        return rootView;
    }

    public void updateList(){
        adaptadorListasFollowers.clear();
        adaptadorListasFollowers.addAll(gd.getFollowers());
        adaptadorListasFollowers.notifyDataSetChanged();
    }
}
