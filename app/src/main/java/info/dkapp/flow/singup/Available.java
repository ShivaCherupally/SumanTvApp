package info.dkapp.flow.singup;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Uday Kumay Vurukonda on 23-Apr-19.
 * <p>
 * Mr.Psycho
 */
public class Available implements Parcelable
{

    @SerializedName("available")
    @Expose
    public boolean available;


    protected Available(Parcel in) {
        available = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (available ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Available> CREATOR = new Creator<Available>() {
        @Override
        public Available createFromParcel(Parcel in) {
            return new Available(in);
        }

        @Override
        public Available[] newArray(int size) {
            return new Available[size];
        }
    };
}
