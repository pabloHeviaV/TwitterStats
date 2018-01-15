package com.example.yo.twittfollows.util;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yo.twittfollows.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Clase que extiende de {@link ArrayAdapter} y sirve para configurar los elementos
 * de los ListView de las pestañas.
 */

public class AdaptadorListas extends ArrayAdapter<TwitterUser> {

    private final Activity context;
    private final List<TwitterUser> lista;

    public AdaptadorListas(Activity context, List<TwitterUser> lista) {
        super(context, R.layout.fila_lista, lista);

        this.context = context;
        this.lista = lista;
    }

    /**
     * Configura los elementos de los ListView para que muestren el
     * nombre, el screen name y la foto de perfil de los usuarios.
     *
     * @param posicion
     * @param view
     * @param parent
     * @return
     */
    public View getView(int posicion, View view, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.fila_lista, null, true);

        TextView txtTitle = rowView.findViewById(R.id.texto_principal);
        ImageView imageView = rowView.findViewById(R.id.icon);
        TextView etxDescripcion = rowView.findViewById(R.id.texto_secundario);

        txtTitle.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
        txtTitle.setText(lista.get(posicion).getName());
        Picasso.with(context.getBaseContext()).load(lista.get(posicion).getProfilePicURL()).
                resize(250, 250)
                .into((imageView));
        etxDescripcion.setText("@" + lista.get(posicion).getScreenName());

        return rowView;
    }

}
