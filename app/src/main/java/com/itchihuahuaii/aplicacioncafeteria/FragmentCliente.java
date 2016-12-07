package com.itchihuahuaii.aplicacioncafeteria;

import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase donde se ejecuta el login de la aplicacion
 * @author: Aplicacion Cafeteria Sistemas Operativos Moviles
 * @version: 1.0 12/2/2016
 */
public class FragmentCliente extends Fragment {

    private AppBarLayout appBarLayout;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public FragmentCliente(){}

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

        View view = inflater.inflate(R.layout.fragment_fragment_cliente,container,false);
        if(savedInstanceState==null){
            insertarTabs(container);
            viewPager = (ViewPager)view.findViewById(R.id.pager);
            poblarViewPager(viewPager);
            tabLayout.setupWithViewPager(viewPager);
        }

        return view;
    }

    /**
     * Metodo donde se insertar las pestañas al fragment
     * @param container
     */
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
        adapter.addFragment(new FragmentComidas(), getString(R.string.tab_comidas));
        adapter.addFragment(new FragmentBebidas(), getString(R.string.tab_bebidas));
        adapter.addFragment(new FragmentFrituras(), getString(R.string.tab_frituras));
        viewPager.setAdapter(adapter);
    }
    /**
     * Metodo cuando se sale del fragment
     */
    public void onDestroyView() {
        super.onDestroyView();
        appBarLayout.removeView(tabLayout);
    }

    /**
     * Clase donde se añaden las pestañas
     */
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

    /**
     * Metodo cuando se cierra un fragment
     */
    @Override
    public void onDetach() {
        super.onDetach();
        Toast.makeText(getContext(), "Estas saliendo del cliente", Toast.LENGTH_SHORT).show();
        Principal principal = (Principal)getContext();
        Cursor c = principal.datos.getCursorQuery("SELECT carrito_detalle.id, producto.id, producto.id_categoria FROM producto,carrito_detalle,carrito WHERE carrito_detalle.id_carrito=carrito.id AND carrito_detalle.id_producto=producto.id AND carrito.id="+principal.getCarrito());
        if(!c.moveToFirst()){
            return;
        }
        do{
            if(c.getInt(2)==1){
                principal.datos.updateComidaMas(c.getInt(1));
                Log.e("DEVOLUCION","Devolucion Correcta Comida");
            }else {
                ContentValues values = new ContentValues();
                int cantidad = (Integer.parseInt(principal.datos.getData("SELECT cantidad FROM producto WHERE id="+c.getInt(1)))+1);
                values.put("cantidad",cantidad);
                if(principal.datos.updateQuery(c.getInt(1),"id","producto",values)){
                    Log.e("DEVOLUCION","Devolucion Correcta");
                }
            }

            principal.datos.deleteQuery("carrito_detalle WHERE id="+c.getInt(0));

        }while(c.moveToNext());
        principal.datos.deleteQuery("carrito WHERE id="+principal.getCarrito());


    }
}
