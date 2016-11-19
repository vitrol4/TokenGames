package tokenlab.com.br.tokengames;

import android.app.Application;
import android.content.Context;

import rx.Scheduler;
import rx.schedulers.Schedulers;
import tokenlab.com.br.tokengames.model.GameAPI;

public class TokenGamesApplication extends Application {

    private GameAPI gameAPI;
    private Scheduler defaultSubscribeScheduler;

    public static TokenGamesApplication get(Context context) {
        return (TokenGamesApplication) context.getApplicationContext();
    }

    public GameAPI getGameAPI() {
        if (gameAPI == null) {
            gameAPI = GameAPI.Factory.create();
        }
        return gameAPI;
    }

    public Scheduler defaultSubscribeScheduler() {
        if (defaultSubscribeScheduler == null) {
            defaultSubscribeScheduler = Schedulers.io();
        }
        return defaultSubscribeScheduler;
    }

}
