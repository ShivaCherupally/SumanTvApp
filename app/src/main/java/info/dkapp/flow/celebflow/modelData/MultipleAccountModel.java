package info.dkapp.flow.celebflow.modelData;

import android.os.Parcel;
import android.os.Parcelable;

public class MultipleAccountModel implements Parcelable {
    public Integer PrimID;
    public String userId;
    public String firstName;
    public String lastName;
    public String image;
    public String emailOrMobile;

    public MultipleAccountModel(){}

    protected MultipleAccountModel(Parcel in) {
        if (in.readByte() == 0) {
            PrimID = null;
        } else {
            PrimID = in.readInt();
        }
        userId = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        image = in.readString();
        emailOrMobile = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (PrimID == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(PrimID);
        }
        dest.writeString(userId);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(image);
        dest.writeString(emailOrMobile);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MultipleAccountModel> CREATOR = new Creator<MultipleAccountModel>() {
        @Override
        public MultipleAccountModel createFromParcel(Parcel in) {
            return new MultipleAccountModel(in);
        }

        @Override
        public MultipleAccountModel[] newArray(int size) {
            return new MultipleAccountModel[size];
        }
    };
}
