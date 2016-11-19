package tokenlab.com.br.tokengames;

public interface BasePresenter<V> {

    void attachView(V view);

    void detachView();

}
