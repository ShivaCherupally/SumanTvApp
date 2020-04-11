package info.sumantv.flow.appmanagers.feed.models;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;


import java.util.List;

import info.sumantv.flow.celebflow.constants.MediaType;

/**
 * Created by User on 30-07-2018.
 **/

public class GalleryFile implements Parcelable {

    public int id;
    public String name;
    public MediaType mediaType;//it will not add automatically, you have define manually
    public String mimeType;
    public Uri uri;
    public Uri thumbnailUri;
    public String size;
    public String dataFilePath;
    public double ratio = 1;
    public boolean isSelect = false;
    public boolean isCamera = false;
    public String caption = "";
    public List<FacePoint> facePointList;

    public GalleryFile()
    {

    }

    protected GalleryFile(Parcel in) {
        id = in.readInt();
        name = in.readString();
        mimeType = in.readString();
        mediaType = MediaType.values()[in.readInt()];
        uri = in.readParcelable(Uri.class.getClassLoader());
        thumbnailUri = in.readParcelable(Uri.class.getClassLoader());
        size = in.readString();
        dataFilePath = in.readString();
        ratio = in.readDouble();
        isSelect = in.readByte() != 0;
        isCamera = in.readByte() != 0;
        caption = in.readString();
        facePointList = in.createTypedArrayList(FacePoint.CREATOR);
    }

    public static final Creator<GalleryFile> CREATOR = new Creator<GalleryFile>() {
        @Override
        public GalleryFile createFromParcel(Parcel in) {
            return new GalleryFile(in);
        }

        @Override
        public GalleryFile[] newArray(int size) {
            return new GalleryFile[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(mimeType);
        parcel.writeInt(mediaType.ordinal());
        parcel.writeParcelable(uri, i);
        parcel.writeParcelable(thumbnailUri, i);
        parcel.writeString(size);
        parcel.writeString(dataFilePath);
        parcel.writeDouble(ratio);
        parcel.writeByte((byte) (isSelect ? 1 : 0));
        parcel.writeByte((byte) (isCamera ? 1 : 0));
        parcel.writeString(caption);
        parcel.writeTypedList(facePointList);
    }
}
