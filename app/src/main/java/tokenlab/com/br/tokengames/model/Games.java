package tokenlab.com.br.tokengames.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Games implements Parcelable {

    private List<Game> games = new ArrayList<>();

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.games);
    }

    public Games() {
    }

    protected Games(Parcel in) {
        this.games = in.createTypedArrayList(Game.CREATOR);
    }

    public static final Creator<Games> CREATOR = new Creator<Games>() {
        @Override
        public Games createFromParcel(Parcel source) {
            return new Games(source);
        }

        @Override
        public Games[] newArray(int size) {
            return new Games[size];
        }
    };
}
