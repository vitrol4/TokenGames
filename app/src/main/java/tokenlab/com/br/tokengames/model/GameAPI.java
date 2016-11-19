package tokenlab.com.br.tokengames.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import rx.Observable;

public interface GameAPI {

    @GET("games")
    Observable<Games> games();

    class Factory {

        public static GameAPI create() {

            Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();

            OkHttpClient client = new OkHttpClient.Builder().build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://dl.dropboxusercontent.com/u/34048947/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(client)
                    .build();

            return retrofit.create(GameAPI.class);
        }

    }
}
