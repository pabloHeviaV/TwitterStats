package com.example.yo.twitterstats.tabs;



/**
 * Created by yo on 09/01/2018.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yo.twitterstats.R;

/**
 * Fragment que almacena los usuarios que te han dado unfollow recientemente.
 */
public class RecentUnfollowersTab extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_recentunfollowers, container, false);

        return rootView;
    }



}
