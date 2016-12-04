package com.itchihuahuaii.aplicacioncafeteria;

/**
 * Clase donde se realizan todas las operaciones de la base de datos
 * @author: Luis Fernando Gallegos
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

    /**
     * Constructor vacio
     */
    private Consultas() {
    }

    /**
     *
     * @param contexto De la aplicacion
     * @return Devuelve la base de datos
     */
    public static Consultas obtenerInstancia(Context contexto) {
        if (baseDatos == null) {
            baseDatos = new BaseDatos(contexto);
        }
        return instancia;
    }

    /**
     * Metodo para regresar los productos
     * @return Devuelve el cursor con todos los productos
     */
    public Cursor getProducto() {
        SQLiteDatabase db = baseDatos.getReadableDatabase();
        String sql = "SELECT * FROM producto";
        return db.rawQuery(sql, null);
    }

    /**
     * Actualiza la base de datos
     */
    public void renovar() {
        baseDatos.onUpgrade(baseDatos.getWritableDatabase(), 1, 2);
    }

    /**
     * Metodo que obtiene todos los productos de determinado tipo
     * @param tipo El tipo del producto que es
     * @return Devuelve el cursor con la consulta
     */
    public Cursor getComidasByTipo(String tipo) {
        SQLiteDatabase db = baseDatos.getReadableDatabase();
        String sql = "SELECT producto.nombre, producto.precio ,producto.cantidad, producto.imagen,producto.id_categoria FROM producto,categoria WHERE " +
                "producto.id_categoria=categoria.id AND categoria.nombre='" + tipo + "'";
        return db.rawQuery(sql, null);
    }

    /**
     * Metodo para obtener un cursor apartir de un String
     * @param sql String con la consulta que se quiere realizar
     * @return Devuelve el cursor con la consulta que se realizo
     */
    public Cursor getCursorQuery(String sql) {
        SQLiteDatabase db = baseDatos.getReadableDatabase();
        return db.rawQuery(sql, null);
    }

    /**
     * Metodo para obtener el primer string de una consulta SQL
     * @param sql String con la consulta que se quiere realizar
     * @return Devuelve el string de la consulta que se realizo
     */
    public String getData(String sql) {
        SQLiteDatabase db = baseDatos.getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);
        if (c.moveToFirst()) {
            return c.getString(0);
        } else {
            return "";
        }

    }

    /**
     * Metodo para insertar un nuevo carrito
     * @param id_usuario id del usuario que usa la aplicacion
     * @param fecha fecha actual en la que se realizo la operacion
     * @return Devuelve el id que resulta del insert
     */
    public String insertCarrito(int id_usuario, String fecha) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_usuario", id_usuario);
        values.put("fecha", fecha);
        return "" + db.insertOrThrow("carrito", null, values);
    }

    /**
     * Metodo para insertar una nueva fila en carrito_detalle
     * @param id_carrito id del carrito actual del usuario
     * @param id_producto id del producto que se compro
     * @param precio_venta precio al cual se vendio el producto
     * @param estado estado que indica si el producto si esta listo o tiene que ser preparado
     * @return Devuelve el id del nuevo insert
     */
    public String insertCarrito_detalle(int id_carrito, int id_producto, int precio_venta, String estado) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();
        ContentValues values = new ContentValues();
        /**
         * Aqui se realiza el procedimiento que quisiera
         * Solo es para saber si se tiene en cuenta o no
         */
        values.put("id_carrito", id_carrito);
        values.put("id_producto", id_producto);
        values.put("precio_venta", precio_venta);
        values.put("estado", estado);
        return "" + db.insertOrThrow("carrito_detalle", null, values);
    }

    /**
     * Metodo para insertar una nueva categoria
     * @param nombre nombre de la categoria
     */
    public void insertCategoria(String nombre) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        db.insertOrThrow("categoria", null, values);
    }

    /**
     * Metodo para insertar una nueva fila en la tabla producto
     * @param nombre nombre del producto
     * @param precio precio del producto
     * @param id_categoria id de la categoria que pertenece el producto
     * @param cantidad cantidad que hay de ese producto
     * @param imagen imagen del producto
     * @return Devuelve el id del insert correspondiente
     */
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

    /**
     * Metodo para actualizar la comida
     * @param id_producto id del producto
     * @param nombre nombre del producto
     * @param precio precio del producto
     * @return devuelve true si se realiza el update correcto
     */
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

    /**
     * Metodo que aumenta en uno la cantidad de un producto tipo comida
     * @param id_producto id del producto
     * @return Devuelve true si se actualiza correctamente
     */
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

    /**
     * Metodo para actualizar un producto tipo comida
     * @param id_producto id del producto
     * @return devuelve true si se actualiza correctamente
     */
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

    /**
     * Metodo para disminuir en uno la cantidad de un producto tipo comida
     * @param id_producto id del producto
     * @return Devuelve un true si se realizo correctamente la actualizacion
     */
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

    /**
     * Metodo para insertar una nueva fila en insumo_producto
     * @param id_insumo id del insumo
     * @param id_producto id del producto de tipo comida
     * @param uso uso de insumo para el determinado producto
     */
    public void insertInsumo_producto(int id_insumo, int id_producto, int uso) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_insumo", id_insumo);
        values.put("id_producto", id_producto);
        values.put("uso", uso);
        db.insertOrThrow("insumo_producto", null, values);
    }

    /**
     * Metodo para borrar una instruccion SQL
     * @param sql String que contiene la informacion a borrar
     * @return Devuelve la cantidad de filas afectadas
     */
    public int deleteQuery(String sql) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();
        return db.delete(sql, null, null);
    }

    /**
     * Metodo para insertar un nuevo usuario
     * @param nombre nombre del usuario
     * @param nick nick del usuario
     * @param tipo tipo del usuario
     * @param password password del usuario
     */
    public void insertUsuario(String nombre, String nick, String tipo, String password) {

        SQLiteDatabase db = baseDatos.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("nick", nick);
        values.put("tipo", tipo);
        values.put("password", password);
        db.insertOrThrow("usuario", null, values);
    }

    /**
     * Metodo para insertar un nuevo insumo
     * @param nombre nombre del insumo
     * @param cantidad cantidad del insumo
     * @param imagen imagen del insumo
     * @return Devuelve el id del nuevo insumo
     */
    public Long insertInsumo(String nombre, int cantidad, int imagen) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("cantidad", cantidad);
        values.put("imagen", imagen);
        return db.insertOrThrow("insumo", null, values);
    }

    /**
     * Metodo para obtener la base de datos escribible
     * @return Devuelve la base de datos
     */
    public SQLiteDatabase getDb() {
        return baseDatos.getWritableDatabase();
    }

    /**
     * Metodo para traer todas las transacciones
     * @return Devuelve un cursor con las transacciones
     */
    public Cursor getTransaccion() {
        SQLiteDatabase db = baseDatos.getReadableDatabase();
        String sql = "SELECT * FROM transaccion";
        return db.rawQuery(sql, null);
    }

    /**
     * Metodo para actualizar la informacion de la base de datos
     * @param id id del registro
     * @param estado estado del producto
     * @param tabla tabla afectada
     * @return Devuelve un true si se realizo correctamente
     */

    public boolean updateData(int id, String estado, String tabla) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("estado", estado);

        String whereClause = String.format("%s=?", "id");
        String[] whereArgs = {"" + id};

        int resultado = db.update(tabla, valores, whereClause, whereArgs);

        return resultado > 0;

    }

    /**
     * Metodo para actualizar una tabla
     * @param id id del registro
     * @param nombre_columna nombre de la columna
     * @param tabla tabla afectada
     * @param values valores
     * @return Devuelve true si se actualizo correctamente
     */
    public boolean updateQuery(int id, String nombre_columna, String tabla, ContentValues values) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();


        String whereClause = String.format("%s=?", "id");
        String[] whereArgs = {"" + id};

        int resultado = db.update(tabla, values, whereClause, whereArgs);

        return resultado > 0;

    }

    /**
     * Metodo para actualizar una tabla
     * @param id id del registro
     * @param nombre_columna nombre de la columna
     * @param tabla tabla afectada
     * @param values valores
     * @return Devuelve true si se actualizo correctamente
     */
    public boolean updateQueryString(String id, String nombre_columna, String tabla, ContentValues values) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();


        String whereClause = String.format("%s=?", "id");
        String[] whereArgs = {"" + id};

        int resultado = db.update(tabla, values, whereClause, whereArgs);

        return resultado > 0;

    }


}
