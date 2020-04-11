package info.sumantv.flow.userflow.UserActivitys.SignupActivity;

/**
 * Created by Shiva on 4/9/2018.
 */

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SignUpSocialData {
    @SerializedName("name")
    private String name;

    @SerializedName("username")
    private String username;

    @SerializedName("mobileNumber")
    private String mobileNumber;

    @SerializedName("location")
    private String location;

    @SerializedName("dateOfBirth")
    private String dateOfBirth;

    @SerializedName("password")
    private String password;

    @SerializedName("confirmPassword")
    private String confirmPassword;

    @SerializedName("email")
    private String email;

    @SerializedName("loginType")
    private String loginType;


    @SerializedName("role")
    private String role;

    @SerializedName("message")
    private String message;

    @SerializedName("referralCode")
    private String mReferal;

    @SerializedName("country")
    private String country;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @SerializedName("userdata")
    private Userdata userdata;

    @SerializedName("errors")
    private ArrayList<ErrorMessage> errors;


    public SignUpSocialData(String email, String loginType, String mobileNumber, String user_name, String refer) {
        this.loginType = loginType;
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.username = user_name;
        this.mReferal = refer;
        this.mReferal = refer;

    }
    public SignUpSocialData(String email, String loginType, String mobileNumber, String user_name, String refer,
                            String country) {
        this.loginType = loginType;
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.username = user_name;
        this.mReferal = refer;
        this.country= country;
    }

    public Userdata getUserdata() {
        return userdata;
    }

    public void setUserdata(Userdata userdata) {
        this.userdata = userdata;
    }

    public ArrayList<ErrorMessage> getErrors() {
        return errors;
    }

    public void setErrors(ArrayList<ErrorMessage> errors) {
        this.errors = errors;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public class ErrorMessage {
        @SerializedName("param")
        private String param;

        @SerializedName("msg")
        private String msg;

        public String getParam() {
            return param;
        }

        public void setParam(String param) {
            this.param = param;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }

    public class Userdata {
        @SerializedName("_id")
        private String _id;

        @SerializedName("name")
        private String name;

        @SerializedName("email")
        private String email;

        @SerializedName("mobileNumber")
        private String mobileNumber;

        @SerializedName("loginType")
        private String loginType;

        @SerializedName("role")
        private String role;

        @SerializedName("__v")
        private int __v;

        @SerializedName("preferences")
        private ArrayList<String> preferences;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMobileNumber() {
            return mobileNumber;
        }

        public void setMobileNumber(String mobileNumber) {
            this.mobileNumber = mobileNumber;
        }

        public String getLoginType() {
            return loginType;
        }

        public void setLoginType(String loginType) {
            this.loginType = loginType;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public int get__v() {
            return __v;
        }

        public void set__v(int __v) {
            this.__v = __v;
        }

        public ArrayList<String> getPreferences() {
            return preferences;
        }

        public void setPreferences(ArrayList<String> preferences) {
            this.preferences = preferences;
        }
    }
}

