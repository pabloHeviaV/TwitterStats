package com.example.yo.twitterstats.tabs;



/**
 * Created by yo on 09/01/2018.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.yo.twitterstats.AdaptadorListas;
import com.example.yo.twitterstats.GetData;
import com.example.yo.twitterstats.R;


public class UnfollowersTab extends Fragment{

    private AdaptadorListas adaptadorListasNotFollowingYou;
    private ListView lista;
    private GetData gd;
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_unfollowers, container, false);

        gd= GetData.getInstance();

        adaptadorListasNotFollowingYou = new AdaptadorListas(this.getActivity(),gd.getNotFollowingYouList());
        Log.e("Unfollowers", "Adapter unfollowers creado");
        lista = (ListView) rootView.findViewById(R.id.notFollowingYouList);
        lista.setAdapter(adaptadorListasNotFollowingYou);
        return rootView;
    }

   public void updateList(){
        adaptadorListasNotFollowingYou.clear();
        adaptadorListasNotFollowingYou.addAll(gd.getNotFollowingYouList());
        adaptadorListasNotFollowingYou.notifyDataSetChanged();
   }
}
