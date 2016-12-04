package com.itchihuahuaii.aplicacioncafeteria;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AdaptadorCocina extends RecyclerView.Adapter<AdaptadorCocina.ViewHolder> {
    private final Context contexto;
    private Cursor items;

    private OnItemClickListener escucha;

    interface OnItemClickListener {
        public void onClick(ViewHolder holder, String idAlquiler);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Referencias UI
        public TextView producto;
        public TextView precio;
        ImageView icono;
        Button listo,finalizado;


        public ViewHolder(View v) {
            super(v);
            producto = (TextView) v.findViewById(R.id.nombre_producto_cocina);
            precio=(TextView)v.findViewById(R.id.precio_producto_cocina);
            icono=(ImageView)v.findViewById(R.id.icono_cocina);

            listo = (Button)v.findViewById(R.id.boton_listo_cocina);
            finalizado = (Button)v.findViewById(R.id.boton_finalizado_cocina);
            listo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    items.moveToPosition(getAdapterPosition());
                    Principal p =(Principal)contexto;
                    if(p.datos.updateData(items.getInt(3),"PREPARADO","carrito_detalle")){
                        listo.setBackgroundColor(Color.RED);
                    }
                }
            });
            finalizado.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    items.moveToPosition(getAdapterPosition());
                    Principal p =(Principal)contexto;
                    String estado = p.datos.getData("SELECT estado FROM carrito_detalle WHERE id="+items.getInt(3));
                    if(p.datos.updateData(items.getInt(3),"FIN","carrito_detalle")&& estado.equals("PREPARADO")){
                        Fragment aux =p.getSupportFragmentManager().findFragmentByTag("cocina");
                        Toast.makeText(p, "Pedido finalizado ", Toast.LENGTH_SHORT).show();
                        p.getSupportFragmentManager().beginTransaction().detach(aux).attach(aux).commit();
                    }
                }
            });
        }

    }


    public AdaptadorCocina(Context contexto) {
        this.contexto = contexto;


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_pedidos, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        items.moveToPosition(position);

        String s;
        // 0 nombre de producto
        // 1 Precio del producto
        // 2 Imagen del producto
        // 3 Estado de carrito_detalle
        // Asignación UI

        s = items.getString(0);
        holder.producto.setText(s);

        s = items.getString(1);
        holder.precio.setText(s+" Pesos");

        s = items.getString(2);
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