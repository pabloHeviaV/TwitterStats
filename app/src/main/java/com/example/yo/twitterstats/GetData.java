package com.example.yo.twitterstats;

import android.util.Log;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import retrofit2.Response;

/**
 * Created by Pablo on 11/01/2018.
 */

public final class GetData
{

    static TwitterSession  session = TwitterCore.getInstance().getSessionManager().getActiveSession();
    public static void getFollowers(){
        new MyTwitterApiClient(session).getFollowersListCustomService().list(session.getUserId(), new Callback<Response>() {
            @Override
            public void success(Result<Response> result) {
                String resultado = result.response.body().toString();
                Log.d("USERS",resultado);

            }

            @Override
            public void failure(TwitterException exception) {

            }
        });



    }

    public class TwitterUser{
        public Long userId;
        public String screename;
        public String profilePicURL;
        public boolean following;

        public TwitterUser(Long userId, String screename, String profilePicURL, boolean following) {
            this.userId = userId;
            this.screename = screename;
            this.profilePicURL = profilePicURL;
            this.following = following;
        }
    }
}

