package com.example.yo.twitterstats;

import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;



import com.twitter.sdk.android.core.TwitterCore;

import com.twitter.sdk.android.core.TwitterSession;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

        // Claves de autenticación para usar twitter4j
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
    Devuelve false si no es posible obtener los datos.
     */
    private boolean fetchFollowers() {

        long[] ids = null;
        try {
            //Se obtienen los ids de los followers
            IDs idsResponse = twitter.getFollowersIDs(session.getUserName(),-1);
            ids = idsResponse.getIDs();
            }
        catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get followers' ids: " + te.getMessage());
            return false;
        }
        Log.e("Ids followers",String.valueOf(ids.length));
        try{
            int i=0;
            int limit;
            do{
                // Corta el array de ids en array de 100 como max
                limit = (i+99>ids.length)? ids.length: i+99;
                long[] idsSlice = Arrays.copyOfRange(ids, i, limit);
                // Se obtienen los datos de los users para los ids
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
            return false;
        }
            Log.e("Nº followers",String.valueOf(followersList.size()));
        return true;
}

    /*
    Ejecuta una query contra la API de Twitter y obtiene los users a los que sigue el
    usuario que tiene la sesión activa.
    Devuelve false si no es posible obtener los datos.
     */
    private boolean fetchFollowing(){
        long[] ids = null;
        try {
            //Se obtienen los ids de los followers
            IDs idsResponse = twitter.getFriendsIDs(session.getUserName(),-1);
            ids = idsResponse.getIDs();
        }
        catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get following' ids: " + te.getMessage());
            return false;
        }
        Log.e("Ids following",String.valueOf(ids.length));
        try{
            int i=0;
            int limit;
            do{
                // Corta el array de ids en array de 100 como max
                limit = (i+99>ids.length)? ids.length: i+99;
                long[] idsSlice = Arrays.copyOfRange(ids, i, limit);
                // Se obtienen los datos de los usuarios para los ids dados
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
            return false;
        }
        Log.e("Nº following",String.valueOf(followingList.size()));
        return true;
    }

    /*
    Obtiene los datos para el usuario que tiene abierta la sesión.
    Devuelve el user o null si hay algún error.
     */
    public User getCurrentUserData(){
        try {
           return twitter.showUser(session.getUserName());
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get user info: " + te.getMessage());
        }
        return null;
    }

    /*
    Obtiene los datos de seguidores y seguidos para el usuario que tiene abierta la
    sesión.
    Si no es posible obtener los datos, devuelve false.
     */
    public boolean fetchData(){
        return fetchFollowing() && fetchFollowers();
    }

    public List<TwitterUser> getFollowers(){ return followersList; }

    public List<TwitterUser> getFollowing() {
        return followingList;
    }
}

