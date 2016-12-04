package com.itchihuahuaii.aplicacioncafeteria;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Clase donde se ejecuta el login de la aplicacion
 * @author: Aplicacion Cafeteria Sistemas Operativos Moviles
 * @version: 1.0 12/2/2016
 */
public class FragmentEditBebida extends Fragment {

    RecyclerView reciclador;
    GridLayoutManager layout;
    AdaptadorEditBebida adaptador;

    public FragmentEditBebida() {

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
        View view =  inflater.inflate(R.layout.fragment_edit_bebida, container, false);

        reciclador =(RecyclerView)view.findViewById(R.id.reciclador);
        layout = new GridLayoutManager(getActivity(),2);
        reciclador.setLayoutManager(layout);
        adaptador = new AdaptadorEditBebida(getContext(),getArguments().getInt("TIPO"));
        Principal ma = (Principal) getActivity();
        // 0 nombre de producto
        // 1 Precio del producto
        // 2 cantidad del producto
        // 3 imagen de producto
        Cursor c;
        if(getArguments().getInt("TIPO")==1){
            c = ma.datos.getCursorQuery("SELECT producto.nombre, producto.precio, producto.cantidad,producto.imagen,producto.id " +
                    "FROM producto");
        }else if(getArguments().getInt("TIPO")==2){
            c = ma.datos.getCursorQuery("SELECT insumo.nombre, insumo.cantidad,insumo.imagen,insumo.id " +
                    "FROM insumo");
        }else {
            c = null;
        }

        adaptador.swapCursor(c);
        reciclador.setAdapter(adaptador);
        return view;
    }

}
