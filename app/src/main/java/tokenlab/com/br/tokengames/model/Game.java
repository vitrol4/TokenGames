package tokenlab.com.br.tokengames.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.internal.Platform;

public class Game implements Parcelable {

    public String name;
    public String image;

    @SerializedName("release_date")
    public Date releaseDate;
    public String trailer;
    public List<String> platforms;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.image);
        dest.writeLong(this.releaseDate != null ? this.releaseDate.getTime() : -1);
        dest.writeString(this.trailer);
        dest.writeList(this.platforms);
    }

    public Game() {

    }

    public Game(Parcel in) {
        this.name = in.readString();
        this.image = in.readString();
        long tmpReleaseDate = in.readLong();
        this.releaseDate = tmpReleaseDate == -1 ? null : new Date(tmpReleaseDate);
        this.trailer = in.readString();
        this.platforms = new ArrayList<>();
        in.readList(this.platforms, Platform.class.getClassLoader());
    }

    public static final Creator<Game> CREATOR = new Creator<Game>() {
        @Override
        public Game createFromParcel(Parcel source) {
            return new Game(source);
        }

        @Override
        public Game[] newArray(int size) {
            return new Game[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Game game = (Game) o;

        if (name != null ? !name.equals(game.name) : game.name != null) return false;
        if (image != null ? !image.equals(game.image) : game.image != null) return false;
        if (releaseDate != null ? !releaseDate.equals(game.releaseDate) : game.releaseDate != null)
            return false;
        if (trailer != null ? !trailer.equals(game.trailer) : game.trailer != null) return false;
        return platforms != null ? platforms.equals(game.platforms) : game.platforms == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (image != null ? image.hashCode() : 0);
        result = 31 * result + (releaseDate != null ? releaseDate.hashCode() : 0);
        result = 31 * result + (trailer != null ? trailer.hashCode() : 0);
        result = 31 * result + (platforms != null ? platforms.hashCode() : 0);
        return result;
    }
}
