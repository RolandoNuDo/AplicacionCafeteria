package com.itchihuahuaii.aplicacioncafeteria;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.widget.Toast;

/**
 * Created by usuario1 on 11/11/2016.
 */

public class BaseDatos extends SQLiteOpenHelper {

    private static final String NOMBRE_BASE_DATOS = "cafeteria.db";

    private static final int VERSION_ACTUAL = 1;

    private final Context context;


    public BaseDatos(Context context) {
        super(context, NOMBRE_BASE_DATOS, null, VERSION_ACTUAL);
        this.context = context;
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                db.setForeignKeyConstraintsEnabled(true);
            } else {
                db.execSQL("PRAGMA foreign_keys=ON");
            }
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TABLA CARRITO

        db.execSQL("CREATE TABLE carrito(id INTEGER PRIMARY KEY AUTOINCREMENT,id_usuario INTEGER NOT NULL REFERENCES usuario(id)," +
                "fecha TEXT NOT NULL)");

        // TABLA CARRITO_DETALLE
        db.execSQL("CREATE TABLE carrito_detalle(id INTEGER PRIMARY KEY AUTOINCREMENT,id_carrito INTEGER NOT NULL REFERENCES carrito(id), " +
                "id_producto INTEGER NOT NULL REFERENCES producto(id), precio_venta INTEGER NOT NULL, estado TEXT NOT NULL)");

        //TABLA PRODUCTO
        db.execSQL("CREATE TABLE producto(id INTEGER PRIMARY KEY AUTOINCREMENT,nombre TEXT UNIQUE NOT NULL," +
                "precio INTEGER NOT NULL,id_categoria INTEGER NOT NULL REFERENCES categoria(id),cantidad INTEGER NOT NULL CHECK(cantidad>=0),imagen INTEGER NOT NULL)");

        // TABLA CATEGORIA
        db.execSQL("CREATE TABLE categoria(id INTEGER PRIMARY KEY AUTOINCREMENT,nombre TEXT UNIQUE NOT NULL)");


        // TABLA USUARIO
        db.execSQL("CREATE TABLE usuario(id INTEGER PRIMARY KEY AUTOINCREMENT,nombre TEXT NOT NULL, nick TEXT UNIQUE NOT NULL," +
                "tipo TEXT NOT NULL, password TEXT NOT NULL)");

        // TABLA INSUMOS
        db.execSQL("CREATE TABLE insumo(id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT UNIQUE NOT NULL,cantidad INTEGER NOT NULL CHECK(cantidad>=0),imagen INTEGER NOT NULL)");

        // TABLA INSUMO_PRODUCTO
        db.execSQL("CREATE TABLE insumo_producto(id INTEGER PRIMARY KEY AUTOINCREMENT,id_insumo INTEGER NOT NULL REFERENCES insumo(id),id_producto INTEGER NOT NULL REFERENCES producto(id),uso INTEGER NOT NULL CHECK(uso>=0))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}
