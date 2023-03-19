package com.example.animeggs.Objetos;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.animeggs.Activities.AnimeDetailsActivity;
import com.example.animeggs.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class AnimeSearchAdapter extends RecyclerView.Adapter<AnimeSearchAdapter.AnimeViewHolder> {
    private Context context;
    private List<Anime> animeList;
    private List<Anime> filteredAnimeList;
    public AnimeSearchAdapter(List<Anime> animeList,Context context) {
        this.animeList = animeList;
        this.filteredAnimeList = new ArrayList<>(animeList);
        this.context=context;
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
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AnimeDetailsActivity.class);
                intent.putExtra("anime_nombre", anime.getNombre());
                intent.putExtra("anime_caratula", anime.getCaratula());
                intent.putExtra("anime_descripcion", anime.getDescripcion());

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = null;
                try {
                    oos = new ObjectOutputStream(baos);
                    oos.writeObject(anime.getEpisodios());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                byte[] byteArray = baos.toByteArray();
                intent.putExtra("anime_episodios", byteArray);
                context.startActivity(intent);
            }
        });
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
        LinearLayout linearLayout;

        public AnimeViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.text_view_anime_search_nombre);
            linearLayout = itemView.findViewById(R.id.anime_search_layout);

        }
    }
}
