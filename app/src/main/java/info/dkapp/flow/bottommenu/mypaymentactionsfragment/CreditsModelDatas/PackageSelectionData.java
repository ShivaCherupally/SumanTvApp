package info.dkapp.flow.bottommenu.mypaymentactionsfragment.CreditsModelDatas;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PackageSelectionData implements Parcelable {

    @SerializedName("isPackageSelected")
    @Expose
    public boolean isPackageSelected;

    @SerializedName("packageRefId")
    @Expose
    public String packageRefId;

    @SerializedName("country")
    @Expose
    public String country;

    @SerializedName("creditValue")
    @Expose
    public Double creditValue;

    @SerializedName("creditAmount")
    @Expose
    public Double creditAmount;

    public PackageSelectionData(boolean isPackageSelected, String packageRefId, String country, Double creditValue, Double creditAmount) {
        this.isPackageSelected = isPackageSelected;
        this.packageRefId = packageRefId;
        this.country = country;
        this.creditValue = creditValue;
        this.creditAmount = creditAmount;
    }

    protected PackageSelectionData(Parcel in) {
        isPackageSelected = in.readByte() != 0;
        packageRefId = in.readString();
        country = in.readString();
        if (in.readByte() == 0) {
            creditValue = null;
        } else {
            creditValue = in.readDouble();
        }
        if (in.readByte() == 0) {
            creditAmount = null;
        } else {
            creditAmount = in.readDouble();
        }
    }

    public static final Creator<PackageSelectionData> CREATOR = new Creator<PackageSelectionData>() {
        @Override
        public PackageSelectionData createFromParcel(Parcel in) {
            return new PackageSelectionData(in);
        }

        @Override
        public PackageSelectionData[] newArray(int size) {
            return new PackageSelectionData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (isPackageSelected ? 1 : 0));
        dest.writeString(packageRefId);
        dest.writeString(country);
        if (creditValue == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(creditValue);
        }
        if (creditAmount == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(creditAmount);
        }
    }
}
