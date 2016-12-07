package com.itchihuahuaii.aplicacioncafeteria;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAdmin extends Fragment {

    private AppBarLayout appBarLayout;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public FragmentAdmin() {
        // Required empty public constructor
    }

    FloatingActionButton add_comida,add_bebida,add_dulce,add_insumo,edit_bebida,edit_insumo;
    Principal contexto;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_fragment_cliente, container, false);
        if(savedInstanceState==null){
            insertarTabs(container);
            viewPager = (ViewPager)view.findViewById(R.id.pager);
            poblarViewPager(viewPager);
            tabLayout.setupWithViewPager(viewPager);
        }

/*
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
                bundle.putInt("TIPO",1);
                aux.setArguments(bundle);
                contexto.getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.contenedor_principal,aux,"edit_bebida").commit();

            }
        });
        edit_insumo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment aux = new FragmentEditBebida();
                Bundle bundle = new Bundle();
                bundle.putInt("TIPO",2);
                aux.setArguments(bundle);
                contexto.getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.contenedor_principal,aux,"edit_bebida").commit();

            }
        });
        */
        return view;
    }
    private void insertarTabs(ViewGroup container) {
        View padre=(View)container.getParent();
        appBarLayout =(AppBarLayout)padre.findViewById(R.id.appBar);
        tabLayout=new TabLayout(getActivity());
        tabLayout.setTabTextColors(Color.parseColor("#FFFFFF"),Color.parseColor("#FFFFFF"));
        appBarLayout.addView(tabLayout);
    }

    /**
     * Metodo donde se asignan los fragment que tendran las comidas
     * @param viewPager ViewPager declarado en el layout
     */
    private void poblarViewPager(ViewPager viewPager) {
        AdaptadorSecciones adapter = new AdaptadorSecciones(getChildFragmentManager());
        Fragment aux = new FragmentEditBebida();
        Bundle bundle = new Bundle();
        bundle.putInt("TIPO",1);
        aux.setArguments(bundle);
        Fragment aux2 = new FragmentEditBebida();
        bundle = new Bundle();
        bundle.putInt("TIPO",2);
        aux2.setArguments(bundle);
        adapter.addFragment(aux, "Editar comida");
        adapter.addFragment(aux2, "Editar insumo");
        viewPager.setAdapter(adapter);
    }
    public void onDestroyView() {
        super.onDestroyView();
        appBarLayout.removeView(tabLayout);
    }
    public class AdaptadorSecciones extends FragmentStatePagerAdapter {
        private final List<Fragment> fragmentos = new ArrayList<>();
        private final List<String> titulosFragmentos = new ArrayList<>();

        /**
         * Constructor
         * @param fm FragmentManager para hacer las transiciones
         */
        public AdaptadorSecciones(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return fragmentos.get(position);
        }

        @Override
        public int getCount() {
            return fragmentos.size();
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        public void addFragment(android.support.v4.app.Fragment fragment, String title) {
            fragmentos.add(fragment);
            titulosFragmentos.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titulosFragmentos.get(position);
        }
    }

}
