package com.includeazzu.azzuagenda;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by Jorge Azzu Franco on 3/5/2018.
 */

public class ContactoAdapter extends RecyclerView.Adapter<ContactoAdapter.ContactoViewHolder> {

    private ArrayList<Contacto> contactos;

    @Override
    public ContactoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_contacto,parent,false);
        return (new ContactoViewHolder(v));
    }

    @Override
    public void onBindViewHolder(ContactoAdapter.ContactoViewHolder holder, int position) {
        holder.nombre.setText(contactos.get(position).getNombre());
        holder.img.setImageResource(contactos.get(position).getImg());


    }
    @Override
    public int getItemCount() {
        return contactos.size();
    }

    public static class ContactoViewHolder extends RecyclerView.ViewHolder{
        CardView card;
        TextView nombre;
        ImageView img;
        public ContactoViewHolder(View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.card_view);
            nombre = itemView.findViewById(R.id.nomContacto);
            img = itemView.findViewById(R.id.img);
        }
    }

    public ContactoAdapter(ArrayList<Contacto> contactos){
        this.contactos = contactos;
    }
}
