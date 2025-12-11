package com.example.oriontek.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.oriontek.Models.Client;
import com.example.oriontek.Interfaces.OnItemClickListener;
import com.example.oriontek.R;

import java.util.ArrayList;

public class ClientAdapter extends RecyclerView.Adapter<ClientAdapter.ViewHolder> {

    private final ArrayList<Client> clients;
    private final OnItemClickListener listener;
    private final int layout;
    private final Context context;

    public ClientAdapter(Context context, ArrayList<Client> clients, int layout, OnItemClickListener listener) {

        this.context = context;
        this.clients = clients;
        this.layout = layout;
        this.listener = listener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context)
                .load(clients.get(position).getImage())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .circleCrop()
                .into(holder.image);

        holder.name.setText(clients.get(position).getName());
        holder.bind(clients.get(position), listener);


    }

    @Override
    public int getItemCount() {
        return clients.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private final ImageView image;
        private final TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imgPhoto);
            name = itemView.findViewById(R.id.tvName);
        }

        public void bind(final Client client, final OnItemClickListener listener){

            itemView.setOnClickListener(view -> listener.onItemClick(client, getAdapterPosition()));
        }
    }
}

