package info.dkapp.flow.ModelClass;

import com.google.gson.annotations.SerializedName;

public class ContractsSuccess  {

    @SerializedName("serviceType")
    private String serviceType;

    @SerializedName("serviceCredits")
    private Double serviceCredits;


    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public Double getServiceCredits() {
        return serviceCredits;
    }

    public void setServiceCredits(Double serviceCredits) {
        this.serviceCredits = serviceCredits;
    }
}
