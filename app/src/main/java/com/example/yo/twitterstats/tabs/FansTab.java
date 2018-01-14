package com.example.yo.twitterstats.tabs;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.yo.twitterstats.util.AdaptadorListas;
import com.example.yo.twitterstats.util.GetData;
import com.example.yo.twitterstats.R;

/**
 * Fragment que almacena tus fans, es decir, gente que te sigue
 * y tú no les sigues.
 */
public class FansTab extends Fragment{

    private AdaptadorListas adaptadorListas;
    private ListView lista;
    private GetData gd;
    private View rootView;
    private TextView tv;

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
        rootView = inflater.inflate(R.layout.tab_fans, container, false);
        tv= (TextView) rootView.findViewById(R.id.textView2);
        gd = GetData.getInstance();

        adaptadorListas = new AdaptadorListas(this.getActivity(),gd.getFansList());
        Log.e("Fans", "Adapter fans creado");
        lista = (ListView) rootView.findViewById(R.id.fans);
        lista.setAdapter(adaptadorListas);
        Spanned datos = Html.fromHtml(getString(R.string.fansTitle)+" <b>("+gd.getFansList().size()+")</b>");
        tv.setText(datos);
        return rootView;
    }

    /**
     * Recarga la lista.
     */
    public void updateList(){
        if(adaptadorListas!=null){
            adaptadorListas.clear();
            adaptadorListas.addAll(gd.getFansList());
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
            Log.e("CLEAR FANS", "clear fragments");
        }
    }

}
