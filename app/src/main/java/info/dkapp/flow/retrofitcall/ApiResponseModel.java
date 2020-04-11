package info.dkapp.flow.retrofitcall;

import android.os.Parcel;
import android.os.Parcelable;

public class ApiResponseModel implements Parcelable {
    public Integer success;
    public String message;
    public String token;
    public Object data;

    public ApiResponseModel(){}

    protected ApiResponseModel(Parcel in) {
        if (in.readByte() == 0) {
            success = null;
        } else {
            success = in.readInt();
        }
        message = in.readString();
        token = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (success == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(success);
        }
        dest.writeString(message);
        dest.writeString(token);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApiResponseModel> CREATOR = new Creator<ApiResponseModel>() {
        @Override
        public ApiResponseModel createFromParcel(Parcel in) {
            return new ApiResponseModel(in);
        }

        @Override
        public ApiResponseModel[] newArray(int size) {
            return new ApiResponseModel[size];
        }
    };

}
