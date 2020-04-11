package info.sumantv.flow.stories.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Uday Kumay Vurukonda on 09-Sep-19.
 * <p>
 * Mr.Psycho
 */
public class SeenProfileInnerData implements Parcelable {

    @SerializedName("_id")
    @Expose
    public String id;
    @SerializedName("isSeen")
    @Expose
    public boolean isSeen;
    @SerializedName("createdAt")
    @Expose
    public String createdAt;

    @SerializedName("storyByMemberDetails")
    @Expose
    public StoryByMemberInfo storyByMemberInfo;


    public class StoryByMemberInfo implements Parcelable {

        @SerializedName("_id")
        @Expose
        public String id;

        @SerializedName("username")
        @Expose
        public String username;

        @SerializedName("firstName")
        @Expose
        public String firstName;

        @SerializedName("lastName")
        @Expose
        public String lastName;

        @SerializedName("avtar_imgPath")
        @Expose
        public String avtar_imgPath;

        @SerializedName("isCeleb")
        @Expose
        public boolean isCeleb;

        @SerializedName("isOnline")
        @Expose
        public boolean isOnline;

        @SerializedName("isManager")
        @Expose
        public boolean isManager;

        @SerializedName("profession")
        @Expose
        public String profession;

        protected StoryByMemberInfo(Parcel in) {
            id = in.readString();
            username = in.readString();
            firstName = in.readString();
            lastName = in.readString();
            avtar_imgPath = in.readString();
            isCeleb = in.readByte() != 0;
            isOnline = in.readByte() != 0;
            isManager = in.readByte() != 0;
            profession = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeString(username);
            dest.writeString(firstName);
            dest.writeString(lastName);
            dest.writeString(avtar_imgPath);
            dest.writeByte((byte) (isCeleb ? 1 : 0));
            dest.writeByte((byte) (isOnline ? 1 : 0));
            dest.writeByte((byte) (isManager ? 1 : 0));
            dest.writeString(profession);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public  final Creator<StoryByMemberInfo> CREATOR = new Creator<StoryByMemberInfo>() {
            @Override
            public StoryByMemberInfo createFromParcel(Parcel in) {
                return new StoryByMemberInfo(in);
            }

            @Override
            public StoryByMemberInfo[] newArray(int size) {
                return new StoryByMemberInfo[size];
            }
        };
    }

    protected SeenProfileInnerData(Parcel in) {
        id = in.readString();
        isSeen = in.readByte() != 0;
        createdAt = in.readString();
        storyByMemberInfo = in.readParcelable(StoryByMemberInfo.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeByte((byte) (isSeen ? 1 : 0));
        dest.writeString(createdAt);
        dest.writeParcelable(storyByMemberInfo, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SeenProfileInnerData> CREATOR = new Creator<SeenProfileInnerData>() {
        @Override
        public SeenProfileInnerData createFromParcel(Parcel in) {
            return new SeenProfileInnerData(in);
        }

        @Override
        public SeenProfileInnerData[] newArray(int size) {
            return new SeenProfileInnerData[size];
        }
    };
}
