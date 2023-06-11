package com.example.animeggs.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.animeggs.Activities.AnimeDetailsActivity;
import com.example.animeggs.Objetos.Anime;
import com.example.animeggs.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AnimeSearchAdapter extends RecyclerView.Adapter<AnimeSearchAdapter.AnimeSearchViewHolder> {
    private Context context;
    private List<Anime> animeList;
    private List<Anime> filteredAnimeList;

    public AnimeSearchAdapter(List<Anime> animeList, Context context) {
        this.animeList = animeList;
        this.filteredAnimeList = new ArrayList<>(animeList);
        this.context = context;
    }

    @Override
    public AnimeSearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_grande_anime, parent, false);

        return new AnimeSearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AnimeSearchViewHolder holder, int position) {
        Anime anime = filteredAnimeList.get(position);
        Picasso.get().load(anime.getImg()).into(holder.cardCaratula);
        holder.cardNombre.setText(anime.getNombre());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AnimeDetailsActivity.class);
                intent.putExtra("anime", (Parcelable) anime);
                intent.putParcelableArrayListExtra("episodios", anime.getEpisodios());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredAnimeList.size();
    }


    public void filter(String query) {
        filteredAnimeList.clear();
        if (query.isEmpty()) {
            filteredAnimeList.addAll(animeList);
        } else {
            for (Anime anime : animeList) {
                if (anime.getNombre().toLowerCase().contains(query.toLowerCase())) {
                    filteredAnimeList.add(anime);
                }
            }
        }
        notifyDataSetChanged();
    }

    public class AnimeSearchViewHolder extends RecyclerView.ViewHolder {
        ImageView cardCaratula;
        TextView cardNombre;
        LinearLayout linearLayout;

        public AnimeSearchViewHolder(View itemView) {
            super(itemView);
            cardCaratula = itemView.findViewById(R.id.card_caratula);
            cardNombre = itemView.findViewById(R.id.card_nombre);
            linearLayout = itemView.findViewById(R.id.anime_search_layout);

        }
    }
}
