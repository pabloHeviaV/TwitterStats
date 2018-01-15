package com.example.yo.twittfollows.tabs;


/**
 * Created by yo on 09/01/2018.
 */

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
 * Fragment que almacena tus No seguidores, es decir, gente que tú sigues
 * pero no te siguen a ti.
 */
public class UnfollowersTab extends Fragment {

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
        View rootView = inflater.inflate(R.layout.tab_unfollowers, container, false);
        tv = rootView.findViewById(R.id.textView6);
        gd = GetData.getInstance();

        adaptadorListas = new AdaptadorListas(this.getActivity(), gd.getNotFollowingYouList());
        Log.e("Unfollowers", "Adapter unfollowers creado");
        lista = rootView.findViewById(R.id.notFollowingYouList);
        lista.setAdapter(adaptadorListas);
        Spanned datos = Html.fromHtml(getString(R.string.unfollowersTitle) +
                " <b>(" + gd.getNotFollowingYouList().size() + ")</b>");
        tv.setText(datos);
        return rootView;
    }

    /**
     * Recarga la lista.
     */
    public void updateList() {
        if (adaptadorListas != null) {
            adaptadorListas.clear();
            adaptadorListas.addAll(gd.getNotFollowingYouList());
            adaptadorListas.notifyDataSetChanged();
            Spanned datos = Html.fromHtml(getString(R.string.unfollowersTitle) +
                    " <b>(" + gd.getNotFollowingYouList().size() + ")</b>");
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
            Spanned datos = Html.fromHtml(getString(R.string.unfollowersTitle) +
                    " <b>(0)</b>");
            tv.setText(datos);
        }
    }
}
