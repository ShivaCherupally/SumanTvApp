//package info.celebkonnect.flow.userflow.UserActivitys.SignupActivity;
//
//import com.google.gson.annotations.SerializedName;
//
//import java.util.ArrayList;
//
///**
// */
//
//public class SignupData {
//    @SerializedName("name")
//    private String name;
//
//    @SerializedName("username")
//    private String username;
//
//    @SerializedName("mobileNumber")
//    private String mobileNumber;
//
//
//    @SerializedName("location")
//    private String location;
//
//    @SerializedName("dateOfBirth")
//    private String dateOfBirth;
//
//    @SerializedName("password")
//    private String password;
//
//    @SerializedName("confirmPassword")
//    private String confirmPassword;
//
//    @SerializedName("email")
//    private String email;
//
//    @SerializedName("loginType")
//    private String loginType;
//
//
//    @SerializedName("role")
//    private String role;
//
//    @SerializedName("message")
//    private String message;
//
//    @SerializedName("referralCode")
//    private String mReferal;
//
//    @SerializedName("country")
//    private  String country;
//
//
//
//    @SerializedName("event")
//    private String event;
//
//
//    @SerializedName("mode_ids")
//    private ArrayList<String> mode_ids;
//
//    @SerializedName("to_addr")
//    private String to_addr;
//
//    @SerializedName("from_addr")
//    private String from_addr;
//
//    @SerializedName("content")
//    private String content;
//
//    @SerializedName("userdata")
//    private Userdata userdata;
//
//    @SerializedName("errors")
//    private ArrayList<ErrorMessage> errors;
//
//
//    public String getCountry() {
//        return country;
//    }
//
//    public void setCountry(String country) {
//        this.country = country;
//    }
//
//
//    public SignupData(String name, String username, String mobileNumber, String location,
//                      String dateOfBirth, String password, String confirmPassword, String email,
//                      String loginType, String role,String referal) {
//        this.name = name;
//        this.username = username;
//        this.mobileNumber = mobileNumber;
//        this.location = location;
//        this.dateOfBirth = dateOfBirth;
//        this.password = password;
//        this.confirmPassword = confirmPassword;
//        this.email = email;
//        this.loginType = loginType;
//        this.role = role;
//        this.mReferal = referal;
//    }
//
//    public SignupData(String name, String username, String mobileNumber, String location,
//                      String dateOfBirth, String password, String confirmPassword, String email,
//                      String loginType, String role,String referal,String searchCode,String event,
//                      ArrayList<String> mode_ids,String to_addr, String from_addr,String content) {
//        this.name = name;
//        this.username = username;
//        this.mobileNumber = mobileNumber;
//        this.location = location;
//        this.dateOfBirth = dateOfBirth;
//        this.password = password;
//        this.confirmPassword = confirmPassword;
//        this.email = email;
//        this.loginType = loginType;
//        this.role = role;
//        this.mReferal = referal;
//        this.country= searchCode;
//        this.event = event;
//        this.mode_ids = mode_ids;
//        this.to_addr = to_addr;
//        this.from_addr = from_addr;
//        this.content = content;
//
//    }
//
//    public SignupData(String name, String username, String mobileNumber, String location,
//                      String dateOfBirth, String password, String confirmPassword, String email,
//                      String loginType, String role) {
//        this.name = name;
//        this.username = username;
//        this.mobileNumber = mobileNumber;
//        this.location = location;
//        this.dateOfBirth = dateOfBirth;
//        this.password = password;
//        this.confirmPassword = confirmPassword;
//        this.email = email;
//        this.loginType = loginType;
//        this.role = role;
//    }
//
//   /* public SignupData(String email, String mobileNumber, String password, String name) {
//
//        this.mobileNumber = mobileNumber;
//        this.email = email;
//        this.password = password;
//        this.username = name;
//    }*/
//
//
//
//    public SignupData(String email, String loginType, String mobileNumber, String role, String name) {
//        this.loginType = loginType;
//        this.mobileNumber = mobileNumber;
//        this.email = email;
//        this.role = role;
//        this.name = name;
//
//    }
//
////    public SignupData(String email, String loginType, String mobileNumber, String role, String refer) {
////        this.loginType = loginType;
////        this.mobileNumber = mobileNumber;
////        this.email = email;
////        this.role = role;
////        this.mReferal = refer;
////
////    }
//
//    public SignupData(String email, String loginType, String mobileNumber, String username) {
//        this.email = email;
//        this.loginType = loginType;
//        this.mobileNumber = mobileNumber;
//        this.username = username;
//    }
//
//    public SignupData(String email, String loginType, String mobileNumber, String username,String country,
//                      String role) {
//        this.email = email;
//        this.loginType = loginType;
//        this.mobileNumber = mobileNumber;
//        this.username = username;
//        this.country= country;
//        this.role = role;
//
//    }
//
//    public Userdata getUserdata() {
//        return userdata;
//    }
//
//    //
//
//    public void setUserdata(Userdata userdata) {
//        this.userdata = userdata;
//    }
//
//    public ArrayList<ErrorMessage> getErrors() {
//        return errors;
//    }
//
//    public void setErrors(ArrayList<ErrorMessage> errors) {
//        this.errors = errors;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//
//    public class ErrorMessage {
//        @SerializedName("param")
//        private String param;
//
//        @SerializedName("msg")
//        private String msg;
//
//        public String getParam() {
//            return param;
//        }
//
//        public void setParam(String param) {
//            this.param = param;
//        }
//
//        public String getMsg() {
//            return msg;
//        }
//
//        public void setMsg(String msg) {
//            this.msg = msg;
//        }
//    }
//
//    public class Userdata {
//        @SerializedName("_id")
//        private String _id;
//
//        @SerializedName("name")
//        private String name;
//
//        @SerializedName("email")
//        private String email;
//
//        @SerializedName("mobileNumber")
//        private String mobileNumber;
//
//        @SerializedName("loginType")
//        private String loginType;
//
//        @SerializedName("role")
//        private String role;
//
//        @SerializedName("__v")
//        private int __v;
//
//        @SerializedName("preferences")
//        private ArrayList<String> preferences;
//
//        public String get_id() {
//            return _id;
//        }
//
//        public void set_id(String _id) {
//            this._id = _id;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public String getEmail() {
//            return email;
//        }
//
//        public void setEmail(String email) {
//            this.email = email;
//        }
//
//        public String getMobileNumber() {
//            return mobileNumber;
//        }
//
//        public void setMobileNumber(String mobileNumber) {
//            this.mobileNumber = mobileNumber;
//        }
//
//        public String getLoginType() {
//            return loginType;
//        }
//
//        public void setLoginType(String loginType) {
//            this.loginType = loginType;
//        }
//
//        public String getRole() {
//            return role;
//        }
//
//        public void setRole(String role) {
//            this.role = role;
//        }
//
//        public int get__v() {
//            return __v;
//        }
//
//        public void set__v(int __v) {
//            this.__v = __v;
//        }
//
//        public ArrayList<String> getPreferences() {
//            return preferences;
//        }
//
//        public void setPreferences(ArrayList<String> preferences) {
//            this.preferences = preferences;
//        }
//    }
//}
