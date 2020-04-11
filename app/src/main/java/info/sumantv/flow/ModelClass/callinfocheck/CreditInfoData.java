package info.sumantv.flow.ModelClass.callinfocheck;

import com.google.gson.annotations.SerializedName;

public class CreditInfoData {

    @SerializedName("serviceType")
    private String serviceType;


    @SerializedName("serviceCredits")
    private int serviceCredits;


    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public int getServiceCredits() {
        return serviceCredits;
    }

    public void setServiceCredits(int serviceCredits) {
        this.serviceCredits = serviceCredits;
    }
}
