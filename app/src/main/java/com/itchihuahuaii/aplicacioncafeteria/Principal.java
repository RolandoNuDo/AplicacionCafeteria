package com.itchihuahuaii.aplicacioncafeteria;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Clase principal de la aplicacion
 * @author: Aplicacion Cafeteria Sistemas Operativos Moviles
 * @version: 1.0 12/2/2016
 */
public class Principal extends AppCompatActivity {

    Consultas datos;

    public int getUsuario() {
        return usuario;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
    }

    public int getProducto() {
        return producto;
    }

    public void setProducto(int producto) {
        this.producto = producto;
    }

    public int getCarrito() {
        return carrito;
    }

    public void setCarrito(int carrito) {
        this.carrito = carrito;
    }

    public int getCarrito_detalle() {
        return carrito_detalle;
    }

    public void setCarrito_detalle(int carrito_detalle) {
        this.carrito_detalle = carrito_detalle;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }


    private int usuario;
    private int producto;
    private int carrito;
    private int carrito_detalle;
    private String tipo;

    NotificationManager nm;
    Notification notif;
    static String ns = Context.NOTIFICATION_SERVICE;

    /**
     * Metodo principal de la activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        datos= Consultas.obtenerInstancia(getApplicationContext());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        usuario = Integer.parseInt(getIntent().getExtras().getString("USUARIO"));
        tipo = getIntent().getExtras().getString("TIPO");
        carrito = Integer.parseInt(datos.insertCarrito(getUsuario(),""+ Calendar.getInstance().getTime()));
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
        Cursor c;

        if(tipo.equals("CLIENTE")){
            c=datos.getCursorQuery("SELECT producto.nombre FROM carrito_detalle,carrito,usuario,producto WHERE" +
                    " carrito_detalle.id_producto=producto.id AND carrito_detalle.id_carrito=carrito.id AND carrito.id_usuario=usuario.id AND usuario.id="+getUsuario()+" AND carrito_detalle.estado='PREPARADO'");
            DatabaseUtils.dumpCursor(c);
            Notification.Builder builder = new Notification.Builder(getApplicationContext());
            NotificationManager notifManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            if(c.moveToFirst()){
                notifManager.notify(1,getInboxStyle(builder,c));
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.contenedor_principal,new FragmentCliente(),"cliente").commit();
        }else if(tipo.equals("COCINA")){
            getSupportFragmentManager().beginTransaction().replace(R.id.contenedor_principal,new FragmentCocina(),"cocina").commit();
        }else if(tipo.equals("ADMIN")){
            getSupportFragmentManager().beginTransaction().replace(R.id.contenedor_principal,new FragmentAdmin(),"admin").commit();
        }

    }

    private Notification getInboxStyle(Notification.Builder builder,Cursor c) {

        Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.cafe);
        builder
                .setContentTitle("Reduced Inbox title")
                .setContentText("Reduced content")
                .setContentInfo("Info")
                .setSmallIcon(R.drawable.cafe)
                .setLargeIcon(bitmap);

        Notification.InboxStyle n = new Notification.InboxStyle(builder)
                .setBigContentTitle("Mis Pedidos Pendientes: ");
        if(c.moveToFirst()){
            do{
                n.addLine("Su "+c.getString(0)+" esta listo.");
            }
            while(c.moveToNext());
        }



        return n.build();
    }

}
