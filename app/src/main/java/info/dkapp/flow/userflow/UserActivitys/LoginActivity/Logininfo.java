
package info.dkapp.flow.userflow.UserActivitys.LoginActivity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

public class Logininfo implements Parcelable
{

    public String id;
    public String email;
    public String username;
    public String password;
    public String mobileNumber;
    public String updatedBy;
    public String createdBy;
    public String updatedAt;
    public String createdAt;
    public Integer mobileVerificationCode;
    public Integer emailVerificationCode;
    public Boolean refCreditValue;
    public String resetPasswordExpires;
    public Object timezone;
    public String os;
    public Object callingDeviceToken;
    public Object deviceToken;
    public Object resetPasswordToken;
    public Object pwdChangeDate;
    public String lastLogoutDate;
    public String lastLoginDate;
    public Boolean verified;
    public Object lastLoginLocation;
    public Map<String, Object> additionalProperties = new HashMap<String, Object>();
    public String token;

    public Logininfo(){}

    protected Logininfo(Parcel in) {
        id = in.readString();
        email = in.readString();
        username = in.readString();
        password = in.readString();
        mobileNumber = in.readString();
        updatedBy = in.readString();
        createdBy = in.readString();
        updatedAt = in.readString();
        createdAt = in.readString();
        if (in.readByte() == 0) {
            mobileVerificationCode = null;
        } else {
            mobileVerificationCode = in.readInt();
        }
        if (in.readByte() == 0) {
            emailVerificationCode = null;
        } else {
            emailVerificationCode = in.readInt();
        }
        byte tmpRefCreditValue = in.readByte();
        refCreditValue = tmpRefCreditValue == 0 ? null : tmpRefCreditValue == 1;
        resetPasswordExpires = in.readString();
        os = in.readString();
        lastLogoutDate = in.readString();
        lastLoginDate = in.readString();
        byte tmpVerified = in.readByte();
        verified = tmpVerified == 0 ? null : tmpVerified == 1;
        token = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(email);
        dest.writeString(username);
        dest.writeString(password);
        dest.writeString(mobileNumber);
        dest.writeString(updatedBy);
        dest.writeString(createdBy);
        dest.writeString(updatedAt);
        dest.writeString(createdAt);
        if (mobileVerificationCode == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(mobileVerificationCode);
        }
        if (emailVerificationCode == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(emailVerificationCode);
        }
        dest.writeByte((byte) (refCreditValue == null ? 0 : refCreditValue ? 1 : 2));
        dest.writeString(resetPasswordExpires);
        dest.writeString(os);
        dest.writeString(lastLogoutDate);
        dest.writeString(lastLoginDate);
        dest.writeByte((byte) (verified == null ? 0 : verified ? 1 : 2));
        dest.writeString(token);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Logininfo> CREATOR = new Creator<Logininfo>() {
        @Override
        public Logininfo createFromParcel(Parcel in) {
            return new Logininfo(in);
        }

        @Override
        public Logininfo[] newArray(int size) {
            return new Logininfo[size];
        }
    };
}
