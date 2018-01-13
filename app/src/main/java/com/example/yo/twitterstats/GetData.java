package com.example.yo.twitterstats;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;



import com.twitter.sdk.android.core.TwitterCore;

import com.twitter.sdk.android.core.TwitterSession;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import twitter4j.IDs;
import twitter4j.ResponseList;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Created by Pablo on 11/01/2018.
 */

public class GetData
{
    // Singleton
    private static final GetData ourInstance = new GetData();
    static GetData getInstance() {
        return ourInstance;
    }

    private List<TwitterUser> followersList;
    private List<TwitterUser> followingList;

    private TwitterSession  session;
    private  Twitter twitter;

    private GetData(){
        session = TwitterCore.getInstance().getSessionManager().getActiveSession();
        followersList = new ArrayList<>();
        followingList = new ArrayList<>();

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("2wM6G1zTaOvSjNoAZQaP9UbUr")
                .setOAuthConsumerSecret("4tlSpm7l1s8SHPSrmToPgDZPRJWPxhUonuZ2sTxJUnQJol3QBf")
                .setOAuthAccessToken("262623444-i1a20bjgLXj3lkW3XVq9S75HpoWAL4fw72CGXT6X")
                .setOAuthAccessTokenSecret("YrN4iQC8eKsym5D7xHvf25kwj4y3mMBJmFBTF4QCKU3P9");
        twitter = new TwitterFactory(cb.build()).getInstance();

    }

    /*
    Ejecuta una query contra la API de Twitter y obtiene los ids de los usuarios
    que siguen al usuario que tiene la sesión activa. Luego hace otra query para
    obtener los datos de estos usuarios.
     */
    public void fetchFollowers() {

        long[] ids = null;
        try {


            //Se obtienen los ids de los followers
            IDs idsResponse = twitter.getFollowersIDs(session.getUserName(),-1);
            ids = idsResponse.getIDs();
            }
        catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get followers' ids: " + te.getMessage());
        }
        Log.e("Ids followers",String.valueOf(ids.length));
        try{
            int i=0;
            int limit;
            do{
                //Corta el array de ids en array de 100 como max
                limit = (i+99>ids.length)? ids.length: i+99;
                long[] idsSlice = Arrays.copyOfRange(ids, i, limit);
                ResponseList<User> users = twitter.lookupUsers(idsSlice);
                for(User u: users) {
                    TwitterUser twitterUser = new TwitterUser(
                            u.getId(),
                            u.getScreenName(),
                            u.getName(),
                            u.getMiniProfileImageURL()
                    );
                    followersList.add(twitterUser);
                }
                i+= 99;
            }while (limit!=ids.length);

        }catch (TwitterException te){
            te.printStackTrace();
            System.out.println("Failed to get followers info: " + te.getMessage());
        }
            Log.e("Nº followers",String.valueOf(followersList.size()));
}

    /*
    Ejecuta una query contra la API de Twitter y obtiene los users a los que sigue el
    usuario que tiene la sesión activa.
     */
    public void fetchFollowing(){
        long[] ids = null;
        try {


            //Se obtienen los ids de los followers
            IDs idsResponse = twitter.getFriendsIDs(session.getUserName(),-1);
            ids = idsResponse.getIDs();
        }
        catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get following' ids: " + te.getMessage());
        }
        Log.e("Ids following",String.valueOf(ids.length));
        try{
            int i=0;
            int limit;
            do{
                //Corta el array de ids en array de 100 como max
                limit = (i+99>ids.length)? ids.length: i+99;
                long[] idsSlice = Arrays.copyOfRange(ids, i, limit);
                ResponseList<User> users = twitter.lookupUsers(idsSlice);
                for(User u: users) {
                    TwitterUser twitterUser = new TwitterUser(
                            u.getId(),
                            u.getScreenName(),
                            u.getName(),
                            u.getMiniProfileImageURL()
                    );
                    followingList.add(twitterUser);
                }
                i+= 99;
            }while (limit!=ids.length);

        }catch (TwitterException te){
            te.printStackTrace();
            System.out.println("Failed to get following info: " + te.getMessage());
        }
        Log.e("Nº following",String.valueOf(followingList.size()));

    }

    public void fetchData(){
        fetchFollowing();
        fetchFollowers();
    }

    public List<TwitterUser> getFollowers(){ return followersList; }

    public List<TwitterUser> getFollowing() {
        return followingList;
    }
}

