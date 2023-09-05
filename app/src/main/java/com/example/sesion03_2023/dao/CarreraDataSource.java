package com.example.sesion03_2023.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sesion03_2023.DatabaseHelper;
import com.example.sesion03_2023.models.Carrera;

import java.util.ArrayList;
import java.util.List;

public class CarreraDataSource {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public CarreraDataSource(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long agregarCarrera(Carrera carrera) {
        ContentValues values = new ContentValues();
        values.put("nombre", carrera.getNombre());
        values.put("estado", carrera.getEstado() ? 1 : 0); // Convierte el valor booleano a 1 o 0
        return database.insert("carrera", null, values);
    }

    public boolean eliminarCarreraPorId(int carreraId) {
        return database.delete("carrera", "id = ?", new String[]{String.valueOf(carreraId)}) > 0;
    }

    public Carrera buscarCarreraPorId(int carreraId) {
        Cursor cursor = database.query("carrera", null, "id = ?", new String[]{String.valueOf(carreraId)}, null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                Carrera carrera = cursorToCarrera(cursor);
                cursor.close();
                return carrera;
            }
            cursor.close();
        }

        return null; // No se encontró la carrera con el ID especificado
    }

    private Carrera cursorToCarrera(Cursor cursor) {
        int idIndex = cursor.getColumnIndex("id");
        int nombreIndex = cursor.getColumnIndex("nombre");
        int estadoIndex = cursor.getColumnIndex("estado");

        int id = cursor.getInt(idIndex);
        String nombre = cursor.getString(nombreIndex);
        boolean estado = cursor.getInt(estadoIndex) == 1;

        return new Carrera(id, nombre, estado);
    }

    public List<Carrera> listarCarreras() {
        List<Carrera> carreras = new ArrayList<>();
        Cursor cursor = database.query("carrera", null, null, null, null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    Carrera carrera = cursorToCarrera(cursor);
                    carreras.add(carrera);
                } while (cursor.moveToNext());
                cursor.close();
            }
        }

        return carreras;
    }

    public boolean actualizarCarrera(Carrera carrera) {
        ContentValues values = new ContentValues();
        values.put("nombre", carrera.getNombre());
        values.put("estado", carrera.getEstado() ? 1 : 0); // Convierte el valor booleano a 1 o 0

        int filasActualizadas = database.update("carrera", values, "id = ?", new String[]{String.valueOf(carrera.getIdCarrera())});
        return filasActualizadas > 0;
    }
}
