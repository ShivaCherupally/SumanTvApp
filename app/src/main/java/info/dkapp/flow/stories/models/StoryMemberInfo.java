package info.dkapp.flow.stories.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Uday Kumay Vurukonda on 09-Sep-19.
 * <p>
 * Mr.Psycho
 */
public class StoryMemberInfo implements Parcelable
{

    @SerializedName("_id")
    @Expose
    public String id;
    @SerializedName("username")
    @Expose
    public String username;
    @SerializedName("cover_imgPath")
    @Expose
    public String coverImgPath;
    @SerializedName("isManager")
    @Expose
    public Boolean isManager;
    @SerializedName("isOnline")
    @Expose
    public Boolean isOnline;
    @SerializedName("isCeleb")
    @Expose
    public Boolean isCeleb;
    @SerializedName("profession")
    @Expose
    public String profession;
    @SerializedName("gender")
    @Expose
    public String gender;
    @SerializedName("lastName")
    @Expose
    public String lastName;
    @SerializedName("firstName")
    @Expose
    public String firstName;
    @SerializedName("avtar_imgPath")
    @Expose
    public String avtarImgPath;
    public final static Parcelable.Creator<StoryMemberInfo> CREATOR = new Creator<StoryMemberInfo>() {


        @SuppressWarnings({
                "unchecked"
        })
        public StoryMemberInfo createFromParcel(Parcel in) {
            return new StoryMemberInfo(in);
        }

        public StoryMemberInfo[] newArray(int size) {
            return (new StoryMemberInfo[size]);
        }

    };


    protected StoryMemberInfo(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.username = ((String) in.readValue((String.class.getClassLoader())));
        this.coverImgPath = ((String) in.readValue((String.class.getClassLoader())));
        this.isManager = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.isOnline = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.isCeleb = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.profession = ((String) in.readValue((String.class.getClassLoader())));
        this.gender = ((String) in.readValue((String.class.getClassLoader())));
        this.lastName = ((String) in.readValue((String.class.getClassLoader())));
        this.firstName = ((String) in.readValue((String.class.getClassLoader())));
        this.avtarImgPath = ((String) in.readValue((String.class.getClassLoader())));
    }

    public StoryMemberInfo() {
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(username);
        dest.writeValue(coverImgPath);
        dest.writeValue(isManager);
        dest.writeValue(isOnline);
        dest.writeValue(isCeleb);
        dest.writeValue(profession);
        dest.writeValue(gender);
        dest.writeValue(lastName);
        dest.writeValue(firstName);
        dest.writeValue(avtarImgPath);
    }

    public int describeContents() {
        return 0;
    }

}
