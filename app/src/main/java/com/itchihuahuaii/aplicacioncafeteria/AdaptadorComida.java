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
import android.widget.Toast;

public class AdaptadorComida extends RecyclerView.Adapter<AdaptadorComida.ViewHolder> {
    private final Context contexto;
    private Cursor items;
    public int imageCursor;

    private OnItemClickListener escucha;

    public void setImageCursor(int imageCursor) {
        this.imageCursor = imageCursor;
    }

    interface OnItemClickListener {
        public void onClick(ViewHolder holder, String idAlquiler);
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        // Referencias UI
        public TextView nombre;
        public TextView precio,cantidad;
        public ImageView imagen;

        public ViewHolder(View v) {
            super(v);
            nombre = (TextView) v.findViewById(R.id.nombre_comida);
            precio=(TextView)v.findViewById(R.id.precio_comida);
            cantidad=(TextView)v.findViewById(R.id.cantidad_comida);
            imagen = (ImageView)v.findViewById(R.id.icono_comida);

            Button boton = (Button)v.findViewById(R.id.button);
            boton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    items.moveToPosition(getAdapterPosition());
                    Principal pc =(Principal)contexto;
                    String usuario = pc.datos.getData("SELECT id FROM producto WHERE nombre='"+items.getString(0)+"'");

                    if(!pc.datos.insertCarrito_detalle(pc.getCarrito(),Integer.parseInt(usuario),Integer.parseInt(items.getString(1)),"FIN").equals("-1")){
                        ContentValues values = new ContentValues();
                        int disp = (items.getInt(2)-1);
                        if(disp<0){
                            Toast.makeText(pc, "Ya no tenemos disponibles", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(items.getInt(4)==1){
                            if(pc.datos.updateComidaMenos(Integer.parseInt(usuario))){
                                Toast.makeText(pc, "Añadido al carrito", Toast.LENGTH_SHORT).show();

                                Cursor c = pc.datos.getCursorQuery("SELECT id FROM producto WHERE id_categoria=1");
                                c.moveToFirst();
                                do{
                                    pc.datos.updateComida(c.getInt(0));
                                }while (c.moveToNext());
                                Fragment aux = pc.getSupportFragmentManager().findFragmentByTag("cliente");
                                pc.getSupportFragmentManager().beginTransaction().detach(aux).attach(aux).commit();
                                return;
                            }else {
                                Log.e("Algo salio mal","dsads");
                            }
                        }
                        values.put("cantidad",disp);
                        if(pc.datos.updateQuery(Integer.parseInt(usuario),"id","producto",values)){
                            Toast.makeText(contexto, "Haz añadido al carrito", Toast.LENGTH_SHORT).show();
                            Fragment aux = pc.getSupportFragmentManager().findFragmentByTag("cliente");
                            pc.getSupportFragmentManager().beginTransaction().detach(aux).attach(aux).commit();
                        }else{

                        }


                    }

                }
            });
        }

    }


    public AdaptadorComida(Context contexto) {
        this.contexto = contexto;


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_comida, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        items.moveToPosition(position);

        String s;

        // 0 nombre del producto
        // 1 precio del producto
        // 2 cantidad del producto
        // 3 imagen del producto
        // 4 id_categoria producto
        // Asignación UI
        s = items.getString(0);
        holder.nombre.setText(s);

        s = items.getString(1);
        holder.precio.setText(s);

        s = items.getString(2);
        holder.cantidad.setText("Disponibles: "+s);

        holder.imagen.setImageResource(Integer.parseInt(items.getString(3)));

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