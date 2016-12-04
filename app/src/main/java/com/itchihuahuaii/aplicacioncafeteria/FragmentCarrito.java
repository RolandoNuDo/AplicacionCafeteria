package com.itchihuahuaii.aplicacioncafeteria;

import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
/**
 * Clase donde se ejecuta el login de la aplicacion
 * @author: Aplicacion Cafeteria Sistemas Operativos Moviles
 * @version: 1.0 12/2/2016
 */
public class FragmentCarrito extends Fragment {

    RecyclerView reciclador;
    LinearLayoutManager layout;
    AdaptadorCarrito adaptador;

    TextView total;
    Button pagar;


    Principal ma;

    public FragmentCarrito() {
        // Required empty public constructor
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    /**
     * Metodo con la vista del fragment
     * @param inflater Inflater
     * @param container contenedor
     * @param savedInstanceState instancia
     * @return Devuelve la vista del fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.vista_carrito,container,false);

        total = (TextView)view.findViewById(R.id.total_carrito);
        pagar = (Button)view.findViewById(R.id.pagar_carrito);

        reciclador =(RecyclerView)view.findViewById(R.id.reciclador);
        layout = new LinearLayoutManager(getActivity());
        reciclador.setLayoutManager(layout);
        adaptador = new AdaptadorCarrito(getContext());
        ma = (Principal) getActivity();
        String suma = ma.datos.getData("SELECT SUM(producto.precio) FROM producto,carrito,carrito_detalle " +
                "WHERE carrito_detalle.id_producto=producto.id AND carrito_detalle.id_carrito=carrito.id AND carrito.id="+ma.getCarrito());
        if(suma==null){
            total.setText("Aun no tiene articulos");
        }else {
            total.setText("Tu total es: "+suma+ " $ pesos ");
        }

        pagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fecha = Calendar.getInstance().getTime().toString();
                Cursor c = ma.datos.getCursorQuery("SELECT producto.id , carrito_detalle.id FROM producto,carrito,carrito_detalle " +
                        "WHERE carrito_detalle.id_producto=producto.id AND carrito_detalle.id_carrito=carrito.id AND carrito.id="+ma.getCarrito());
                DatabaseUtils.dumpCursor(c);
                if(!c.moveToFirst()){
                    Toast.makeText(getContext(), "Su carrito esta vacio", Toast.LENGTH_SHORT).show();
                    return;
                }
                do {
                    String id = c.getString(0);
                    String cat = ma.datos.getData("SELECT id_categoria FROM producto WHERE id="+id);
                    if(cat.equals("1")){
                        ma.datos.updateData(Integer.parseInt(c.getString(1)),"COCINAR","carrito_detalle");
                    }
                }while(c.moveToNext());
                ma.getSupportFragmentManager().beginTransaction().replace(R.id.contenedor_principal,new FragmentFinal(),"final").addToBackStack(null).commit();
                ma.setCarrito(Integer.parseInt(ma.datos.insertCarrito(ma.getUsuario(),""+Calendar.getInstance().getTime())));

                DatabaseUtils.dumpCursor(ma.datos.getCursorQuery("SELECT * FROM carrito_detalle"));
                  /*
                    System.out.println("Usuario: 1"+Integer.parseInt(ma.datos.getData("SELECT id FROM producto WHERE nombre='"+c.getString(0)+"'"))+" estado: "+estado+" fecha "+fecha);
                    ma.datos.insertTransaccion(1,Integer.parseInt(ma.datos.getData("SELECT id FROM producto WHERE nombre='"+c.getString(0)+"'")),estado,fecha);
                    if(!c.moveToNext()){
                        i=c.getCount();
                    }
                }
                ma.datos.deleteQuery("carrito");
                ma.getSupportFragmentManager().beginTransaction().replace(R.id.contenedor_principal,new FragmentFinal()).addToBackStack(null).commit();
            */
        }});

        adaptador.swapCursor(ma.datos.getCursorQuery("SELECT producto.nombre, producto.precio , producto.imagen ,carrito_detalle.id FROM producto,carrito,carrito_detalle " +
                "WHERE carrito_detalle.id_producto=producto.id AND carrito_detalle.id_carrito=carrito.id AND carrito.id="+ma.getCarrito()));
        reciclador.setAdapter(adaptador);
        return view;
    }


}