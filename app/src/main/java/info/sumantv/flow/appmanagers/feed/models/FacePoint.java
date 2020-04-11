package info.sumantv.flow.appmanagers.feed.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by User on 08-08-2018.
 **/

public class FacePoint implements Parcelable {
    @SerializedName("posX")
    public float x;

    @SerializedName("posY")
    public float y;

    public float width;
    public float height;

    public float faceCenterX;

    public float faceCenterY;

    public FacePoint() {

    }

    protected FacePoint(Parcel in) {
        x = in.readFloat();
        y = in.readFloat();
        width = in.readFloat();
        height = in.readFloat();
        faceCenterX = in.readFloat();
        faceCenterY = in.readFloat();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(x);
        dest.writeFloat(y);
        dest.writeFloat(width);
        dest.writeFloat(height);
        dest.writeFloat(faceCenterX);
        dest.writeFloat(faceCenterY);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FacePoint> CREATOR = new Creator<FacePoint>() {
        @Override
        public FacePoint createFromParcel(Parcel in) {
            return new FacePoint(in);
        }

        @Override
        public FacePoint[] newArray(int size) {
            return new FacePoint[size];
        }
    };
}
