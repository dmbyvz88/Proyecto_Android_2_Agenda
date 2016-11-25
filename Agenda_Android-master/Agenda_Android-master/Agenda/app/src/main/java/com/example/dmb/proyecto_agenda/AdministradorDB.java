package com.example.dmb.proyecto_agenda;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
/**
 * Diego Murillo Barrantes
 */
public class AdministradorDB {
    SQLiteOpenHelper dbHelper;
    SQLiteDatabase database;
    /**
     * Metodo principal y estático que ejecuta las notas
     */
    private static final String[] allColumns = {
            NotasDBOpenHelper.CN_ID,
            NotasDBOpenHelper.CN_TITLE,
            NotasDBOpenHelper.CN_HOUR,
            NotasDBOpenHelper.CN_PLACE,
            NotasDBOpenHelper.CN_CONTEND
    };

    /**
     * Constructor principal del administrador de la base de datos
     *
     * @param context
     */
    public AdministradorDB(Context context) {
        dbHelper = new NotasDBOpenHelper(context);
    }

    /**
     * Abrir la base de datos
     */
    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    /**
     * Cerrar la base de datos
     */
    public void close() {
        dbHelper.close();
    }

    /**
     * Crea una base de datos
     *
     * @param notas
     * @return
     */
    //Insertar en la base de datos
    public Notas create(Notas notas) {
        database.insert(NotasDBOpenHelper.TABLE_NAME, null, columnas(notas));
        return notas;
    }

    /**
     * Actualiza una nota
     *
     * @param notas
     * @return
     */
    public Notas update(Notas notas, int id) {
        database.update(NotasDBOpenHelper.TABLE_NAME, columnas(notas), NotasDBOpenHelper.CN_ID + " = ? ", new String[]{Long.toString(id)});
        return notas;
    }

    /**
     * Contiene los valores del objeto notas
     *
     * @param notas
     * @return
     */
    public ContentValues columnas(Notas notas) {
        ContentValues values = new ContentValues();
        values.put(NotasDBOpenHelper.CN_TITLE, notas.getTitulo());
        values.put(NotasDBOpenHelper.CN_CONTEND, notas.getDescripcion());
        values.put(NotasDBOpenHelper.CN_PLACE, notas.getLugar());
        values.put(NotasDBOpenHelper.CN_HOUR, notas.getHora());
        return values;
    }

    /**
     * Borra una nota
     *
     * @param id
     */
    public void delete(long id) {
        database.delete(NotasDBOpenHelper.TABLE_NAME, NotasDBOpenHelper.CN_ID + " = ?", new String[]{Long.toString(id)});
    }

    /**
     * Retorna la lista de notas segun el buscador
     *
     * @return
     */
    public List<Notas> buscarNotas(String titulo) {
        Cursor cursor = database.query(NotasDBOpenHelper.TABLE_NAME, allColumns, NotasDBOpenHelper.CN_TITLE + " like   ? ", new String[]{titulo + "%"}, null, null, null);
        List<Notas> notas = cursorLista(cursor);
        return notas;
    }

    /**
     * Retorna toda la lista de notas
     *
     * @return
     */
    public List<Notas> todasNotas() {
        Cursor cursor = database.query(NotasDBOpenHelper.TABLE_NAME, allColumns, null, null, null, null, "Fecha ASC");
        List<Notas> notas = cursorLista(cursor);
        return notas;
    }

    /**
     * @param cursor
     * @return
     */
    public List<Notas> cursorLista(Cursor cursor) {
        List<Notas> notas = new ArrayList<Notas>();
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Notas nota = new Notas();
                nota.setId(cursor.getLong(cursor.getColumnIndex(NotasDBOpenHelper.CN_ID)));
                nota.setTitulo(cursor.getString(cursor.getColumnIndex(NotasDBOpenHelper.CN_TITLE)));
                nota.setDescripcion(cursor.getString(cursor.getColumnIndex(NotasDBOpenHelper.CN_CONTEND)));
                nota.setHora(cursor.getString(cursor.getColumnIndex(NotasDBOpenHelper.CN_HOUR)));
                nota.setLugar(cursor.getString(cursor.getColumnIndex(NotasDBOpenHelper.CN_PLACE)));
                notas.add(nota);
            }
        }
        return notas;
    }
}
