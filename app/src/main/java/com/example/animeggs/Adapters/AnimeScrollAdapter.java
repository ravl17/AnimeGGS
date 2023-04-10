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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.animeggs.Activities.AnimeDetailsActivity;
import com.example.animeggs.Objetos.Anime;
import com.example.animeggs.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AnimeScrollAdapter extends RecyclerView.Adapter<AnimeScrollAdapter.AnimeScrollViewHolder> {
    private Context context;
    private List<Anime> animeList;

    public AnimeScrollAdapter(List<Anime> animeList, Context context) {
        this.animeList = animeList;
        this.context = context;
    }

    @NonNull
    @Override
    public AnimeScrollViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardAnime = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_small_anime, null);
        return new AnimeScrollViewHolder(cardAnime);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimeScrollViewHolder holder, int position) {
        Anime anime = animeList.get(position);
        Picasso.get().load(anime.getCaratula()).into(holder.cardCaratula);

        holder.cardNombre.setText(anime.getNombre());
        holder.cardLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AnimeDetailsActivity.class);
                intent.putExtra("anime_nombre", anime.getNombre());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return animeList.size();
    }

    public class AnimeScrollViewHolder extends RecyclerView.ViewHolder {
        LinearLayout cardLayout;
        ImageView cardCaratula;
        TextView cardNombre;

        public AnimeScrollViewHolder(CardView v) {
            super(v);
            cardLayout = v.findViewById(R.id.card_layout);
            int screenWidth = context.getResources().getDisplayMetrics().widthPixels;
            cardLayout.getLayoutParams().width = (int) (screenWidth /2.5);
            cardLayout.getLayoutParams().height = (int) (screenWidth*0.68);
            cardCaratula = v.findViewById(R.id.card_caratula);
            cardNombre = v.findViewById(R.id.card_nombre);
        }

    }
}
