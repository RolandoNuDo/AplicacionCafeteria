package com.itchihuahuaii.aplicacioncafeteria;

/**
 * Created by usuario1 on 11/11/2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

public final class Consultas {

    private static BaseDatos baseDatos;

    private static Consultas instancia = new Consultas();


    private Consultas() {
    }

    public static Consultas obtenerInstancia(Context contexto) {
        if (baseDatos == null) {
            baseDatos = new BaseDatos(contexto);
        }
        return instancia;
    }

    public Cursor getProducto() {
        SQLiteDatabase db = baseDatos.getReadableDatabase();
        String sql = "SELECT * FROM producto";
        return db.rawQuery(sql, null);
    }

    public void renovar() {
        baseDatos.onUpgrade(baseDatos.getWritableDatabase(), 1, 2);
    }


    public Cursor getComidasByTipo(String tipo) {
        SQLiteDatabase db = baseDatos.getReadableDatabase();
        String sql = "SELECT producto.nombre, producto.precio ,producto.cantidad, producto.imagen,producto.id_categoria FROM producto,categoria WHERE " +
                "producto.id_categoria=categoria.id AND categoria.nombre='" + tipo + "'";
        return db.rawQuery(sql, null);
    }

    public Cursor getCursorQuery(String sql) {
        SQLiteDatabase db = baseDatos.getReadableDatabase();
        return db.rawQuery(sql, null);
    }

    public String getData(String sql) {
        SQLiteDatabase db = baseDatos.getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);
        if (c.moveToFirst()) {
            return c.getString(0);
        } else {
            return "";
        }

    }

    public String insertCarrito(int id_usuario, String fecha) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_usuario", id_usuario);
        values.put("fecha", fecha);
        return "" + db.insertOrThrow("carrito", null, values);
    }

    public String insertCarrito_detalle(int id_carrito, int id_producto, int precio_venta, String estado) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_carrito", id_carrito);
        values.put("id_producto", id_producto);
        values.put("precio_venta", precio_venta);
        values.put("estado", estado);
        return "" + db.insertOrThrow("carrito_detalle", null, values);
    }

    public void insertCategoria(String nombre) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        db.insertOrThrow("categoria", null, values);
    }

    public Long insertProducto(String nombre, int precio, int id_categoria, int cantidad, int imagen) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("precio", precio);
        values.put("id_categoria", id_categoria);
        values.put("cantidad", cantidad);
        values.put("imagen", imagen);
        return db.insertOrThrow("producto", null, values);
    }

    public boolean updateComida(int id_producto, String nombre, int precio) {
        Cursor c = getCursorQuery("SELECT insumo_producto.uso,insumo.cantidad FROM insumo_producto,insumo WHERE insumo_producto.id_insumo=insumo.id AND insumo_producto.id_producto=" + id_producto);

        c.moveToFirst();
        int menor = c.getInt(1) / c.getInt(0);
        do {
            int aux = c.getInt(1) / c.getInt(0);
            if (menor > aux) {
                menor = aux;
            }
        } while (c.moveToNext());
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("precio", precio);
        values.put("id_categoria", 1);
        values.put("cantidad", menor);
        values.put("imagen", R.drawable.hamburguesa);
        return updateQuery(id_producto, "id", "producto", values);
    }

    public boolean updateComidaMas(int id_producto) {
        Cursor c = getCursorQuery("SELECT insumo_producto.uso,insumo.cantidad,insumo.id FROM insumo_producto,insumo WHERE insumo_producto.id_insumo=insumo.id AND insumo_producto.id_producto=" + id_producto);

        int aux;
        c.moveToFirst();
        do {
            aux = c.getInt(1) + c.getInt(0);
            if (aux < 0) {
                return false;
            }
            ContentValues values = new ContentValues();
            values.put("cantidad", aux);
            updateQuery(c.getInt(2), "id", "insumo", values);
        } while (c.moveToNext());
        c = getCursorQuery("SELECT insumo_producto.uso,insumo.cantidad,insumo.id FROM insumo_producto,insumo WHERE insumo_producto.id_insumo=insumo.id AND insumo_producto.id_producto=" + id_producto);

        c.moveToFirst();
        int menor = c.getInt(1) / c.getInt(0);
        int aux2;
        do {
            aux2 = c.getInt(1) / c.getInt(0);
            if (menor > aux2) {
                menor = aux2;
            }
        } while (c.moveToNext());
        ContentValues values = new ContentValues();
        values.put("cantidad", menor);
        return updateQuery(id_producto, "id", "producto", values);
    }
    public boolean updateComida(int id_producto) {
        Cursor c = getCursorQuery("SELECT insumo_producto.uso,insumo.cantidad,insumo.id FROM insumo_producto,insumo WHERE insumo_producto.id_insumo=insumo.id AND insumo_producto.id_producto=" + id_producto);

        c = getCursorQuery("SELECT insumo_producto.uso,insumo.cantidad,insumo.id FROM insumo_producto,insumo WHERE insumo_producto.id_insumo=insumo.id AND insumo_producto.id_producto=" + id_producto);

        c.moveToFirst();
        int menor = c.getInt(1) / c.getInt(0);
        int aux2;
        do {
            aux2 = c.getInt(1) / c.getInt(0);
            if (menor > aux2) {
                menor = aux2;
            }
        } while (c.moveToNext());
        ContentValues values = new ContentValues();
        values.put("cantidad", menor);
        return updateQuery(id_producto, "id", "producto", values);
    }
    public boolean updateComidaMenos(int id_producto) {
        Cursor c = getCursorQuery("SELECT insumo_producto.uso,insumo.cantidad,insumo.id FROM insumo_producto,insumo WHERE insumo_producto.id_insumo=insumo.id AND insumo_producto.id_producto=" + id_producto);


        int aux;
        c.moveToFirst();
        do {
            aux = c.getInt(1) - c.getInt(0);
            if (aux < 0) {
                return false;
            }
            Log.e("CURSOR IMPORTANTE", "Va " + aux + " para el id:" + c.getInt(2));
            ContentValues values = new ContentValues();
            values.put("cantidad", aux);
            updateQuery(c.getInt(2), "id", "insumo", values);
        } while (c.moveToNext());
        c = getCursorQuery("SELECT insumo_producto.uso,insumo.cantidad,insumo.id FROM insumo_producto,insumo WHERE insumo_producto.id_insumo=insumo.id AND insumo_producto.id_producto=" + id_producto);

        c.moveToFirst();
        int menor = c.getInt(1) / c.getInt(0);
        int aux2;
        do {
            Log.e("CURSOR IMPORTANTE", "Va " + aux);
            aux2 = c.getInt(1) / c.getInt(0);
            if (menor > aux2) {
                menor = aux2;
            }
        } while (c.moveToNext());
        ContentValues values = new ContentValues();
        values.put("cantidad", menor);
        return updateQuery(id_producto, "id", "producto", values);
    }

    public void insertInsumo_producto(int id_insumo, int id_producto, int uso) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_insumo", id_insumo);
        values.put("id_producto", id_producto);
        values.put("uso", uso);
        db.insertOrThrow("insumo_producto", null, values);
    }

    public int deleteQuery(String sql) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();
        return db.delete(sql, null, null);
    }

    public void insertUsuario(String nombre, String nick, String tipo, String password) {

        SQLiteDatabase db = baseDatos.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("nick", nick);
        values.put("tipo", tipo);
        values.put("password", password);
        db.insertOrThrow("usuario", null, values);
    }


    public Long insertInsumo(String nombre, int cantidad, int imagen) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("cantidad", cantidad);
        values.put("imagen", imagen);
        return db.insertOrThrow("insumo", null, values);
    }


    public SQLiteDatabase getDb() {
        return baseDatos.getWritableDatabase();
    }


    public Cursor getTransaccion() {
        SQLiteDatabase db = baseDatos.getReadableDatabase();
        String sql = "SELECT * FROM transaccion";
        return db.rawQuery(sql, null);
    }


    public boolean updateData(int id, String estado, String tabla) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("estado", estado);

        String whereClause = String.format("%s=?", "id");
        String[] whereArgs = {"" + id};

        int resultado = db.update(tabla, valores, whereClause, whereArgs);

        return resultado > 0;

    }

    public boolean updateQuery(int id, String nombre_columna, String tabla, ContentValues values) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();


        String whereClause = String.format("%s=?", "id");
        String[] whereArgs = {"" + id};

        int resultado = db.update(tabla, values, whereClause, whereArgs);

        return resultado > 0;

    }

    public boolean updateQueryString(String id, String nombre_columna, String tabla, ContentValues values) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();


        String whereClause = String.format("%s=?", "id");
        String[] whereArgs = {"" + id};

        int resultado = db.update(tabla, values, whereClause, whereArgs);

        return resultado > 0;

    }


}
