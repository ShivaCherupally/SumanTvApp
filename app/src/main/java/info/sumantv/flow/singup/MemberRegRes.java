package info.sumantv.flow.singup;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Uday Kumay Vurukonda on 24-Apr-19.
 * <p>
 * Mr.Psycho
 */
public class MemberRegRes implements Parcelable {

    @SerializedName("userId")
    @Expose
    public String userId;

    @SerializedName("username")
    @Expose
    public String username;


    protected MemberRegRes(Parcel in) {
        userId = in.readString();
        username = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userId);
        dest.writeString(username);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MemberRegRes> CREATOR = new Creator<MemberRegRes>() {
        @Override
        public MemberRegRes createFromParcel(Parcel in) {
            return new MemberRegRes(in);
        }

        @Override
        public MemberRegRes[] newArray(int size) {
            return new MemberRegRes[size];
        }
    };
}
