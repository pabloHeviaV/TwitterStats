package com.example.yo.twitterstats;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Ivan on 13/01/2018.
 */

public class AdaptadorListas extends ArrayAdapter<TwitterUser> {

    private final Activity context;
    private final List<TwitterUser> lista;

    public AdaptadorListas(Activity context, List<TwitterUser>lista) {
        super(context, R.layout.fila_lista,lista);

        this.context=context;
        this.lista=lista;
    }

    public View getView(int posicion, View view, ViewGroup parent){

        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.fila_lista,null,true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.texto_principal);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView etxDescripcion = (TextView) rowView.findViewById(R.id.texto_secundario);

        txtTitle.setText(lista.get(posicion).getName());
        Picasso.with(context.getBaseContext()).load(lista.get(posicion).getProfilePicURL()).
               resize(250,250)
                .into((imageView));
        etxDescripcion.setText("@"+lista.get(posicion).getScreenName());

        return rowView;
    }

}
