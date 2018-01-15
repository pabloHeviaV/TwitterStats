package com.example.yo.twittfollows.tabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.yo.twittfollows.R;
import com.example.yo.twittfollows.util.AdaptadorListas;
import com.example.yo.twittfollows.util.GetData;

/**
 * Fragment que almacena tus mutuals, es decir, gente que te sigue
 * y tú les sigues.
 */
public class MutualsTab extends Fragment {

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
        View rootView = inflater.inflate(R.layout.tab_mutuals, container, false);
        tv = rootView.findViewById(R.id.textView4);
        gd = GetData.getInstance();

        adaptadorListas = new AdaptadorListas(this.getActivity(), gd.getMutualsList());
        Log.e("mutuals", "Adapter mutual creado");
        lista = rootView.findViewById(R.id.mutuals);
        lista.setAdapter(adaptadorListas);
        Spanned datos = Html.fromHtml(getString(R.string.mutualsTitle) +
                " <b>(" + gd.getMutualsList().size() + ")</b>");
        tv.setText(datos);
        return rootView;
    }

    /**
     * Recarga la lista.
     */
    public void updateList() {
        if (adaptadorListas != null) {
            adaptadorListas.clear();
            adaptadorListas.addAll(gd.getMutualsList());
            adaptadorListas.notifyDataSetChanged();
            Spanned datos = Html.fromHtml(getString(R.string.mutualsTitle) +
                    " <b>(" + gd.getMutualsList().size() + ")</b>");
            tv.setText(datos);
        }

    }

    /**
     * Elimina todos los objetos de la lista.
     */
    public void clearList() {
        if (adaptadorListas != null) {
            adaptadorListas.clear();
            adaptadorListas.notifyDataSetChanged();
            Spanned datos = Html.fromHtml(getString(R.string.mutualsTitle) +
                    " <b>(0)</b>");
            tv.setText(datos);
        }
    }
}
