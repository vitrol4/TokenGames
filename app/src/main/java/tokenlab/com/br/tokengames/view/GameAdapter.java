package tokenlab.com.br.tokengames.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import tokenlab.com.br.tokengames.R;
import tokenlab.com.br.tokengames.model.Game;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameViewHolder> {

    private List<Game> games;
    private Callback callback;

    public GameAdapter() {
        this.games = Collections.emptyList();
    }

    public GameAdapter(List<Game> games) {
        this.games = games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    public GameViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_game, parent, false);
        final GameViewHolder viewHolder = new GameViewHolder(itemView);
        viewHolder.trailerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.onItemClick(viewHolder.game);
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final GameViewHolder holder, int position) {
        Game game = games.get(position);
        holder.game = game;
        holder.nameTextView.setText(game.name);
        holder.loading.setVisibility(View.VISIBLE);
        Picasso.with(holder.imageView.getContext())
                .load(game.image)
                .error(R.drawable.placeholder)
                .into(holder.imageView, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        holder.loading.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        holder.loading.setVisibility(View.GONE);
                    }
                });
        holder.platformsLinearLayout.removeAllViews();
        for (String platform : game.platforms) {
            LayoutInflater inflater = LayoutInflater.from(holder.platformsLinearLayout.getContext());

            View mPlatformTextView;

            if (platform.startsWith("X")) {
                mPlatformTextView = inflater.inflate(R.layout.platform_microsoft_text_view, null, false);
            } else {
                mPlatformTextView = inflater.inflate(R.layout.platform_text_view, null, false);
            }

            TextView mTextView = (TextView) mPlatformTextView.findViewById(R.id.platform_text_view);
            mTextView.setText(platform);
            holder.platformsLinearLayout.addView(mPlatformTextView);
        }
        SimpleDateFormat sdf = new SimpleDateFormat(holder.releaseDateTextView.getContext().getString(R.string.date_mask), Locale.getDefault());
        holder.releaseDateTextView.setText(sdf.format(game.releaseDate));
    }

    @Override
    public int getItemCount() {
        return games.size();
    }

    interface Callback {
        void onItemClick(Game game);
    }

    static class GameViewHolder extends RecyclerView.ViewHolder {

        View loading;
        ImageView imageView;
        TextView nameTextView;
        LinearLayout platformsLinearLayout;
        View trailerView;
        TextView releaseDateTextView;
        Game game;

        GameViewHolder(View itemView) {
            super(itemView);
            loading = itemView.findViewById(R.id.loading);
            imageView = (ImageView) itemView.findViewById(R.id.image);
            nameTextView = (TextView) itemView.findViewById(R.id.name);
            platformsLinearLayout = (LinearLayout) itemView.findViewById(R.id.linear_platforms);
            trailerView = itemView.findViewById(R.id.trailer);
            releaseDateTextView = (TextView) itemView.findViewById(R.id.release_date);
        }
    }
}
