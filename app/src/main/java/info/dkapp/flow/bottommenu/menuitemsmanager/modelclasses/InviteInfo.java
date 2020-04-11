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
public class InviteInfo implements Parcelable {

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

    @SerializedName("inviteimage")
    @Expose
    public String inviteimage;


    protected InviteInfo(Parcel in) {
        memberCode = in.readString();
        if (in.readByte() == 0) {
            referralCreditValue = null;
        } else {
            referralCreditValue = in.readInt();
        }
        if (in.readByte() == 0) {
            referreCreditValue = null;
        } else {
            referreCreditValue = in.readInt();
        }
        createdBy = in.readString();
        updatedBy = in.readString();
        id = in.readString();
        memberId = in.readString();
        updatedAt = in.readString();
        createdAt = in.readString();
        inviteimage = in.readString();
    }

    public static final Creator<InviteInfo> CREATOR = new Creator<InviteInfo>() {
        @Override
        public InviteInfo createFromParcel(Parcel in) {
            return new InviteInfo(in);
        }

        @Override
        public InviteInfo[] newArray(int size) {
            return new InviteInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(memberCode);
        if (referralCreditValue == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(referralCreditValue);
        }
        if (referreCreditValue == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(referreCreditValue);
        }
        parcel.writeString(createdBy);
        parcel.writeString(updatedBy);
        parcel.writeString(id);
        parcel.writeString(memberId);
        parcel.writeString(updatedAt);
        parcel.writeString(createdAt);
        parcel.writeString(inviteimage);
    }
}
