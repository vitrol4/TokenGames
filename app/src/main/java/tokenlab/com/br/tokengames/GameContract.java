package tokenlab.com.br.tokengames;

import java.util.List;

import tokenlab.com.br.tokengames.model.Game;

public interface GameContract {

    interface View extends BaseView {

        void showGames(List<Game> games);

        void showMessage(int stringId);

        void showProgressIndicator();
    }

    interface Presenter extends BasePresenter<View> {

        void attachView(View view);

        void detachView();

        void loadGames();

    }
}
