package com.example.yo.twitterstats.util;

import android.util.Log;


import com.twitter.sdk.android.core.TwitterCore;

import com.twitter.sdk.android.core.TwitterSession;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
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
 * Clase que ejecuta las querys pertinentes a la API REST de Twitter para
 * obtener las listas de usuarios necesarias en las distintas pestañas de
 * CentralActivity, así como los datos del usuario conectado necesarios
 * en su perfil.
 */

public class GetData
{
    // Singleton
    private static final GetData ourInstance = new GetData();
    public static GetData getInstance() {
        return ourInstance;
    }

    /**
     * Lista de usuarios que siguen al User.
     */
    private List<TwitterUser> followersList;

    /**
     * Lista de usuarios a los que sigue el User.
     */
    private List<TwitterUser> followingList;

    /**
     * Lista de users que siguen al User pero él no les sigue.
     */
    private List<TwitterUser> fansList;

    /**
     * Lista de usuarios que el User sigue pero no le siguen de vuelta.
     */
    private List<TwitterUser> mutualsList;

    /**
     * Lista de usuarios que se se siguen mutuamente con el User.
     */
    private List<TwitterUser> notFollowingYouList;

    private TwitterSession  session;
    private Twitter twitter;

    /**
     * Constructor de la clase GetData.
     * Inicializa las listas de users,
     * obtiene la sesión activa de twitter e
     * inicializa el objeto twitter4j.Twitter usado para las querys a la API.
     *
     */
    private GetData(){
        session = TwitterCore.getInstance().getSessionManager().getActiveSession();
        followersList = new ArrayList<>();
        followingList = new ArrayList<>();
        fansList= new ArrayList<>();
        mutualsList= new ArrayList<>();
        notFollowingYouList= new ArrayList<>();

        // Claves de autenticación para usar twitter4j
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("2wM6G1zTaOvSjNoAZQaP9UbUr")
                .setOAuthConsumerSecret("4tlSpm7l1s8SHPSrmToPgDZPRJWPxhUonuZ2sTxJUnQJol3QBf")
                .setOAuthAccessToken("262623444-i1a20bjgLXj3lkW3XVq9S75HpoWAL4fw72CGXT6X")
                .setOAuthAccessTokenSecret("YrN4iQC8eKsym5D7xHvf25kwj4y3mMBJmFBTF4QCKU3P9");
        twitter = new TwitterFactory(cb.build()).getInstance();

    }

    /**
     * Ejecuta una query contra la API de Twitter y obtiene los ids de los
     * usuarios que siguen al usuario que tiene la sesión activa. Luego hace
     * otra query para obtener los datos de estos usuarios.
     *
     * @return true si obtiene los datos, false si hay un error
     */
    private boolean fetchFollowers() {
        followersList = new ArrayList<>();
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

        if(ids.length>0) {
            try {
                int i = 0;
                int limit;
                do {
                    // Corta el array de ids en array de 100 como max
                    limit = (i + 99 > ids.length) ? ids.length : i + 99;
                    long[] idsSlice = Arrays.copyOfRange(ids, i, limit);
                    // Se obtienen los datos de los users para los ids
                    Log.e("Ids followers slice",String.valueOf(idsSlice.length));
                    Log.e("i",String.valueOf(i));
                    Log.e("limit",String.valueOf(limit));
                    ResponseList<User> users = twitter.lookupUsers(idsSlice);
                    for (User u : users) {
                        TwitterUser twitterUser = new TwitterUser(
                                u.getId(),
                                u.getScreenName(),
                                u.getName(),
                                u.getOriginalProfileImageURL()
                        );
                        followersList.add(twitterUser);
                    }
                    i += 100;
                } while (limit != ids.length);

            } catch (TwitterException te) {
                te.printStackTrace();
                System.out.println("Failed to get followers info: " + te.getMessage());
                return false;
            }
            Log.e("Nº followers", String.valueOf(followersList.size()));
        }
        return true;
}
    /**
     * Ejecuta una query contra la API de Twitter y obtiene los ids de los
     * users a los que sigue el usuario que tiene la sesión activa. Luego
     * ejecuta una segunda query que obtiene los datos de estos usuarios.
     *
     * @return true si obtiene los datos, false si hay un error
     */
    private boolean fetchFollowing(){
        followingList = new ArrayList<>();
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

        if(ids.length>0) {
            try {
                int i = 0;
                int limit;
                do {
                    // Corta el array de ids en array de 100 como max
                    limit = (i + 99 > ids.length) ? ids.length : i + 99;
                    long[] idsSlice = Arrays.copyOfRange(ids, i, limit);
                    Log.e("Ids following slice",String.valueOf(idsSlice.length));
                    Log.e("i",String.valueOf(i));
                    Log.e("limit",String.valueOf(limit));

                    // Se obtienen los datos de los usuarios para los ids dados
                    ResponseList<User> users = twitter.lookupUsers(idsSlice);
                    for (User u : users) {
                        TwitterUser twitterUser = new TwitterUser(
                                u.getId(),
                                u.getScreenName(),
                                u.getName(),
                                u.getOriginalProfileImageURL()
                        );
                        followingList.add(twitterUser);
                    }
                    i += 100;
                } while (limit < ids.length);

            } catch (TwitterException te) {
                te.printStackTrace();
                System.out.println("Failed to get following info: " + te.getMessage());
                return false;
            }
            Log.e("Nº following", String.valueOf(followingList.size()));
        }
        return true;
    }

