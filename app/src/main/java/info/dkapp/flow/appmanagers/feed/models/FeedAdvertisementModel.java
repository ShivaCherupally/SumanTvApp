package info.dkapp.flow.appmanagers.feed.models;

import android.os.Parcel;
import android.os.Parcelable;

public class FeedAdvertisementModel implements Parcelable {
    public String title;
    public String description;
    public String advertisementType;
    public String location;
    public String appUrlIos;
    public String appUrlAndroid;
    public String webUrl;
    public String appLogoUrl;
    public String _id;
    public String status;
    public String expire;
    public String start;
    public String createdAt;
    public float appLogoRatio;
    public Source src;

    protected FeedAdvertisementModel(Parcel in) {
        title = in.readString();
        description = in.readString();
        advertisementType = in.readString();
        location = in.readString();
        appUrlIos = in.readString();
        appUrlAndroid = in.readString();
        webUrl = in.readString();
        appLogoUrl = in.readString();
        _id = in.readString();
        status = in.readString();
        expire = in.readString();
        start = in.readString();
        createdAt = in.readString();
        appLogoRatio = in.readFloat();
        src = in.readParcelable(Source.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(advertisementType);
        dest.writeString(location);
        dest.writeString(appUrlIos);
        dest.writeString(appUrlAndroid);
        dest.writeString(webUrl);
        dest.writeString(appLogoUrl);
        dest.writeString(_id);
        dest.writeString(status);
        dest.writeString(expire);
        dest.writeString(start);
        dest.writeString(createdAt);
        dest.writeFloat(appLogoRatio);
        dest.writeParcelable(src, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FeedAdvertisementModel> CREATOR = new Creator<FeedAdvertisementModel>() {
        @Override
        public FeedAdvertisementModel createFromParcel(Parcel in) {
            return new FeedAdvertisementModel(in);
        }

        @Override
        public FeedAdvertisementModel[] newArray(int size) {
            return new FeedAdvertisementModel[size];
        }
    };
}
