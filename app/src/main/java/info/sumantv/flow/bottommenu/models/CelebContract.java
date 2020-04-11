
package info.sumantv.flow.bottommenu.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CelebContract implements Parcelable
{

    @SerializedName("serviceType")
    @Expose
    private String serviceType;
    @SerializedName("managerSharePercentage")
    @Expose
    private Integer managerSharePercentage;
    @SerializedName("charitySharePercentage")
    @Expose
    private Integer charitySharePercentage;
    @SerializedName("promoterSharePercentage")
    @Expose
    private Integer promoterSharePercentage;
    @SerializedName("sharingPercentage")
    @Expose
    private Integer sharingPercentage;
    @SerializedName("serviceCredits")
    @Expose
    private Double serviceCredits;
    public final static Creator<CelebContract> CREATOR = new Creator<CelebContract>() {


        @SuppressWarnings({
            "unchecked"
        })
        public CelebContract createFromParcel(Parcel in) {
            return new CelebContract(in);
        }

        public CelebContract[] newArray(int size) {
            return (new CelebContract[size]);
        }

    }
    ;

    protected CelebContract(Parcel in) {
        this.serviceType = ((String) in.readValue((String.class.getClassLoader())));
        this.managerSharePercentage = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.charitySharePercentage = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.promoterSharePercentage = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.sharingPercentage = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.serviceCredits = ((Double) in.readValue((Integer.class.getClassLoader())));
    }

    public CelebContract() {
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public Integer getManagerSharePercentage() {
        return managerSharePercentage;
    }

    public void setManagerSharePercentage(Integer managerSharePercentage) {
        this.managerSharePercentage = managerSharePercentage;
    }

    public Integer getCharitySharePercentage() {
        return charitySharePercentage;
    }

    public void setCharitySharePercentage(Integer charitySharePercentage) {
        this.charitySharePercentage = charitySharePercentage;
    }

    public Integer getPromoterSharePercentage() {
        return promoterSharePercentage;
    }

    public void setPromoterSharePercentage(Integer promoterSharePercentage) {
        this.promoterSharePercentage = promoterSharePercentage;
    }

    public Integer getSharingPercentage() {
        return sharingPercentage;
    }

    public void setSharingPercentage(Integer sharingPercentage) {
        this.sharingPercentage = sharingPercentage;
    }

    public Double getServiceCredits() {
        return serviceCredits;
    }

    public void setServiceCredits(Double serviceCredits) {
        this.serviceCredits = serviceCredits;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(serviceType);
        dest.writeValue(managerSharePercentage);
        dest.writeValue(charitySharePercentage);
        dest.writeValue(promoterSharePercentage);
        dest.writeValue(sharingPercentage);
        dest.writeValue(serviceCredits);
    }

    public int describeContents() {
        return  0;
    }

}
