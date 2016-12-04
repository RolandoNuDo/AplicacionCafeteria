package com.itchihuahuaii.aplicacioncafeteria;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Fragment que muestra las bebidas
 * @author: Aplicacion Cafeteria Sistemas Operativos Moviles
 * @version: 1.0 12/2/2016
 */
public class FragmentBebidas extends Fragment {

    RecyclerView reciclador;
    GridLayoutManager layout;
    AdaptadorComida adaptador;
    FloatingActionButton fab;

    public FragmentBebidas() {
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
        View view = inflater.inflate(R.layout.vista_comidas,container,false);

        reciclador =(RecyclerView)view.findViewById(R.id.reciclador);
        fab = (FloatingActionButton)view.findViewById(R.id.fab);

        layout = new GridLayoutManager(getActivity(),2);
        reciclador.setLayoutManager(layout);
        adaptador = new AdaptadorComida(getContext());
        final Principal ma = (Principal) getActivity();
        adaptador.swapCursor(ma.datos.getComidasByTipo("BEBIDAS"));
        adaptador.setImageCursor(R.drawable.cafe);
        reciclador.setAdapter(adaptador);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ma.getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.contenedor_principal,new FragmentCarrito(),"carrito").commit();
            }
        });
        return view;
    }

}
