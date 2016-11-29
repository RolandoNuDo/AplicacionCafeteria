package com.itchihuahuaii.aplicacioncafeteria;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.DatabaseUtils;
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
        if(tipo.equals("CLIENTE")){
            getSupportFragmentManager().beginTransaction().replace(R.id.contenedor_principal,new FragmentCliente(),"cliente").commit();
        }else if(tipo.equals("COCINA")){
            getSupportFragmentManager().beginTransaction().replace(R.id.contenedor_principal,new FragmentCocina(),"cocina").commit();
        }else if(tipo.equals("ADMIN")){
            getSupportFragmentManager().beginTransaction().replace(R.id.contenedor_principal,new FragmentAdmin(),"admin").commit();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
