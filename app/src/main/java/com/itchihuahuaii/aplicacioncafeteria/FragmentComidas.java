package com.itchihuahuaii.aplicacioncafeteria;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;


public class FragmentComidas extends Fragment {

    RecyclerView reciclador;
    GridLayoutManager layout;
    AdaptadorComida adaptador;
    FloatingActionButton fab;
    public FragmentComidas() {
        // Required empty public constructor
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
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
        adaptador.swapCursor(ma.datos.getComidasByTipo("COMIDAS"));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ma.getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.contenedor_principal,new FragmentCarrito(),"carrito").commit();
            }
        });
        adaptador.setImageCursor(R.drawable.hamburguesa);
        reciclador.setAdapter(adaptador);
        return view;
    }


}
