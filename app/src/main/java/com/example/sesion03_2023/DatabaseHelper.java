package com.example.sesion03_2023;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "mi_base_de_datos";
    private static final int DATABASE_VERSION = 2;

    // Sentencia SQL para crear la tabla "Carrera"
    private static final String CREATE_TABLE_CARRERA = "CREATE TABLE carrera (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "nombre TEXT," +
            "estado TEXT);";

    // Sentencia SQL para crear la tabla "Alumnos"
    private static final String CREATE_TABLE_ALUMNOS = "CREATE TABLE alumnos (" +
            "idAlumno INTEGER PRIMARY KEY AUTOINCREMENT," +
            "nombre TEXT," +
            "apellidos TEXT," +
            "correo TEXT," +
            "carrera_id INTEGER," +
            "FOREIGN KEY (carrera_id) REFERENCES carrera(id));";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear las tablas en la base de datos
        db.execSQL(CREATE_TABLE_CARRERA);
        db.execSQL(CREATE_TABLE_ALUMNOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            // Realiza las actualizaciones necesarias para la versión 2
            db.execSQL("ALTER TABLE alumnos ADD COLUMN nueva_columna TEXT;");
        }

        // Puedes seguir con otros casos de actualización si es necesario

        // En caso de actualizaciones futuras de la base de datos
        db.execSQL("DROP TABLE IF EXISTS carrera");
        db.execSQL("DROP TABLE IF EXISTS alumnos");
        onCreate(db);
    }
}

