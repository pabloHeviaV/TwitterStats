package com.example.yo.twitterstats;



/**
 * Created by yo on 09/01/2018.
 */

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.twitter.sdk.android.core.TwitterCore;


public class FansTab extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_fans, container, false);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

    }
}
