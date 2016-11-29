package com.itchihuahuaii.aplicacioncafeteria;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class FragmentEditBebida extends Fragment {

    RecyclerView reciclador;
    GridLayoutManager layout;
    AdaptadorEditBebida adaptador;

    public FragmentEditBebida() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_edit_bebida, container, false);

        reciclador =(RecyclerView)view.findViewById(R.id.reciclador);
        layout = new GridLayoutManager(getActivity(),2);
        reciclador.setLayoutManager(layout);
        adaptador = new AdaptadorEditBebida(getContext());
        Principal ma = (Principal) getActivity();
        // 0 nombre de producto
        // 1 Precio del producto
        // 2 cantidad del producto
        // 3 imagen de producto
        Cursor c = ma.datos.getCursorQuery("SELECT producto.nombre, producto.precio, producto.cantidad,producto.imagen,producto.id " +
                "FROM producto");
        adaptador.swapCursor(c);
        reciclador.setAdapter(adaptador);
        return view;
    }

}
