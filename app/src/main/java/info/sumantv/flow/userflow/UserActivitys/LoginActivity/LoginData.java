package info.sumantv.flow.userflow.UserActivitys.LoginActivity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by PROXIM on 2/5/2018.
 */

public class LoginData {


    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("role")
    private String role;

    @SerializedName("message")
    private String message;

    @SerializedName("success")
    private int success;

    @SerializedName("lastLoginLocation")
    private String loginlocation;

    @SerializedName("deviceToken")
    private String deviceToken;

    @SerializedName("timezone")
    private String timezone;

    @SerializedName("error")
    private String error;

    @SerializedName("osType")
    private String osType;

    @SerializedName("secureNewLogin")
    Boolean secureNewLogin;



    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public LoginData(String email) {
        this.email = email;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getLoginlocation() {
        return loginlocation;
    }

    public void setLoginlocation(String loginlocation) {
        this.loginlocation = loginlocation;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }

    public Boolean getSecureNewLogin() {
        return secureNewLogin;
    }

    public void setSecureNewLogin(Boolean secureNewLogin) {
        this.secureNewLogin = secureNewLogin;
    }

    public LoginData(String email, String password, String loginlocation, String deviceToken, String timezone, String osType,Boolean secureNewLogin) {
        this.email = email;
        this.password = password;
        this.loginlocation = loginlocation;
        this.deviceToken = deviceToken;
        this.timezone = timezone;
        this.osType = osType;
        this.secureNewLogin = secureNewLogin;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public LoginData(String email, String password, String loginlocation) {
        this.email = email;
        this.password = password;
        this.loginlocation = loginlocation;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }
}
