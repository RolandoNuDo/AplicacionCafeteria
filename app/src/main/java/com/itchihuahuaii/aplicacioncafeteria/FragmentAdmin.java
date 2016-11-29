package com.itchihuahuaii.aplicacioncafeteria;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAdmin extends Fragment {


    public FragmentAdmin() {
        // Required empty public constructor
    }

    FloatingActionButton add_comida,add_bebida,add_dulce,add_insumo,edit_bebida,edit_insumo;
    Principal contexto;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_admin, container, false);
        contexto = (Principal)getContext();
        add_comida = (FloatingActionButton)view.findViewById(R.id.add_comida);
        add_bebida = (FloatingActionButton)view.findViewById(R.id.add_bebida);
        add_dulce = (FloatingActionButton)view.findViewById(R.id.add_dulce);
        add_insumo = (FloatingActionButton)view.findViewById(R.id.add_insumo);
        edit_bebida = (FloatingActionButton)view.findViewById(R.id.edit_bebida);
        edit_insumo = (FloatingActionButton)view.findViewById(R.id.edit_insumo);

        add_bebida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment aux = new FragmentAddBebida();
                Bundle bundle = new Bundle();
                bundle.putInt("imagen",R.drawable.add_bebida2);
                bundle.putInt("categoria",2);
                bundle.putInt("tipo",1);
                bundle.putInt("imagen_defecto",R.drawable.cafe);
                aux.setArguments(bundle);
                contexto.getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.contenedor_principal,aux,"add_bebida").commit();

            }
        });
        add_dulce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment aux = new FragmentAddBebida();
                Bundle bundle = new Bundle();
                bundle.putInt("imagen",R.drawable.add_dulce);
                bundle.putInt("categoria",3);
                bundle.putInt("tipo",1);
                bundle.putInt("imagen_defecto",R.drawable.chocolate);
                aux.setArguments(bundle);
                contexto.getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.contenedor_principal,aux,"add_bebida").commit();

            }
        });
        add_insumo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment aux = new FragmentAddBebida();
                Bundle bundle = new Bundle();
                bundle.putInt("imagen",R.drawable.add_insumo);
                bundle.putInt("categoria",3);
                bundle.putInt("imagen_defecto",R.drawable.pan);
                bundle.putInt("tipo",2);
                aux.setArguments(bundle);
                contexto.getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.contenedor_principal,aux,"add_bebida").commit();

            }
        });
        add_comida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment aux = new FragmentAddBebida();
                Bundle bundle = new Bundle();
                bundle.putInt("imagen",R.drawable.add_comida);
                bundle.putInt("categoria",2);
                bundle.putInt("tipo",3);
                bundle.putInt("imagen_defecto",R.drawable.hamburguesa);
                aux.setArguments(bundle);
                contexto.getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.contenedor_principal,aux,"add_bebida").commit();

            }
        });
        edit_bebida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment aux = new FragmentEditBebida();
                Bundle bundle = new Bundle();
                aux.setArguments(bundle);
                contexto.getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.contenedor_principal,aux,"edit_bebida").commit();

            }
        });
        return view;
    }

}
