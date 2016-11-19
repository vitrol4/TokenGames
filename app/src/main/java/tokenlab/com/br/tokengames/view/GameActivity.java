package tokenlab.com.br.tokengames.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import tokenlab.com.br.tokengames.GameContract;
import tokenlab.com.br.tokengames.R;
import tokenlab.com.br.tokengames.model.Game;
import tokenlab.com.br.tokengames.presenter.GamePresenter;

public class GameActivity extends AppCompatActivity implements GameContract.View {

    private GameContract.Presenter presenter;
    private RecyclerView mRecycleView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new GamePresenter();
        presenter.attachView(this);

        setContentView(R.layout.activity_game);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mRecycleView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.my_swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.accent, R.color.primary, R.color.accent, R.color.primary);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.loadGames();
            }
        });
        GameAdapter adapter = new GameAdapter();
        adapter.setCallback(new GameAdapter.Callback() {
            @Override
            public void onItemClick(Game game) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(game.trailer)));
            }
        });
        mRecycleView.setAdapter(adapter);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));

        presenter.loadGames();
    }

    @Override
    protected void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showGames(List<Game> games) {
        GameAdapter adapter = (GameAdapter) mRecycleView.getAdapter();
        adapter.setGames(games);
        adapter.notifyDataSetChanged();
        mRecycleView.requestFocus();
        mSwipeRefreshLayout.setRefreshing(false);
        mRecycleView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMessage(int stringId) {
        mSwipeRefreshLayout.setRefreshing(false);
        mRecycleView.setVisibility(View.INVISIBLE);
        Toast.makeText(getContext(), getString(stringId), Toast.LENGTH_LONG).show();
    }

    @Override
    public void showProgressIndicator() {
        mSwipeRefreshLayout.setRefreshing(true);
        mRecycleView.setVisibility(View.INVISIBLE);
    }

}
