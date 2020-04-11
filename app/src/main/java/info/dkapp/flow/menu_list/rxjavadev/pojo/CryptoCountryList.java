package info.dkapp.flow.menu_list.rxjavadev.pojo;

import com.google.gson.annotations.SerializedName;

public class CryptoCountryList {

    @SerializedName("countryCode")
    public String countryCode;
    @SerializedName("countryName")
    public String countryName;


    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
