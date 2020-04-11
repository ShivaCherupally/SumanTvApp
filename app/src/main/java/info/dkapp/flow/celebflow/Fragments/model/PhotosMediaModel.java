package info.dkapp.flow.celebflow.Fragments.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


import info.dkapp.flow.appmanagers.feed.models.Media;

public class PhotosMediaModel implements Parcelable{

    @SerializedName("_id")
    @Expose
    public String id;
    @SerializedName("memberId")
    @Expose
    public String memberId;
    @SerializedName("media")
    @Expose
    public ArrayList<Media> media;
    @SerializedName("createdAt")
    @Expose
    public String createdAt;


    public PhotosMediaModel(Parcel in) {
        id = in.readString();
        memberId = in.readString();
        media = in.createTypedArrayList(Media.CREATOR);
        createdAt = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(memberId);
        dest.writeTypedList(media);
        dest.writeString(createdAt);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PhotosMediaModel> CREATOR = new Creator<PhotosMediaModel>() {
        @Override
        public PhotosMediaModel createFromParcel(Parcel in) {
            return new PhotosMediaModel(in);
        }

        @Override
        public PhotosMediaModel[] newArray(int size) {
            return new PhotosMediaModel[size];
        }
    };
}
