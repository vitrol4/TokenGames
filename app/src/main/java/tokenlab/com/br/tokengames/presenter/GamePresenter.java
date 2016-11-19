package tokenlab.com.br.tokengames.presenter;

import android.util.Log;

import java.util.List;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import tokenlab.com.br.tokengames.GameContract;
import tokenlab.com.br.tokengames.R;
import tokenlab.com.br.tokengames.TokenGamesApplication;
import tokenlab.com.br.tokengames.model.Game;
import tokenlab.com.br.tokengames.model.GameAPI;
import tokenlab.com.br.tokengames.model.Games;

public class GamePresenter implements GameContract.Presenter {

    private static String TAG = "GamePresenter";

    private GameContract.View gameView;
    private Subscription subscription;
    private List<Game> games;

    @Override
    public void attachView(GameContract.View view) {
        this.gameView = view;
    }

    @Override
    public void detachView() {
        this.gameView = null;
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }

    @Override
    public void loadGames() {
        gameView.showProgressIndicator();
        if (subscription != null) subscription.unsubscribe();
        TokenGamesApplication application = TokenGamesApplication.get(gameView.getContext());
        GameAPI gameAPI = application.getGameAPI();
        subscription = gameAPI.games()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(application.defaultSubscribeScheduler())
                .subscribe(new Subscriber<Games>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "Games loaded " + games);
                        if (!games.isEmpty()) {
                            gameView.showGames(games);
                        } else {
                            gameView.showMessage(R.string.empty);
                        }
                    }

                    @Override
                    public void onError(Throwable error) {
                        Log.e(TAG, "Error loading Games", error);
                        if (isHttp404(error)) {
                            gameView.showMessage(R.string.error_not_found);
                        } else {
                            gameView.showMessage(R.string.error_generic);
                        }
                    }

                    @Override
                    public void onNext(Games games) {
                        GamePresenter.this.games = games.getGames();
                    }
                });
    }

    private static boolean isHttp404(Throwable error) {
        return error instanceof HttpException && ((HttpException) error).code() == 404;
    }

}
