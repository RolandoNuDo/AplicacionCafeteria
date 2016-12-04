package com.itchihuahuaii.aplicacioncafeteria;


import android.content.ContentValues;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAddBebida extends Fragment {


    public FragmentAddBebida() {
        // Required empty public constructor
    }

    Button registrar_bebida;
    EditText nombre_bebida, precio_bebida, cantidad_bebida;
    Principal contexto;
    ImageView icono;

    //Reciclador para el tipo 3
    RecyclerView reciclador;
    GridLayoutManager layout;
    AdaptadorInsumo adaptador;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_bebida, container, false);
        contexto = (Principal) getContext();
        icono = (ImageView) view.findViewById(R.id.icono_add_bebida);
        icono.setImageResource(getArguments().getInt("imagen"));
        registrar_bebida = (Button) view.findViewById(R.id.registrar_bebida);
        nombre_bebida = (EditText) view.findViewById(R.id.nombre_bebida);
        precio_bebida = (EditText) view.findViewById(R.id.precio_bebida);
        cantidad_bebida = (EditText) view.findViewById(R.id.cantidad_bebida);
        reciclador = (RecyclerView) view.findViewById(R.id.reciclador);
        if (getArguments().getInt("tipo") == 4) {
            String nombre = getArguments().getString("NOMBRE");
            String precio = getArguments().getString("PRECIO");
            String cantidad = getArguments().getString("CANTIDAD");

            nombre_bebida.setText(nombre);
            precio_bebida.setText(precio);
            cantidad_bebida.setText(cantidad);
        }
        if (getArguments().getInt("tipo") == 2 || getArguments().getInt("tipo") == 6) {
            precio_bebida.setVisibility(View.GONE);
            String nombre = getArguments().getString("NOMBRE");
            String cantidad = getArguments().getString("CANTIDAD");
            nombre_bebida.setText(nombre);
            cantidad_bebida.setText(cantidad);
        } else if (getArguments().getInt("tipo") == 3) {
            contexto.setProducto(Integer.parseInt("" + contexto.datos.insertProducto("temporal", 0, 1, 0, R.drawable.hamburguesa)));
            Log.e("Este es el id", "" + contexto.getProducto());
            cantidad_bebida.setVisibility(View.GONE);
            layout = new GridLayoutManager(contexto, 2);
            reciclador.setLayoutManager(layout);
            adaptador = new AdaptadorInsumo(contexto);
            // 0 nombre de insumo
            // 1 cantidad del insumo
            // 2 imagen de insumo
            adaptador.swapCursor(contexto.datos.getCursorQuery("SELECT nombre,cantidad,imagen,id FROM insumo"));
            reciclador.setAdapter(adaptador);
        }

        registrar_bebida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = nombre_bebida.getText().toString();
                String cantidadAux = cantidad_bebida.getText().toString();
                if ((nombre.equals("") || cantidadAux.equals("") && getArguments().getInt("tipo") != 3)) {
                    Toast.makeText(contexto, "Rellene los campos", Toast.LENGTH_SHORT).show();
                    return;
                }


                // METODO PARA AGREGAR UNA NUEVA BEBIDA O DULCE
                // METODO PARA AGREGAR UNA NUEVA BEBIDA O DULCE
                // METODO PARA AGREGAR UNA NUEVA BEBIDA O DULCE

                if (getArguments().getInt("tipo") == 1) {
                    int cantidad = Integer.parseInt(cantidadAux);
                    if (precio_bebida.getText().toString().equals("")) {
                        Toast.makeText(contexto, "Rellene los campos", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    int precio = Integer.parseInt(precio_bebida.getText().toString());
                    try {

                        if (contexto.datos.insertProducto(nombre, precio, getArguments().getInt("categoria"), cantidad, getArguments().getInt("imagen_defecto")) > -1) {
                            Toast.makeText(contexto, "Se ha dado de alta el producto", Toast.LENGTH_SHORT).show();
                            Fragment aux = contexto.getSupportFragmentManager().findFragmentByTag("admin");
                            contexto.getSupportFragmentManager().beginTransaction().replace(R.id.contenedor_principal, aux).addToBackStack(null).commit();
                        }
                    } catch (Exception e) {
                        Toast.makeText(contexto, "Datos no validos", Toast.LENGTH_SHORT).show();
                    }
                }
                // METODO PARA AGREGAR UNA COMIDA
                // METODO PARA AGREGAR UNA COMIDA
                // METODO PARA AGREGAR UNA COMIDA

                if (getArguments().getInt("tipo") == 3) {
                    if (precio_bebida.getText().toString().equals("")) {
                        Toast.makeText(contexto, "Rellene los campos", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    int precio = Integer.parseInt(precio_bebida.getText().toString());
                    try {
                        if(contexto.datos.updateComida(contexto.getProducto(),nombre,precio)){
                            Toast.makeText(contexto, "Producto correctamente dado de alta", Toast.LENGTH_SHORT).show();
                            Fragment aux = contexto.getSupportFragmentManager().findFragmentByTag("admin");
                            contexto.getSupportFragmentManager().beginTransaction().replace(R.id.contenedor_principal, aux).addToBackStack(null).commit();
                        }

                    } catch (Exception e) {
                        Log.e("Error",""+e);
                        Toast.makeText(contexto, "Datos no validos", Toast.LENGTH_SHORT).show();
                    }
                }
                // METODO PARA AGREGAR UNA INSUMO
                // METODO PARA AGREGAR UNA INSUMO
                // METODO PARA AGREGAR UNA INSUMO
                else if (getArguments().getInt("tipo") == 2) {
                    try {
                        int cantidad = Integer.parseInt(cantidadAux);
                        if (contexto.datos.insertInsumo(nombre, cantidad, getArguments().getInt("imagen_defecto")) > -1) {
                            Toast.makeText(contexto, "Se ha dado de alta el insumo", Toast.LENGTH_SHORT).show();
                            Fragment aux = contexto.getSupportFragmentManager().findFragmentByTag("admin");
                            contexto.getSupportFragmentManager().beginTransaction().replace(R.id.contenedor_principal, aux).addToBackStack(null).commit();
                        }
                    } catch (Exception e) {
                        Toast.makeText(contexto, "Datos no validos", Toast.LENGTH_SHORT).show();
                    }
                }else if (getArguments().getInt("tipo") == 4) {
                    try {
                        int cantidad = Integer.parseInt(cantidadAux);
                        ContentValues values = new ContentValues();
                        values.put("nombre",nombre);
                        values.put("cantidad",cantidad);
                        values.put("precio",Integer.parseInt(precio_bebida.getText().toString()));

                        if (contexto.datos.updateQuery(getArguments().getInt("ID"),"id","producto",values)) {
                            Toast.makeText(contexto, "Se ha actualizado correctamente", Toast.LENGTH_SHORT).show();
                            Fragment aux = contexto.getSupportFragmentManager().findFragmentByTag("admin");
                            contexto.getSupportFragmentManager().beginTransaction().replace(R.id.contenedor_principal, aux).addToBackStack(null).commit();
                        }
                    } catch (Exception e) {
                        Toast.makeText(contexto, "Datos no validos", Toast.LENGTH_SHORT).show();
                    }
                }else if (getArguments().getInt("tipo") == 6) {
                    try {
                        int cantidad = Integer.parseInt(cantidadAux);
                        ContentValues values = new ContentValues();
                        values.put("nombre",nombre);
                        values.put("cantidad",cantidad);

                        if (contexto.datos.updateQuery(getArguments().getInt("ID"),"id","insumo",values)) {
                            Toast.makeText(contexto, "Se ha actualizado correctamente", Toast.LENGTH_SHORT).show();
                            Fragment aux = contexto.getSupportFragmentManager().findFragmentByTag("admin");
                            contexto.getSupportFragmentManager().beginTransaction().replace(R.id.contenedor_principal, aux).addToBackStack(null).commit();
                        }
                    } catch (Exception e) {
                        Toast.makeText(contexto, "Datos no validos", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if(getArguments().getInt("tipo")==3){
            contexto.datos.deleteQuery("producto WHERE id="+contexto.getProducto());
        }
    }
}
