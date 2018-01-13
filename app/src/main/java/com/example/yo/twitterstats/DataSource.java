package com.example.yo.twitterstats;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

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
     * Columnas de la tabla
     */
    private final String[] allColumns = { MyDBHelper.COLUMN_ID, MyDBHelper.COLUMN_SCREENAME,
            MyDBHelper.COLUMN_PROFILE_PIC_URL, MyDBHelper.COLUMN_NAME};
    /**
     * Constructor.
     *
     * @param context
     */
    public DataSource(Context context) {
        //el último parámetro es la versión
        dbHelper = new MyDBHelper(context, null, null, 1);
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
     * Cierra la conexion con la base de datos
     */
    public void close() {
        dbHelper.close();
    }


    public long createValoration( TwitterUser userToInsert, String table) {
        // Establecemos los valores que se insertaran
        ContentValues values = new ContentValues();
        values.put(MyDBHelper.COLUMN_ID, userToInsert.getUserId());
        values.put(MyDBHelper.COLUMN_SCREENAME,userToInsert.getScreenName());
        values.put(MyDBHelper.COLUMN_NAME, userToInsert.getName());
        values.put(MyDBHelper.COLUMN_PROFILE_PIC_URL, userToInsert.getProfilePicURL());

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
        Cursor cursor = database.query(table, allColumns,
                null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            final TwitterUser user = new TwitterUser(cursor.getLong(1),cursor.getString(2),cursor.getString(3),cursor.getString(4));

            twitterUserList.add(user);
            cursor.moveToNext();
        }

        cursor.close();
        // Una vez obtenidos todos los datos y cerrado el cursor, devolvemos la
        // lista.
        return twitterUserList;
    }

}
