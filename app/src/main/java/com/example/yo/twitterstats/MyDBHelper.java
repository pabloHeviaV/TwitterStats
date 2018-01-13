package com.example.yo.twitterstats;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Android on 21/10/15.
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

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_SCREENAME = "screename";
    public static final String COLUMN_PROFILE_PIC_URL = "profilePicUrl";
    public static final String COLUMN_NAME = "name";



    /**
     * Script para crear la base datos
     */
    private static final String DATABASE_CREATE =
            "create table " + TABLE_FOLLOWING
            + "( " + COLUMN_ID + " " + "bigint primary key, "
            + COLUMN_SCREENAME + " text not null, "
            + COLUMN_NAME + " text not null, "
            + COLUMN_PROFILE_PIC_URL+ " text not null" + "); " +

            "create table " + TABLE_FOLLOWERS
            + "( " + COLUMN_ID + " " + "bigint primary key, "
            + COLUMN_SCREENAME  + " text not null, "
            + COLUMN_NAME + " text not null, "
            + COLUMN_PROFILE_PIC_URL +  " text not null" + "); ";

    /**
     * Script para borrar la base de datos
     */
   // private static final String DATABASE_DROP = "DROP TABLE IF EXISTS " + TABLE_USERS;

    public MyDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                      int version) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //invocacamos execSQL pq no devuelve ningún tipo de dataset
        db.execSQL(DATABASE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*db.execSQL(DATABASE_DROP);
        this.onCreate(db);*/

    }
}
