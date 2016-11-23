package com.example.dmb.proyecto_agenda;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * Diego Murillo Barrantes
 */
public class NotasDBOpenHelper extends SQLiteOpenHelper {

    /**
     * Tabla de la base de datos
     **/
    private static final String DB_NAME = "DB_Agent";//nombre de la base de datos
    private static final int DB_VERSION = 1;//version de base de datos
    public static final String TABLE_NAME = "Notas";//Nombre de la tabla
    /**
     * Columnas de la tabla
     **/
    public static final String CN_ID = "IdNota";
    public static final String CN_TITLE = "Titulo";
    public static final String CN_CONTEND = "Nota";
    public static final String CN_PLACE = "Lugar";
    public static final String CN_HOUR = "Fecha";

    /**
     * Setencia para crear base de datos
     **/
    private static String CREATE_TABLE = " CREATE TABLE " + TABLE_NAME + "("
            + CN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CN_TITLE + " TEXT NOT NULL,"
            + CN_CONTEND + " TEXT NOT NULL,"
            + CN_HOUR + " TEXT NOT NULL,"
            + CN_PLACE + " TEXT NOT NULL" +
            ")";

    /**
     * Contructor
     *
     * @param context
     */
    public NotasDBOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * Creacion de base de datos
     *
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    /**
     * Actualiza la base de datos
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