    /**
     * Obtiene los datos para el usuario que tiene la sesión activa.
     *
     * @return User el usuario, o null si hay algún error
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

    /**
     * Obtiene los datos de seguidores y seguidos para el usuario con la
     * sesión activa.
     *
     * @return true si se obtienen los datos, false si hay algún error
     */
    public boolean fetchData(){
        return  fetchFollowing() && fetchFollowers();
    }

    /**
     * Devuelve una copia de la lista de seguidores del usuario con la sesión
     * activa.
     *
     * @return lista de usuarios que siguen al User
     */
    public List<TwitterUser> getFollowers(){ return new ArrayList<>(followersList); }

    /**
     * Devuelve una copia de la lista de seguidos del usuario con la sesión
     * activa.
     *
     * @return lista de usuarios seguidos por el User
     */
    public List<TwitterUser> getFollowing() {
        return new ArrayList<>(followingList);
    }

    /**
     * Obtiene las listas de usuarios necesarias para las tabs.
     *
     * -fansList: lista de users que siguen al User pero él no les sigue
     * -notFollowingYouList: lista de usuarios que el User sigue pero no
     *      le siguen de vuelta
     * -mutualsList: lista de usuarios que se se siguen mutuamente con el User
     */
    public void calculateLists(){

        // Followers - Following
        Log.e("Followers antes", String.valueOf(getFollowers().size()));
        fansList = getFollowers();
        fansList.removeAll(getFollowing());
        Log.e("Fans list", String.valueOf(fansList.size()));
        Log.e("Followers despues", String.valueOf(getFollowers().size()));

        // Following - Followers
        notFollowingYouList = getFollowing();
        notFollowingYouList.removeAll(getFollowers());
        Log.e("NotF list", String.valueOf(notFollowingYouList.size()));

        //Followers ∪ Following
        mutualsList = getFollowing();
        mutualsList.retainAll(new HashSet<TwitterUser>(getFollowers()));
        Log.e("Mutuals list", String.valueOf(mutualsList.size()));
    }

    /**
     * Devuelve la lista de fans del User.
     *
     * @return lista de usuarios que son fans del User
     */
    public List<TwitterUser> getFansList() {
        return fansList;
    }

    /**
     * Devuelvela lista de seguidores mutuos del User.
     *
     * @return una lista de usuarios que son mutos con el User
     */
    public List<TwitterUser> getMutualsList() {
        return mutualsList;
    }

    /**
     * Devuelve la lista de usuarios a los que el User sigue
     * pero no le siguen de vuelta.
     *
     * @return lista de users que no siguen de vuelta al User
     */
    public List<TwitterUser> getNotFollowingYouList() {
        return notFollowingYouList;
    }
}

