package info.dkapp.flow.bottommenu.menuitemsmanager.modelclasses;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Uday Kumay Vurukonda on 22-Aug-19.
 * <p>
 * Mr.Psycho
 */
public class InviteAFriendData implements Parcelable
{

    @SerializedName("memberCode")
    @Expose
    public String memberCode;
    @SerializedName("referralCreditValue")
    @Expose
    public Integer referralCreditValue;
    @SerializedName("referreCreditValue")
    @Expose
    public Integer referreCreditValue;
    @SerializedName("createdBy")
    @Expose
    public String createdBy;
    @SerializedName("updatedBy")
    @Expose
    public String updatedBy;
    @SerializedName("_id")
    @Expose
    public String id;
    @SerializedName("memberId")
    @Expose
    public String memberId;
    @SerializedName("updatedAt")
    @Expose
    public String updatedAt;
    @SerializedName("createdAt")
    @Expose
    public String createdAt;
    public final static Parcelable.Creator<InviteAFriendData> CREATOR = new Creator<InviteAFriendData>() {


        @SuppressWarnings({
                "unchecked"
        })
        public InviteAFriendData createFromParcel(Parcel in) {
            return new InviteAFriendData(in);
        }

        public InviteAFriendData[] newArray(int size) {
            return (new InviteAFriendData[size]);
        }

    };

    protected InviteAFriendData(Parcel in) {
        this.memberCode = ((String) in.readValue((String.class.getClassLoader())));
        this.referralCreditValue = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.referreCreditValue = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.createdBy = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedBy = ((String) in.readValue((String.class.getClassLoader())));
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.memberId = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedAt = ((String) in.readValue((String.class.getClassLoader())));
        this.createdAt = ((String) in.readValue((String.class.getClassLoader())));
    }

    public InviteAFriendData() {
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(memberCode);
        dest.writeValue(referralCreditValue);
        dest.writeValue(referreCreditValue);
        dest.writeValue(createdBy);
        dest.writeValue(updatedBy);
        dest.writeValue(id);
        dest.writeValue(memberId);
        dest.writeValue(updatedAt);
        dest.writeValue(createdAt);
    }

    public int describeContents() {
        return 0;
    }

}
