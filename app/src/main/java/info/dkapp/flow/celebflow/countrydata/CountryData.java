package info.dkapp.flow.celebflow.countrydata;


import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/**
 * Created by Shiva on 7/17/2018.
 */

public class CountryData implements Serializable {
    @SerializedName("countryName")
    private String countryName;

    @SerializedName("dialCode")
    private String dial_code;

    @SerializedName("flag")
    private String flag;

    @SerializedName("maxlength")
    private int maxlength;

    @SerializedName("minLength")
    private int minLength;
    //dialCode

    @SerializedName("countryCode")
    private String countryCode;

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getDial_code() {
        return dial_code;
    }

    public void setDial_code(String dial_code) {
        this.dial_code = dial_code;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public int getMaxlength() {
        return maxlength;
    }

    public void setMaxlength(int maxlength) {
        this.maxlength = maxlength;
    }

    public int getMinLength() {
        return minLength;
    }

    public void setMinLength(int minLength) {
        this.minLength = minLength;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
}
