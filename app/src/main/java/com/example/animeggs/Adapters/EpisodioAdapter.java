package com.example.animeggs.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.animeggs.Objetos.Anime;
import com.example.animeggs.Objetos.Episodio;
import com.example.animeggs.R;
import com.example.animeggs.Activities.VerEpisodio;

import java.io.Serializable;
import java.util.List;

public class EpisodioAdapter extends RecyclerView.Adapter<EpisodioAdapter.EpisodioViewHolder> {
    private Context context;
    private List<Episodio> episodios;
    private Anime anime;

    public EpisodioAdapter(Anime anime, Context context) {
        this.episodios = anime.getEpisodios();
        this.anime = anime;
        this.context = context;
    }

    @NonNull
    @Override
    public EpisodioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.anime_episodio, parent, false);
        return new EpisodioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EpisodioViewHolder holder, int position) {
        Episodio episodio = episodios.get(position);
        holder.textViewEpisodioNumero.setText("Episodio " + (position+1));
        holder.episodio_layout.setOnClickListener(v -> {
            Intent intent = new Intent(context, VerEpisodio.class);
            intent.putExtra("enlaceEpisodio", episodio.getEp());
            intent.putExtra("numeroEpisodio", ""+position);
            intent.putExtra("anime", (Serializable) anime);
            intent.putParcelableArrayListExtra("episodios",  anime.getEpisodios());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return episodios.size();
    }

    public static class EpisodioViewHolder extends RecyclerView.ViewHolder {
        TextView textViewEpisodioNumero;
        TextView textViewEpisodioTitulo;
        LinearLayout episodio_layout;

        public EpisodioViewHolder(@NonNull View itemView) {
            super(itemView);
            episodio_layout = itemView.findViewById(R.id.anime_search_layout);
            textViewEpisodioTitulo = itemView.findViewById(R.id.text_view_anime_search_nombre);
            textViewEpisodioNumero = itemView.findViewById(R.id.text_view_episodio_numero);

        }

    }
}
