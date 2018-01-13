package com.example.yo.twitterstats;

import com.google.gson.annotations.SerializedName;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class MyTwitterApiClient extends TwitterApiClient {
    public MyTwitterApiClient(TwitterSession session) {
        super(session);
    }

    public GetUsersShowAPICustomService getUserAPICustomService() {
        return getService(GetUsersShowAPICustomService.class);
    }
    interface GetUsersShowAPICustomService {
        @GET("/1.1/users/show.json")
        Call<User> show(@Query("user_id") long userId);
    }

}




