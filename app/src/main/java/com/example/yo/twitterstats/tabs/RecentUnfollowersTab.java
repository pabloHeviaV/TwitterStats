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

import com.example.yo.twitterstats.R;
import com.example.yo.twitterstats.util.AdaptadorListas;
import com.example.yo.twitterstats.util.GetData;

/**
 * Fragment que almacena los usuarios que te han dado unfollow recientemente.
 */
public class RecentUnfollowersTab extends Fragment{

    private AdaptadorListas adaptadorListas;
    private ListView lista;
    private GetData gd;
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_recentunfollowers, container, false);

        gd= GetData.getInstance();

        adaptadorListas = new AdaptadorListas(this.getActivity(),gd.getUnfollowers());
        Log.e("Recent unfollowers", "Adapter recent unfollowers creado");
        lista = (ListView) rootView.findViewById(R.id.recentUnfollowers);
        lista.setAdapter(adaptadorListas);
        return rootView;

    }

    /**
     * Recarga la lista.
     */
    public void updateList(){
        if(adaptadorListas!=null){
            adaptadorListas.clear();
            adaptadorListas.addAll(gd.getUnfollowers());
            adaptadorListas.notifyDataSetChanged();
        }

    }

    /**
     * Elimina todos los objetos de la lista.
     */
    public void clearList(){
        if(adaptadorListas!=null){
            adaptadorListas.clear();
            adaptadorListas.notifyDataSetChanged();
        }
    }


}
