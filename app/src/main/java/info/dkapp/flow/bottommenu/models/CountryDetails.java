package info.dkapp.flow.bottommenu.models;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Uday Kumay Vurukonda on 05-Jun-19.
 * <p>
 * Mr.Psycho
 */
public class CountryDetails implements Parcelable
{

    @SerializedName("_id")
    @Expose
    public String id;
    @SerializedName("countryName")
    @Expose
    public String countryName;
    @SerializedName("dialCode")
    @Expose
    public String dialCode;
    @SerializedName("countryCode")
    @Expose
    public String countryCode;
    @SerializedName("flag")
    @Expose
    public String flag;
    @SerializedName("maxlength")
    @Expose
    public Integer maxlength;
    @SerializedName("minLength")
    @Expose
    public Integer minLength;

    protected CountryDetails(Parcel in) {
        id = in.readString();
        countryName = in.readString();
        dialCode = in.readString();
        countryCode = in.readString();
        flag = in.readString();
        if (in.readByte() == 0) {
            maxlength = null;
        } else {
            maxlength = in.readInt();
        }
        if (in.readByte() == 0) {
            minLength = null;
        } else {
            minLength = in.readInt();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(countryName);
        dest.writeString(dialCode);
        dest.writeString(countryCode);
        dest.writeString(flag);
        if (maxlength == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(maxlength);
        }
        if (minLength == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(minLength);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CountryDetails> CREATOR = new Creator<CountryDetails>() {
        @Override
        public CountryDetails createFromParcel(Parcel in) {
            return new CountryDetails(in);
        }

        @Override
        public CountryDetails[] newArray(int size) {
            return new CountryDetails[size];
        }
    };
}
