package com.example.animeggs;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EpisodioAdapter extends RecyclerView.Adapter<EpisodioAdapter.EpisodioViewHolder> {
    private List<Episodio> episodios;

    public EpisodioAdapter(List<Episodio> episodios) {
        this.episodios = episodios;
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
        holder.textViewEpisodioNumero.setText("Episodio " + episodio.getNumero());
        holder.textViewEpisodioTitulo.setText(episodio.getTitulo());
    }

    @Override
    public int getItemCount() {
        return episodios.size();
    }

    public static class EpisodioViewHolder extends RecyclerView.ViewHolder {
        TextView textViewEpisodioNumero;
        TextView textViewEpisodioTitulo;

        public EpisodioViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewEpisodioTitulo = itemView.findViewById(R.id.text_view_episodio_titulo);
            textViewEpisodioNumero = itemView.findViewById(R.id.text_view_episodio_numero);

        }
    }
}
