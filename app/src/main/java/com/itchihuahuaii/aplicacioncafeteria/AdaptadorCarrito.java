package com.itchihuahuaii.aplicacioncafeteria;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import static com.itchihuahuaii.aplicacioncafeteria.R.drawable.principal;

public class AdaptadorCarrito extends RecyclerView.Adapter<AdaptadorCarrito.ViewHolder> {
    private final Context contexto;
    private Cursor items;


    private OnItemClickListener escucha;

    interface OnItemClickListener {
        public void onClick(ViewHolder holder, String idAlquiler);
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        // Referencias UI
        public TextView nombre;
        public TextView precio;
        ImageView icono;
        Button delete;
        Principal principal;
        public ViewHolder(View v) {
            super(v);
            nombre = (TextView) v.findViewById(R.id.nombre_comida);
            precio=(TextView)v.findViewById(R.id.precio_comida);
            icono = (ImageView)v.findViewById(R.id.icon_carrito_lista);
            delete = (Button)v.findViewById(R.id.borrar_carrito);
            principal = (Principal)contexto;
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    items.moveToPosition(getAdapterPosition());

                    if(principal.datos.deleteQuery("carrito_detalle WHERE id="+items.getInt(3))>0){
                        ContentValues values = new ContentValues();
                        int cantidad = (Integer.parseInt(principal.datos.getData("SELECT cantidad FROM producto WHERE nombre='"+items.getString(0)+"'"))+1);
                        int producto = Integer.parseInt(principal.datos.getData("SELECT id FROM producto WHERE nombre='"+items.getString(0)+"'"));
                        if(1==Integer.parseInt(principal.datos.getData("SELECT id_categoria FROM producto WHERE id="+producto))){
                            principal.datos.updateComidaMas(producto);
                            Fragment aux = principal.getSupportFragmentManager().findFragmentByTag("carrito");
                            principal.getSupportFragmentManager().beginTransaction().detach(aux).attach(aux).commit();

                        }else {
                            values.put("cantidad",cantidad);
                            if(principal.datos.updateQuery(producto,"id","producto",values)){
                                Fragment aux = principal.getSupportFragmentManager().findFragmentByTag("carrito");
                                principal.getSupportFragmentManager().beginTransaction().detach(aux).attach(aux).commit();
                            }
                        }

                    }
                }
            });
        }

    }


    public AdaptadorCarrito(Context contexto) {
        this.contexto = contexto;


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_carrito, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        items.moveToPosition(position);

        String s;

        // nombre del producto del carrito
        s = items.getString(0);
        holder.nombre.setText(s);
        // precio del producto del carrito
        s = items.getString(1);
        holder.precio.setText(s);

        holder.icono.setImageResource(Integer.parseInt(items.getString(2)));


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