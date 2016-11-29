package com.itchihuahuaii.aplicacioncafeteria;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AdaptadorInsumo extends RecyclerView.Adapter<AdaptadorInsumo.ViewHolder> {
    private final Context contexto;
    private Cursor items;

    private OnItemClickListener escucha;

    interface OnItemClickListener {
        public void onClick(ViewHolder holder, String idAlquiler);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Referencias UI
        public TextView nombre_bebida, precio_bebida, cantidad_bebida;
        ImageView icono;
        LinearLayout carta;
        Button boton;
        EditText uso;
        boolean carta_click = false;


        public ViewHolder(View v) {
            super(v);
            nombre_bebida = (TextView) v.findViewById(R.id.nombre_edt_bebida);
            cantidad_bebida = (TextView) v.findViewById(R.id.cantidad_edt_bebida);
            icono = (ImageView) v.findViewById(R.id.icono_edt_bebida);
            carta = (LinearLayout) v.findViewById(R.id.carta);
            boton = (Button)v.findViewById(R.id.boton);
            uso = (EditText)v.findViewById(R.id.uso);

            final Principal principal = (Principal)contexto;

            boton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    items.moveToPosition(getAdapterPosition());
                    if (carta_click) {
                        carta.setBackgroundColor(Color.WHITE);
                        boton.setBackgroundColor(Color.WHITE);
                        Log.e("Cantidad de filas: ",""+principal.datos.deleteQuery("insumo_producto WHERE id_producto="+principal.getProducto()+" AND id_insumo="+items.getInt(3)));
                        carta_click = false;
                        boton.setText("+");
                        carta.setBackgroundColor(Color.WHITE);
                        boton.setBackgroundColor(Color.WHITE);
                    } else {
                        if(uso.getText().toString().equals("") || Integer.parseInt(uso.getText().toString())<0){
                            Toast.makeText(principal, "Rellene el campo de cantidad", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        principal.datos.insertInsumo_producto(items.getInt(3),principal.getProducto(),Integer.parseInt(uso.getText().toString()));
                        DatabaseUtils.dumpCursor(principal.datos.getCursorQuery("SELECT * FROM insumo_producto"));
                        carta.setBackgroundColor(Color.GRAY);
                        carta_click = true;
                        boton.setBackgroundColor(Color.GRAY);
                        boton.setText("-");
                    }

                }
            });
        }

    }


    public AdaptadorInsumo(Context contexto) {
        this.contexto = contexto;


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_edt_insumo, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        items.moveToPosition(position);

        String s;
        // 0 nombre de producto
        // 1 Cantidad del producto
        // 2 imagen de producto
        // 3 id de insumo
        s = items.getString(0);
        holder.nombre_bebida.setText(s);

        s = items.getString(1);

        holder.cantidad_bebida.setText("Cantidad: " + s);

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



