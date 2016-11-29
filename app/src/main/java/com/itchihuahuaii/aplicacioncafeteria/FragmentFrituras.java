package com.itchihuahuaii.aplicacioncafeteria;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class FragmentFrituras extends Fragment {

    RecyclerView reciclador;
    GridLayoutManager layout;
    AdaptadorComida adaptador;
    FloatingActionButton fab;
    public FragmentFrituras() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.vista_comidas,container,false);
        fab = (FloatingActionButton)view.findViewById(R.id.fab);
        reciclador =(RecyclerView)view.findViewById(R.id.reciclador);
        layout = new GridLayoutManager(getActivity(),2);
        reciclador.setLayoutManager(layout);
        adaptador = new AdaptadorComida(getContext());
        final Principal ma = (Principal) getActivity();
        adaptador.swapCursor(ma.datos.getComidasByTipo("FRITURAS"));
        adaptador.setImageCursor(R.drawable.chocolate);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ma.getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.contenedor_principal,new FragmentCarrito(),"carrito").commit();
            }
        });
        reciclador.setAdapter(adaptador);
        return view;
    }

}
