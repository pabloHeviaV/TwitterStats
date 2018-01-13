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

/**
 * Created by yo on 10/01/2018.
 */

public class MutualsTab extends Fragment{

    private AdaptadorListas adaptadorListasMutuals;
    private ListView lista;
    private GetData gd;
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_mutuals, container, false);
        gd= GetData.getInstance();

        adaptadorListasMutuals = new AdaptadorListas(this.getActivity(),gd.getMutualsList());
        Log.e("mutuals","Adapter mutual creado");
        lista = (ListView) rootView.findViewById(R.id.mutuals);
        lista.setAdapter(adaptadorListasMutuals);
        return rootView;
    }

    public void updateList(){
        adaptadorListasMutuals.clear();
        adaptadorListasMutuals.addAll(gd.getMutualsList());
        adaptadorListasMutuals.notifyDataSetChanged();
    }

}
