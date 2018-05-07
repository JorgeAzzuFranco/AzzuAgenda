package com.includeazzu.azzuagenda;

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

import java.util.ArrayList;

/**
 * Created by Jorge Azzu Franco on 3/5/2018.
 */

public class ContactoAdapter extends RecyclerView.Adapter<ContactoAdapter.ContactoViewHolder> {

    private ArrayList<Contacto> contactos;
    Tab1_Contactos contextT1 = new Tab1_Contactos();

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

    @Override
    public ContactoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_contacto,parent,false);
        return (new ContactoViewHolder(v));
    }

    @Override
    public int getItemCount() {
        return contactos.size();
    }

    @Override
    public void onBindViewHolder(final ContactoAdapter.ContactoViewHolder holder, final int position) {
        holder.nombre.setText(contactos.get(position).getNombre());
        holder.img.setImageResource(contactos.get(position).getImg());

        //Comprueba si es favorito, coloca imagen correspondiente
        if (contactos.get(position).isFavorito()) {
            holder.btnFav.setImageResource(R.drawable.fav);
        } else {
            holder.btnFav.setImageResource(R.drawable.nofav);
        }


        //Listener del boton favorito
        holder.btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Verifica que sea favorito
                if(estafav(position)){
                    //Si no es favorito, cambia el icono a fav y lo mete al arreglo
                    holder.btnFav.setImageResource(R.drawable.fav);
                    contextT1.addFavoritos(contactos.get(position));
                    Log.d("Entra","Es favorito: "+contactos.get(position).isFavorito());
                }
                else{
                    //Si es favorito, cambia el icono a nofav y lo quita del arreglo
                    contactos.get(position).setFavorito(false);
                    holder.btnFav.setImageResource(R.drawable.nofav);
                    contextT1.borrarFavorito(contactos.get(position).getNombre());
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

    public boolean estafav(int position){
        contactos.get(position).setFavorito(!contactos.get(position).isFavorito());
        return contactos.get(position).isFavorito();
    }

}
