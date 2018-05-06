package com.includeazzu.azzuagenda;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    public void onBindViewHolder(final ContactoAdapter.ContactoViewHolder holder, final int position) {
        holder.nombre.setText(contactos.get(position).getNombre());
        holder.img.setImageResource(contactos.get(position).getImg());

        //Listener del boton favorito
        holder.btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean esFav = contactos.get(position).isFavorito();
                if(esFav){
                    contactos.get(position).setFavorito(false);
                    holder.btnFav.setImageResource(R.drawable.nofav);
                    Log.d("Entra","Es favorito: "+contactos.get(position).isFavorito());
                }
                else{
                    contactos.get(position).setFavorito(true);
                    holder.btnFav.setImageResource(R.drawable.fav);
                    Log.d("Entra","Es favorito: "+contactos.get(position).isFavorito());
                }
            }
        });

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent infoContacto = new Intent(holder.context.getApplicationContext(), infoContactoActivity.class);
                String contactNombre = contactos.get(position).getNombre();
                String contactNum = contactos.get(position).getNumero();
                int contactImg = contactos.get(position).getImg();

                infoContacto.putExtra("nom", contactNombre);
                infoContacto.putExtra("num", contactNum);
                infoContacto.putExtra("img", contactImg);

                view.getContext().startActivity(infoContacto);
            }
        });

    }


    @Override
    public int getItemCount() {
        return contactos.size();
    }

    public static class ContactoViewHolder extends RecyclerView.ViewHolder{
        CardView card;
        TextView nombre;
        ImageView img;
        ImageButton btnFav;
        Context context;
        public ContactoViewHolder(View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.card_view);
            nombre = itemView.findViewById(R.id.nomContacto);
            img = itemView.findViewById(R.id.img);
            btnFav = itemView.findViewById(R.id.btnfav);
            context = itemView.getContext();
        }
    }

    public ContactoAdapter(ArrayList<Contacto> contactos){
        this.contactos = contactos;
    }
}
