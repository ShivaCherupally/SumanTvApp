package info.dkapp.flow.menu_list.Settings.ChangePassword;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 */

public class ChangePasswordData {

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;


    @SerializedName("message")
    private String message;

    @SerializedName("errors")
    private ArrayList<ChangePasswordData.ErrorMessage> errors;

    public ArrayList<ChangePasswordData.ErrorMessage> getErrors() {
        return errors;
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

    public ChangePasswordData(String email, String password) {
        this.email = email;
        this.password = password;
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
}
