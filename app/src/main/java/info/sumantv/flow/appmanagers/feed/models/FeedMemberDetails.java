package info.sumantv.flow.appmanagers.feed.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Uday Kumay Vurukonda on 12/20/2018.
 * <p>
 * Mr.Psycho
 */
public class FeedMemberDetails implements Parcelable {
    public String profession;
    public String lastName;
    public String firstName;
    public String gender;
    public Boolean isCeleb;
    public Boolean isManager;
    public Boolean isOnline;
    public String _id;
    public String avtar_imgPath;
    public String username;
    public String category;

    public FeedMemberDetails() {
    }

    public FeedMemberDetails(Boolean isCeleb, Boolean isManager) {
        this.isCeleb = isCeleb;
        this.isManager = isManager;
    }

    protected FeedMemberDetails(Parcel in) {
        profession = in.readString();
        lastName = in.readString();
        firstName = in.readString();
        gender = in.readString();
        byte tmpIsCeleb = in.readByte();
        isCeleb = tmpIsCeleb == 0 ? null : tmpIsCeleb == 1;
        byte tmpIsManager = in.readByte();
        isManager = tmpIsManager == 0 ? null : tmpIsManager == 1;
        byte tmpIsOnline = in.readByte();
        isOnline = tmpIsOnline == 0 ? null : tmpIsOnline == 1;
        _id = in.readString();
        avtar_imgPath = in.readString();
        username = in.readString();
        category = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(profession);
        dest.writeString(lastName);
        dest.writeString(firstName);
        dest.writeString(gender);
        dest.writeByte((byte) (isCeleb == null ? 0 : isCeleb ? 1 : 2));
        dest.writeByte((byte) (isManager == null ? 0 : isManager ? 1 : 2));
        dest.writeByte((byte) (isOnline == null ? 0 : isOnline ? 1 : 2));
        dest.writeString(_id);
        dest.writeString(avtar_imgPath);
        dest.writeString(username);
        dest.writeString(category);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FeedMemberDetails> CREATOR = new Creator<FeedMemberDetails>() {
        @Override
        public FeedMemberDetails createFromParcel(Parcel in) {
            return new FeedMemberDetails(in);
        }

        @Override
        public FeedMemberDetails[] newArray(int size) {
            return new FeedMemberDetails[size];
        }
    };
}
