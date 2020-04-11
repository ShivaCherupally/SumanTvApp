package info.dkapp.flow.imagepicker.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import info.dkapp.flow.appmanagers.feed.models.FacePoint;
import info.dkapp.flow.celebflow.constants.MediaType;

/**
 * Created by hoanglam on 7/31/16.
 */
public class Image implements Parcelable {
    public long id;
    public String name;
    public MediaType mediaType;//it will not add automatically, you have define manually
    public String mimeType;
    public Uri uri;
    public Uri thumbnailUri;
    public String size;
    public String dataFilePath;
    public double ratio = 1;
    public boolean isSelect = false;
    public boolean isSelectMu = false;
    public boolean isCamera = false;
    public String caption = "";
    public List<FacePoint> facePointList;

    public Image(long id, String name, String path,MediaType mediaType,String mimeType,String size,Uri uri, Uri thumbnailUri) {
        this.id = id;
        this.name = name;
        this.dataFilePath = path;
        this.mediaType = mediaType;
        this.mimeType = mimeType;
        this.size = size;
        this.uri = uri;
        this.thumbnailUri = thumbnailUri;
    }
    public Image(long id, String name, String path) {
        this.id = id;
        this.name = name;
        this.dataFilePath = path;
    }


    protected Image(Parcel in) {
        id = in.readLong();
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

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(mimeType);
        dest.writeInt(mediaType.ordinal());
        dest.writeParcelable(uri, flags);
        dest.writeParcelable(thumbnailUri, flags);
        dest.writeString(size);
        dest.writeString(dataFilePath);
        dest.writeDouble(ratio);
        dest.writeByte((byte) (isSelect ? 1 : 0));
        dest.writeByte((byte) (isCamera ? 1 : 0));
        dest.writeString(caption);
        dest.writeTypedList(facePointList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDataFilePath() {
        return dataFilePath;
    }

    public void setDataFilePath(String dataFilePath) {
        this.dataFilePath = dataFilePath;
    }
}
