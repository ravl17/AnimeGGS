package com.example.animeggs.Adapters;




import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.animeggs.Objetos.Anime;
import com.example.animeggs.Objetos.Episodio;
import com.example.animeggs.R;
import com.example.animeggs.Activities.VerEpisodio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EpisodioAdapter extends RecyclerView.Adapter<EpisodioAdapter.EpisodioViewHolder> {
    private Context context;
    private List<Episodio> episodios;
    private Anime anime;
    private ArrayList<String> epVistos;

    public EpisodioAdapter(Anime anime,String epVistos, Context context) {
        this.episodios = anime.getEpisodios();
        this.anime = anime;
        this.context = context;
        this.epVistos=new ArrayList<>(Arrays.asList(epVistos.split(",")));
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
        Log.d("TAG", "onBindViewHolder: "+epVistos);
        if(epVistos.contains(position+"")){
            holder.imgVisto.setVisibility(View.VISIBLE);
            holder.episodioLayout.setBackgroundColor(0xFFE53637);
        }else{
            holder.imgVisto.setVisibility(View.GONE);
            holder.episodioLayout.setBackgroundColor(0xFFFFFFFF);
        }
        holder.textViewEpisodioNumero.setText("Episodio " + (position+1));
        holder.episodioLayout.setOnClickListener(v -> {
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
        LinearLayout episodioLayout;
        ImageView imgVisto;

        public EpisodioViewHolder(@NonNull View itemView) {
            super(itemView);
            episodioLayout = itemView.findViewById(R.id.anime_episodio_layout);
            imgVisto = itemView.findViewById(R.id.imgVisto);
            textViewEpisodioNumero = itemView.findViewById(R.id.text_view_episodio_numero);

        }

    }
}
