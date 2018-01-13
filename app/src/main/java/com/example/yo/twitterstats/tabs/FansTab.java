package com.example.yo.twitterstats.tabs;



/**
 * Created by yo on 09/01/2018.
 */

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

import java.util.List;


public class FansTab extends Fragment{

    private static AdaptadorListas adaptadorListasFans;
    private ListView lista;
    private GetData gd;
    private View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.tab_fans, container, false);
        gd = GetData.getInstance();



        List<TwitterUser> fansList = gd.getFansList();
//        fansList.removeAll(gd.getFollowing());
        Log.e("fanslist",fansList.toString());
        adaptadorListasFans = new AdaptadorListas(this.getActivity(),fansList);
        Log.e("Fans", "Adapter fans creado");
        lista = (ListView) rootView.findViewById(R.id.fans);
        lista.setAdapter(adaptadorListasFans);
        return rootView;
    }

    public void updateList(){
        adaptadorListasFans.clear();
        adaptadorListasFans.addAll(gd.getFansList());
        adaptadorListasFans.notifyDataSetChanged();
    }

    public static AdaptadorListas getAdaptadorListasFans() {
        return adaptadorListasFans;
    }
}
