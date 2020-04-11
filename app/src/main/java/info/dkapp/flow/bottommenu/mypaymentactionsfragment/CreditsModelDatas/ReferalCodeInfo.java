package info.dkapp.flow.bottommenu.mypaymentactionsfragment.CreditsModelDatas;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReferalCodeInfo implements Parcelable {
    @SerializedName("_id")
    @Expose
    public String _id;

    @SerializedName("memberCode")
    @Expose
    public String memberCode;

    protected ReferalCodeInfo(Parcel in) {
        _id = in.readString();
        memberCode = in.readString();
    }

    public static final Creator<ReferalCodeInfo> CREATOR = new Creator<ReferalCodeInfo>() {
        @Override
        public ReferalCodeInfo createFromParcel(Parcel in) {
            return new ReferalCodeInfo(in);
        }

        @Override
        public ReferalCodeInfo[] newArray(int size) {
            return new ReferalCodeInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(memberCode);
    }
}
