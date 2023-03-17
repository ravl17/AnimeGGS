package com.example.animeggs.Objetos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.animeggs.R;

import java.util.ArrayList;
import java.util.List;

public class AnimeSearchAdapter extends RecyclerView.Adapter<AnimeSearchAdapter.AnimeViewHolder> {

    private List<Anime> animeList;
    private List<Anime> filteredAnimeList;
    public AnimeSearchAdapter(List<Anime> animeList) {
        this.animeList = animeList;
        this.filteredAnimeList = new ArrayList<>(animeList);
    }
    @Override
    public AnimeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.anime_search, parent, false);
        return new AnimeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AnimeViewHolder holder, int position) {
        Anime anime = filteredAnimeList.get(position);

        holder.titleTextView.setText(anime.getNombre());
        // Bind other views to corresponding Anime fields here
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
    public static class AnimeViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;

        public AnimeViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.text_view_anime_search_nombre);
        }
    }
}
