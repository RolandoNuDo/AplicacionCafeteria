package com.itchihuahuaii.aplicacioncafeteria;

import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * Clase donde se ejecuta el login de la aplicacion
 * @author: Aplicacion Cafeteria Sistemas Operativos Moviles
 * @version: 1.0 12/2/2016
 */

public class LoginInicio extends AppCompatActivity {

    EditText pass,nick;
    Button boton;
    public Consultas datos;

    /**
     * Metodo donde se crea la activity
     * @param savedInstanceState Bundle inicial
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        pass =(EditText)findViewById(R.id.pass);
        nick = (EditText)findViewById(R.id.nick);
        boton=(Button)findViewById(R.id.boton);

        getApplicationContext().deleteDatabase("cafeteria.db");

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String n1 = nick.getText().toString();
                String p1= pass.getText().toString();
                String tipo = datos.getData("SELECT tipo FROM usuario WHERE nick='"+n1+"' AND password='"+p1+"'");
                if(!tipo.equals("")){
                    Intent intent = new Intent(getApplicationContext(),Principal.class);
                    String usuario = datos.getData("SELECT id FROM usuario WHERE nick='"+n1+"'");
                    Cursor c = datos.getCursorQuery("SELECT id FROM producto WHERE id_categoria=1");
                    c.moveToFirst();
                    do{
                        datos.updateComida(c.getInt(0));
                    }while(c.moveToNext());

                    intent.putExtra("USUARIO",usuario);
                    intent.putExtra("TIPO",tipo);
                    startActivity(intent);

                    Toast.makeText(LoginInicio.this, "Bienvenido "+nick.getText(), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(LoginInicio.this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                }

            }
        });


        datos= Consultas.obtenerInstancia(getApplicationContext());
        HttpURLConnection urlConnection = null;

        List<Cliente> listaPost = new ArrayList<>();

        try {

            URL url = new URL("http://beta.json-generator.com/api/json/get/4y95ujCZM");
            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setRequestMethod("GET");

            int reponse = urlConnection.getResponseCode();
            Log.e("TODO","TODO HA SALIDO BIEN");
            if (reponse == HttpURLConnection.HTTP_OK) {

                BufferedReader inStream = new BufferedReader(
                        new InputStreamReader(urlConnection.getInputStream()));

                String inputLine = "";

                StringBuffer buffer = new StringBuffer();

                while ( (inputLine = inStream.readLine()) != null) {
                    buffer.append(inputLine);
                }
                inStream.close();
                Toast.makeText(this, buffer.toString()+"", Toast.LENGTH_SHORT).show();
                Log.e("DSA",buffer.toString());
                JSONArray jsonArray = new JSONArray(buffer.toString());

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonPost = jsonArray.getJSONObject(i);
                    listaPost.add(new Cliente(jsonPost.getInt("id"), jsonPost.getString("nombre"),jsonPost.getString("nick"),jsonPost.getString("tipo"),""+jsonPost.getInt("password")));
                }
                Log.e("TODO","TODO HA SALIDO BIEN");
            } else {
                Log.e("da","ALGO SALIO MAL");

            }

        } catch (Exception e) {
            Log.e("Error",""+e);
        } finally {
            urlConnection.disconnect();
        }



        try{
            for(int i=0;i<listaPost.size();i++){
                datos.insertUsuario(listaPost.get(i).getNombre(),listaPost.get(i).getNick(),listaPost.get(i).getTipo(),listaPost.get(i).getPassword());

            }

            DatabaseUtils.dumpCursor(datos.getCursorQuery("SELECT * FROM usuario"));
            datos.insertUsuario("COCINA","cocina","COCINA","123");
            datos.insertUsuario("CLIENTE","cliente","CLIENTE","123");
            datos.insertUsuario("ADMIN","admin","ADMIN","123");

            datos.insertCategoria("COMIDAS");
            datos.insertCategoria("BEBIDAS");
            datos.insertCategoria("FRITURAS");

            datos.insertProducto("Hamburguesa",14,1,10,R.drawable.hamburguesa);
            datos.insertProducto("Panini",31,1,10,R.drawable.food4);
            datos.insertProducto("Agua",12,2,10,R.drawable.agua);
            datos.insertProducto("Cafe",12,2,10,R.drawable.cafe);
            datos.insertProducto("Yoghurt",11,2,10,R.drawable.yoghurt);
            datos.insertProducto("Chocolate",5,3,10,R.drawable.chocolate);
            datos.insertProducto("Dulce",15,3,10,R.drawable.dulce);

            datos.insertInsumo("Tomate",10,R.drawable.add_insumo);
            datos.insertInsumo("Cebolla",10,R.drawable.add_insumo);
            datos.insertInsumo("Mayonesa",10,R.drawable.add_insumo);

            datos.insertInsumo_producto(1,1,2);
            datos.insertInsumo_producto(2,1,3);
            datos.insertInsumo_producto(3,1,2);
            datos.insertInsumo_producto(2,2,3);
            datos.insertInsumo_producto(3,2,2);

            datos.insertCarrito(1,"11/20/2016");
            datos.insertCarrito(1,"11/21/2016");

            datos.insertCarrito_detalle(1,3,10,"FIN");
            datos.insertCarrito_detalle(1,4,12,"FIN");

            datos.insertCarrito_detalle(2,1,20,"COCINAR");
            datos.insertCarrito_detalle(2,2,20,"COCINAR");

            datos.updateComidaMas(1);
            datos.updateComidaMas(1);
            datos.updateComidaMas(2);
            DatabaseUtils.dumpCursor(datos.getCursorQuery("SELECT * FROM usuario"));
            DatabaseUtils.dumpCursor(datos.getCursorQuery("SELECT * FROM producto WHERE id=1"));

        }catch (Exception e){
            Log.e("ACABO CICLO",""+e);
        }

/*
        DatabaseUtils.dumpCursor(datos.getCursorQuery("SELECT * FROM usuario"));
        DatabaseUtils.dumpCursor(datos.getCursorQuery("SELECT * FROM categoria"));
        DatabaseUtils.dumpCursor(datos.getCursorQuery("SELECT * FROM producto"));
        DatabaseUtils.dumpCursor(datos.getCursorQuery("SELECT * FROM insumo"));
        DatabaseUtils.dumpCursor(datos.getCursorQuery("SELECT * FROM insumo_producto"));
        DatabaseUtils.dumpCursor(datos.getCursorQuery("SELECT * FROM carrito"));
        DatabaseUtils.dumpCursor(datos.getCursorQuery("SELECT * FROM carrito_detalle"));*/
    }


}
