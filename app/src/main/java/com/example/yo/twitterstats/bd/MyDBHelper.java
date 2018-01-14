package com.example.yo.twitterstats.bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 *
 */
public class MyDBHelper extends SQLiteOpenHelper {

    /**
     * Nombre y version de la base de datos
     */
    private static final String DATABASE_NAME = "twitter.db";
    private static final int DATABASE_VERSION = 1;
    /**
     * Nombre de la tabla valorations y sus columnas
     */
    public static final String TABLE_FOLLOWING = "following";
    public static final String TABLE_FOLLOWERS = "followers";
    public static final String TABLE_UNFOLLOWERS = "unfollowers";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_SCREENAME = "screename";
    public static final String COLUMN_PROFILE_PIC_URL = "profilePicUrl";
    public static final String COLUMN_NAME = "name";



    /**
     * Script para crear la base datos
     */
    private static final String DATABASE_CREATE_FOLLOWING =
            "create table " + TABLE_FOLLOWING
            + "( " + COLUMN_ID + " " + "integer primary key, "
            + COLUMN_SCREENAME + " text not null, "
            + COLUMN_NAME + " text not null, "
            + COLUMN_PROFILE_PIC_URL+ " text not null" + "); ";

    private static final String DATABASE_CREATE_UNFOLLOWERS=
            "create table " + TABLE_UNFOLLOWERS
            + "( " + COLUMN_ID + " " + " integer primary key, "
            + COLUMN_SCREENAME + " text not null, "
            + COLUMN_NAME + " text not null, "
            + COLUMN_PROFILE_PIC_URL +  " text not null" + "); ";
    private static final String DATABASE_CREATE_FOLLOWERS=
            " create table " + TABLE_FOLLOWERS
            + "( " + COLUMN_ID + " " + "integer primary key, "
            + COLUMN_SCREENAME  + " text not null, "
            + COLUMN_NAME + " text not null, "
            + COLUMN_PROFILE_PIC_URL +  " text not null" + "); ";

    private static final String DELETE_FROM_FOLLOWERS = "DELETE FROM " + TABLE_FOLLOWERS;
    private static final String DELETE_FROM_FOLLOWING = "DELETE FROM " + TABLE_FOLLOWING;
    private static final String DELETE_FROM_UNFOLLOWERS = "DELETE FROM " + TABLE_UNFOLLOWERS;

    /**
     * Script para borrar la base de datos
     */
    private static final String DATABASE_DROP = "DROP TABLE IF EXISTS ";

    public MyDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                      int version) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void deleteFromTableUnfollowers(SQLiteDatabase db){
        db.execSQL(DELETE_FROM_UNFOLLOWERS);
    }

    public void deleteFromTableFollowers(SQLiteDatabase db){
        db.execSQL(DELETE_FROM_FOLLOWERS);
    }

    public void deleteFromTableFollowing(SQLiteDatabase db){
        db.execSQL(DELETE_FROM_FOLLOWING);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //invocacamos execSQL pq no devuelve ningún tipo de dataset
        db.execSQL(DATABASE_CREATE_FOLLOWERS);
        db.execSQL(DATABASE_CREATE_FOLLOWING);
        db.execSQL(DATABASE_CREATE_UNFOLLOWERS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DATABASE_DROP+ TABLE_FOLLOWERS);
        db.execSQL(DATABASE_DROP+ TABLE_FOLLOWING);
        db.execSQL(DATABASE_DROP+ TABLE_UNFOLLOWERS);
        this.onCreate(db);
    }

    public static void dropDB(SQLiteDatabase db){
        db.execSQL(DATABASE_DROP+ TABLE_FOLLOWERS);
        db.execSQL(DATABASE_DROP+ TABLE_FOLLOWING);
        db.execSQL(DATABASE_DROP+ TABLE_UNFOLLOWERS);
    }
}
