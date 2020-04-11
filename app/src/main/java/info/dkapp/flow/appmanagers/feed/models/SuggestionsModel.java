package info.dkapp.flow.appmanagers.feed.models;

import android.os.Parcel;
import android.os.Parcelable;

public class SuggestionsModel implements Parcelable {
    public String _id;
    public String username;
    public boolean isCeleb;
    public String profession;
    public String aboutMe;
    public String lastName;
    public String firstName;
    public String imageRatio;
    public String avtar_imgPath;
    public String preferenceId;
    public Boolean isFollowStatus;

    protected SuggestionsModel(Parcel in) {
        _id = in.readString();
        username = in.readString();
        isCeleb = in.readByte() != 0;
        profession = in.readString();
        aboutMe = in.readString();
        lastName = in.readString();
        firstName = in.readString();
        imageRatio = in.readString();
        avtar_imgPath = in.readString();
        preferenceId = in.readString();
        byte tmpIsFanStatus = in.readByte();
        isFollowStatus = tmpIsFanStatus == 0 ? null : tmpIsFanStatus == 1;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(username);
        dest.writeByte((byte) (isCeleb ? 1 : 0));
        dest.writeString(profession);
        dest.writeString(aboutMe);
        dest.writeString(lastName);
        dest.writeString(firstName);
        dest.writeString(imageRatio);
        dest.writeString(avtar_imgPath);
        dest.writeString(preferenceId);
        dest.writeByte((byte) (isFollowStatus == null ? 0 : isFollowStatus ? 1 : 2));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SuggestionsModel> CREATOR = new Creator<SuggestionsModel>() {
        @Override
        public SuggestionsModel createFromParcel(Parcel in) {
            return new SuggestionsModel(in);
        }

        @Override
        public SuggestionsModel[] newArray(int size) {
            return new SuggestionsModel[size];
        }
    };
}
