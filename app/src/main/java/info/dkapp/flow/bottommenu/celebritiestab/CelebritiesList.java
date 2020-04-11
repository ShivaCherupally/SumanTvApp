
package info.dkapp.flow.bottommenu.celebritiestab;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import info.dkapp.flow.appmanagers.feed.models.Celebrity;

import java.util.ArrayList;

public class CelebritiesList implements Parcelable {

    @SerializedName("celebrities")
    @Expose
    public ArrayList<Celebrity> celebrities;

    @SerializedName("recommendedCelebrities")
    @Expose
    public ArrayList<Celebrity> recommendedCelebrities;

    @SerializedName("editorChoiceCelebrities")
    @Expose
    public ArrayList<Celebrity> editorChoiceCelebrities;

    @SerializedName("listOfOnlineCelebraties")
    @Expose
    public ArrayList<Celebrity> listOfOnlineCelebraties;

    @SerializedName("trendingCelebrities")
    @Expose
    public ArrayList<Celebrity> trendingCelebrities;


    public CelebritiesList() {
    }


    protected CelebritiesList(Parcel in) {
        celebrities = in.createTypedArrayList(Celebrity.CREATOR);
        recommendedCelebrities = in.createTypedArrayList(Celebrity.CREATOR);
        editorChoiceCelebrities = in.createTypedArrayList(Celebrity.CREATOR);
        listOfOnlineCelebraties = in.createTypedArrayList(Celebrity.CREATOR);
        trendingCelebrities = in.createTypedArrayList(Celebrity.CREATOR);
    }

    public static final Creator<CelebritiesList> CREATOR = new Creator<CelebritiesList>() {
        @Override
        public CelebritiesList createFromParcel(Parcel in) {
            return new CelebritiesList(in);
        }

        @Override
        public CelebritiesList[] newArray(int size) {
            return new CelebritiesList[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(celebrities);
        dest.writeTypedList(recommendedCelebrities);
        dest.writeTypedList(editorChoiceCelebrities);
        dest.writeTypedList(listOfOnlineCelebraties);
        dest.writeTypedList(trendingCelebrities);
    }
}
