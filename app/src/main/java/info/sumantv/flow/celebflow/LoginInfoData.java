package info.sumantv.flow.celebflow;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Shiva on 3/17/2018.
 */

public class LoginInfoData {
    @SerializedName("username")
    private String username;

    @SerializedName("email")
    private String email;

    @SerializedName("password_signin")
    private String password;

    @SerializedName("mobileNumber")
    private String mobileNumber;

    @SerializedName("message")
    private String message;

    @SerializedName("country")
    private String country;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @SerializedName("timezone")
    private String timezone;


    @SerializedName("deviceToken")
    private String deviceToken;

    @SerializedName("loginType")
    private String loginType;

//    @SerializedName("country")
//    private String searchCode;
//    //
//
//
//    public String getSearchCode() {
//        return searchCode;
//    }
//
//    public void setSearchCode(String searchCode) {
//        this.searchCode = searchCode;
//    }

    @SerializedName("lastLoginLocation")
    private String loginlocation;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    /*public LoginInfoData(String email, String username, String password_signin, String mobileNumber) {
        this.email = email;
        this.username = username;
        this.password_signin = password_signin;
        this.mobileNumber = mobileNumber;
    }*/
    public LoginInfoData(String email, String username, String password, String mobileNumber,String country) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.mobileNumber = mobileNumber;
        this.country = country;
    }

    /*public LoginInfoData(String email, String username, String password_signin, String mobileNumber,
                         String loginlocation, String deviceToken, String timezone) {
        this.email = email;
        this.username = username;
        this.password_signin = password_signin;
        this.mobileNumber = mobileNumber;
        this.loginlocation = loginlocation;
        this.deviceToken = deviceToken;
        this.timezone = timezone;
    }*/

  /*  public LoginInfoData(String email, String username, String password_signin, String mobileNumber,
                         String loginlocation, String deviceToken, String timezone, String loginType) {
        this.email = email;
        this.username = username;
        this.password_signin = password_signin;
        this.mobileNumber = mobileNumber;
        this.loginlocation = loginlocation;
        this.deviceToken = deviceToken;
        this.timezone = timezone;
        this.loginType = loginType;
    }*/

    public LoginInfoData(String email, String username, String password, String mobileNumber,
                         String loginlocation, String deviceToken, String timezone, String loginType, String searchCode) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.mobileNumber = mobileNumber;
        this.loginlocation = loginlocation;
        this.deviceToken = deviceToken;
        this.timezone = timezone;
        this.loginType = loginType;
        this.country = searchCode;
    }
}
