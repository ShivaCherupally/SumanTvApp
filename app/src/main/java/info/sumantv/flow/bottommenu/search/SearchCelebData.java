package info.sumantv.flow.bottommenu.search;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 *
 */

public class SearchCelebData implements Parcelable {


    @SerializedName("_id")
    @Expose
    private String _id;


    @SerializedName("history")
    @Expose
    public ArrayList<SearchCelebItemModel> history;

    @SerializedName("message")
    @Expose
    public String message;

    protected SearchCelebData(Parcel in) {
        _id = in.readString();
        history = in.createTypedArrayList(SearchCelebItemModel.CREATOR);
        message = in.readString();
    }

    public static final Creator<SearchCelebData> CREATOR = new Creator<SearchCelebData>() {
        @Override
        public SearchCelebData createFromParcel(Parcel in) {
            return new SearchCelebData(in);
        }

        @Override
        public SearchCelebData[] newArray(int size) {
            return new SearchCelebData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeTypedList(history);
        dest.writeString(message);
    }
}
