//package info.celebkonnect.flow.celebflow.EditProfileActivity;
//
//import android.util.Log;
//
//import com.google.gson.annotations.SerializedName;
//
//import java.util.ArrayList;
//
///**
// */
//
//public class ProfileData {
//
//    @SerializedName("profilePic")
//    private String profilePic;
//
//    @SerializedName("location")
//    private String Location;
//
//    @SerializedName("id")
//    private String id;
//
//    @SerializedName("dateOfBirth")
//    private String dateOfBirth;
//
//    @SerializedName("address")
//    private String address;
//
//    @SerializedName("availableCredits")
//    private String availableCredits;
//
//
//    @SerializedName("profession")
//    private String profession;
//
//    @SerializedName("gender")
//    private String gender;
//
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
//
//
//
//    @SerializedName("message")
//    private String message;
//
//
//    public String getProfilePic() {
//        return profilePic;
//    }
//
//    public void setProfilePic(String profilePic) {
//        this.profilePic = profilePic;
//    }
//
//    public String getLocation() {
//        return Location;
//    }
//
//    public void setLocation(String location) {
//        Location = location;
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public String getDateOfBirth() {
//        return dateOfBirth;
//    }
//
//    public void setDateOfBirth(String dateOfBirth) {
//        this.dateOfBirth = dateOfBirth;
//    }
//
//    public String getAddress() {
//        return address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }
//
//    public String getAvailableCredits() {
//        return availableCredits;
//    }
//
//    public void setAvailableCredits(String availableCredits) {
//        this.availableCredits = availableCredits;
//    }
//
//    public String getProfession() {
//        return profession;
//    }
//
//    public void setProfession(String profession) {
//        profession = profession;
//    }
//
//    public String getGender() {
//        return gender;
//    }
//
//    public void setGender(String gender) {
//        gender = gender;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getLoginType() {
//        return loginType;
//    }
//
//    public void setLoginType(String loginType) {
//        this.loginType = loginType;
//    }
//
//    public String getRole() {
//        return role;
//    }
//
//    public void setRole(String role) {
//        this.role = role;
//    }
//
//    public ProfileData(String profilePic, String location, String id, String dateOfBirth,
//                       String address, String availableCredits, String profession, String gender,
//                       String loginType, String role
//                       ) {
//        this.profilePic = profilePic;
//        this.Location = location;
//        this.id = id;
//        this.dateOfBirth = dateOfBirth;
//        this.address = address;
//        this.availableCredits = availableCredits;
//        this.profession = profession;
//        this.gender = gender;
//        this.loginType = loginType;
//        this.role = role;
//        Log.e("paramsUpdated", toString() + "");
//    }
//
//    @SerializedName("errors")
//    private ArrayList<ErrorMessage> errors;
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
//    @SerializedName("userdata")
//    private Userdata userdata;
//
//    public Userdata getUserdata() {
//        return userdata;
//    }
//
//    public void setUserdata(Userdata userdata) {
//        this.userdata = userdata;
//    }
//
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
//
//    @Override
//    public String toString() {
//        return "ProfileData{" +
//                "profilePic='" + profilePic + '\'' +
//                ", Location='" + Location + '\'' +
//                ", id='" + id + '\'' +
//                ", dateOfBirth='" + dateOfBirth + '\'' +
//                ", address='" + address + '\'' +
//                ", availableCredits='" + availableCredits + '\'' +
//                ", profession='" + profession + '\'' +
//                ", gender='" + gender + '\'' +
//                ", email='" + email + '\'' +
//                ", loginType='" + loginType + '\'' +
//                ", role='" + role + '\'' +
//                ", message='" + message + '\'' +
//                ", errors=" + errors +
//                ", userdata=" + userdata +
//                '}';
//    }
//}
