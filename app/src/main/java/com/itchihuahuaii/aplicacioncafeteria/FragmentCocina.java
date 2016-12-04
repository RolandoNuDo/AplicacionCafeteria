package com.itchihuahuaii.aplicacioncafeteria;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Clase donde se ejecuta el login de la aplicacion
 * @author: Aplicacion Cafeteria Sistemas Operativos Moviles
 * @version: 1.0 12/2/2016
 */
public class FragmentCocina extends Fragment {

    RecyclerView reciclador;
    LinearLayoutManager layout;
    AdaptadorCocina adaptador;

    public FragmentCocina() {
        // Required empty public constructor
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
        View view = inflater.inflate(R.layout.fragment_cocina,container,false);

        reciclador =(RecyclerView)view.findViewById(R.id.reciclador);
        layout = new LinearLayoutManager(getActivity());
        reciclador.setLayoutManager(layout);

        adaptador = new AdaptadorCocina(getContext());
        Principal ma = (Principal) getActivity();
        adaptador.swapCursor(ma.datos.getCursorQuery("SELECT producto.nombre ,producto.precio,producto.imagen ,carrito_detalle.id" +
                " FROM carrito_detalle,producto WHERE carrito_detalle.id_producto=producto.id" +
                " AND (carrito_detalle.estado='COCINAR' OR carrito_detalle.estado='PREPARADO') "));
        reciclador.setAdapter(adaptador);
        return view;
    }

}
