package com.example.yo.twittfollows.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.yo.twittfollows.util.TwitterUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan on 11/01/2018.
 */

public class DataSource {
    /**
     * Referencia para manejar la base de datos. Este objeto lo obtenemos a partir de MyDBHelper
     * y nos proporciona metodos para hacer operaciones
     * CRUD (create, read, updateList and delete)
     */
    private SQLiteDatabase database;
    /**
     * Referencia al helper que se encarga de crear y actualizar la base de datos.
     */
    private MyDBHelper dbHelper;

    /**
     * Constructor.
     *
     * @param context
     */
    public DataSource(Context context) {
        //el último parámetro es la versión
        dbHelper = new MyDBHelper(context, "twitter.db", null, 1);
    }

    public SQLiteDatabase getBBDD() {
        return database;
    }

    public MyDBHelper getDbHelper() {
        return dbHelper;
    }

    /**
     * Abre una conexion para escritura con la base de datos.
     * Esto lo hace a traves del helper con la llamada a getWritableDatabase. Si la base de
     * datos no esta creada, el helper se encargara de llamar a onCreate
     *
     * @throws SQLException
     */
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    /**
     * Cierra la conexión con la base de datos
     */
    public void close() {
        dbHelper.close();
    }

    /**
     * Inserta un TwitterUser en una tabla.
     *
     * @param userToInsert el usuario que se quiere insertar
     * @param table        la tabla en la que se va a insertar
     * @return
     */
    public long createTwitterUser(TwitterUser userToInsert, String table, int order) {
        // Establecemos los valores que se insertarán
        ContentValues values = new ContentValues();
        values.put(MyDBHelper.COLUMN_ID, userToInsert.getUserId());
        values.put(MyDBHelper.COLUMN_SCREENAME, userToInsert.getScreenName());
        values.put(MyDBHelper.COLUMN_NAME, userToInsert.getName());
        values.put(MyDBHelper.COLUMN_PROFILE_PIC_URL, userToInsert.getProfilePicURL());
        if (!table.equals(MyDBHelper.TABLE_UNFOLLOWERS))
            values.put(MyDBHelper.COLUMN_ORDER, order);

        // Insertamos la valoracion
        long insertId = database.insert(table, null, values);

        return insertId;
    }

    /**
     * Obtiene todos los usuarios.
     *
     * @return Lista de objetos de tipo TwitterUser
     */
    public List<TwitterUser> getAllUsers(String table) {
        // Lista que almacenara el resultado
        List<TwitterUser> twitterUserList = new ArrayList<TwitterUser>();
        //hacemos una query porque queremos devolver un cursor

        String query = (table.equals(MyDBHelper.TABLE_UNFOLLOWERS)) ? "select * from " + table
                : "select * from " + table + " order by " + MyDBHelper.COLUMN_ORDER + " asc ";

        Cursor cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                TwitterUser user = new TwitterUser(cursor.getLong(0), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3));

                twitterUserList.add(user);
                cursor.moveToNext();
            }
        }

        cursor.close();
        // Una vez obtenidos todos los datos y cerrado el cursor, devolvemos la
        // lista.
        return twitterUserList;
    }

}
