
package info.dkapp.flow.userflow.UserActivitys.LoginActivity;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse implements Parcelable {

    @SerializedName("loginInfo")
    @Expose
    public Logininfo loginInfo;

    @SerializedName("userInfo")
    @Expose
    public UserInfo userInfo;

    @SerializedName("creditInfo")
    @Expose
    public CreditInfo creditInfo;

    public Boolean isPreferencesSelected;

    public LoginResponse(){}

    protected LoginResponse(Parcel in) {
        loginInfo = in.readParcelable(Logininfo.class.getClassLoader());
        userInfo = in.readParcelable(UserInfo.class.getClassLoader());
        creditInfo = in.readParcelable(CreditInfo.class.getClassLoader());
        byte tmpIsPreferencesSelected = in.readByte();
        isPreferencesSelected = tmpIsPreferencesSelected == 0 ? null : tmpIsPreferencesSelected == 1;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(loginInfo, flags);
        dest.writeParcelable(userInfo, flags);
        dest.writeParcelable(creditInfo, flags);
        dest.writeByte((byte) (isPreferencesSelected == null ? 0 : isPreferencesSelected ? 1 : 2));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LoginResponse> CREATOR = new Creator<LoginResponse>() {
        @Override
        public LoginResponse createFromParcel(Parcel in) {
            return new LoginResponse(in);
        }

        @Override
        public LoginResponse[] newArray(int size) {
            return new LoginResponse[size];
        }
    };
}
