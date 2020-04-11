package info.sumantv.flow.bottommenu.search;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Chenna on 21-08-2018.
 */

public class SearchData implements Parcelable {

    @SerializedName("celebSearchInfo")
    @Expose
    public ArrayList<Search> celebSearchInfo;
    @SerializedName("paginationDate")
    @Expose
    public String paginationDate;

    protected SearchData(Parcel in) {
        paginationDate = in.readString();
    }

    public static final Creator<SearchData> CREATOR = new Creator<SearchData>() {
        @Override
        public SearchData createFromParcel(Parcel in) {
            return new SearchData(in);
        }

        @Override
        public SearchData[] newArray(int size) {
            return new SearchData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(paginationDate);
    }
}
