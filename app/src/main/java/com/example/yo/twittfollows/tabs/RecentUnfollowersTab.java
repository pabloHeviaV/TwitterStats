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
 * Fragment que almacena los usuarios que te han dado unfollow recientemente.
 */
public class RecentUnfollowersTab extends Fragment {

    private AdaptadorListas adaptadorListas;
    private ListView lista;
    private GetData gd;
    private View rootView;
    private TextView tv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_recentunfollowers,
                container, false);
        tv = rootView.findViewById(R.id.textView);

        gd = GetData.getInstance();

        adaptadorListas = new AdaptadorListas(this.getActivity(), gd.getUnfollowers());
        Log.e("Recent unfollowers", "Adapter recent unfollowers creado");
        lista = rootView.findViewById(R.id.recentUnfollowers);
        lista.setAdapter(adaptadorListas);
        Spanned datos = Html.fromHtml(getString(R.string.recentUnfollowersTitle) +
                " <b>(" + gd.getUnfollowers().size() + ")</b>");
        tv.setText(datos);
        return rootView;

    }

    /**
     * Recarga la lista.
     */
    public void updateList() {
        if (adaptadorListas != null) {
            adaptadorListas.clear();
            adaptadorListas.addAll(gd.getUnfollowers());
            adaptadorListas.notifyDataSetChanged();
            Spanned datos = Html.fromHtml(getString(R.string.recentUnfollowersTitle) +
                    " <b>(" + gd.getUnfollowers().size() + ")</b>");
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
            Spanned datos = Html.fromHtml(getString(R.string.recentUnfollowersTitle)
                    + " <b>(0)</b>");
            tv.setText(datos);
        }
    }


}
