package com.example.yo.twitterstats;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class MyTwitterApiClient extends TwitterApiClient {
    public MyTwitterApiClient(TwitterSession session) {
        super(session);
    }

    public GetUsersShowAPICustomService getUserCustomService() {
        return getService(GetUsersShowAPICustomService.class);
    }

    public GetFollowersListAPICustomService getFollowersListCustomService(){
        return getService(GetFollowersListAPICustomService.class);
    }

}

interface GetUsersShowAPICustomService {
    @GET("/1.1/users/show.json")
    Call<User> show(@Query("user_id") long userId);
}

interface GetFollowersListAPICustomService {
    @GET("/1.1/followers/list.json")
    void list(@Query("user_id") long id, Callback<Response> cb);
}
