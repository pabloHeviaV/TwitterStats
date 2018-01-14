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

import com.example.yo.twitterstats.util.AdaptadorListas;
import com.example.yo.twitterstats.util.GetData;
import com.example.yo.twitterstats.R;

/**
 * Fragment que almacena tus No seguidores, es decir, gente que tú sigues
 * pero no te siguen a ti.
 */
public class UnfollowersTab extends Fragment{

    private AdaptadorListas adaptadorListas;
    private ListView lista;
    private GetData gd;
    private View rootView;

    /**
     * Crea el adapter y la lista y los enlaza.
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_unfollowers, container, false);

        gd= GetData.getInstance(getActivity().getApplicationContext());

        adaptadorListas = new AdaptadorListas(this.getActivity(),gd.getNotFollowingYouList());
        Log.e("Unfollowers", "Adapter unfollowers creado");
        lista = (ListView) rootView.findViewById(R.id.notFollowingYouList);
        lista.setAdapter(adaptadorListas);
        return rootView;
    }

    /**
     * Recarga la lista.
     */
   public void updateList(){
        if(adaptadorListas!=null){
            adaptadorListas.clear();
            adaptadorListas.addAll(gd.getNotFollowingYouList());
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
