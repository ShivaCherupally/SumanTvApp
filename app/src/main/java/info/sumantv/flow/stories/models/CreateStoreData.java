package info.sumantv.flow.stories.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;

/**
 * Created by Uday Kumay Vurukonda on 03-Sep-19.
 * <p>
 * Mr.Psycho
 */
public class CreateStoreData implements Parcelable
{

    @SerializedName("memberId")
    @Expose
    public String memberId;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("media")
    @Expose
    public List<CreateStoreMedia> media = null;

    public ArrayList<MultipartBody.Part> partArrayList;

    public CreateStoreData(String memberId, String title, List<CreateStoreMedia> media) {
        this.memberId = memberId;
        this.title = title;
        this.media = media;
    }
    public CreateStoreData(String memberId, String title, List<CreateStoreMedia> media,ArrayList<MultipartBody.Part> partArrayList) {
        this.memberId = memberId;
        this.title = title;
        this.media = media;
        this.partArrayList = partArrayList;
    }


    protected CreateStoreData(Parcel in) {
        memberId = in.readString();
        title = in.readString();
        media = in.createTypedArrayList(CreateStoreMedia.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(memberId);
        dest.writeString(title);
        dest.writeTypedList(media);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CreateStoreData> CREATOR = new Creator<CreateStoreData>() {
        @Override
        public CreateStoreData createFromParcel(Parcel in) {
            return new CreateStoreData(in);
        }

        @Override
        public CreateStoreData[] newArray(int size) {
            return new CreateStoreData[size];
        }
    };
}
