package com.itchihuahuaii.aplicacioncafeteria;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class AdaptadorEditBebida extends RecyclerView.Adapter<AdaptadorEditBebida.ViewHolder> {
    private final Context contexto;
    private Cursor items;

    private OnItemClickListener escucha;

    interface OnItemClickListener {
        public void onClick(ViewHolder holder, String idAlquiler);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Referencias UI
        public TextView nombre_bebida,precio_bebida,cantidad_bebida;
        ImageView icono;
        LinearLayout carta;

        public ViewHolder(View v) {
            super(v);
            nombre_bebida = (TextView) v.findViewById(R.id.nombre_edt_bebida);
            precio_bebida = (TextView) v.findViewById(R.id.precio_edt_bebida);
            cantidad_bebida = (TextView) v.findViewById(R.id.cantidad_edt_bebida);
            icono = (ImageView) v.findViewById(R.id.icono_edt_bebida);
            carta = (LinearLayout)v.findViewById(R.id.carta);

            carta.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    items.moveToPosition(getAdapterPosition());
                    Principal principal = (Principal)contexto;
                    Fragment aux = new FragmentAddBebida();
                    Bundle bundle = new Bundle();
                    bundle.putInt("imagen",R.drawable.add_bebida2);
                    bundle.putInt("categoria",2);
                    bundle.putInt("tipo",4);
                    bundle.putInt("imagen_defecto",R.drawable.cafe);

                    bundle.putString("NOMBRE",items.getString(0));
                    bundle.putString("PRECIO",items.getString(1));
                    bundle.putString("CANTIDAD",items.getString(2));
                    bundle.putInt("ID",items.getInt(4));
                    aux.setArguments(bundle);
                    principal.getSupportFragmentManager().beginTransaction().replace(R.id.contenedor_principal,aux,"add_bebida").addToBackStack(null).commit();

                }
            });

        }

    }


    public AdaptadorEditBebida(Context contexto) {
        this.contexto = contexto;


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_edt_bebida, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        items.moveToPosition(position);

        String s;
        // 0 nombre de producto
        // 1 Precio del producto
        // 2 cantidad del producto
        // 3 imagen de producto
        // 4 id de producto
        // 5 id_categoria de producto

        s = items.getString(0);
        holder.nombre_bebida.setText(s);

        s = items.getString(1);
        holder.precio_bebida.setText(s + " Pesos");

        s = items.getString(2);
        holder.cantidad_bebida.setText("Cantidad: "+s);

        s = items.getString(3);
        holder.icono.setImageResource(Integer.parseInt(s));

    }

    @Override
    public int getItemCount() {
        if (items != null)
            return items.getCount();
        return 0;
    }

    public void swapCursor(Cursor nuevoCursor) {
        if (nuevoCursor != null) {
            items = nuevoCursor;
            notifyDataSetChanged();
        }
    }

    public Cursor getCursor() {
        return items;
    }
}


