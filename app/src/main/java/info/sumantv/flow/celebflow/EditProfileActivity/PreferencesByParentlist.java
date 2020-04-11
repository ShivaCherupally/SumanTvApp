//package info.celebkonnect.flow.celebflow.EditProfileActivity;
//
//import android.os.Parcel;
//import android.os.Parcelable;
//import com.google.gson.annotations.Expose;
//import com.google.gson.annotations.SerializedName;
//
//import java.util.List;
//
///**
// * Created by Uday Kumay Vurukonda on 12-Feb-19.
// * <p>
// * Mr.Psycho
// */
//public class PreferencesByParentlist implements Parcelable
//{
//
//    @SerializedName("preferenceName")
//    @Expose
//    public String preferenceName;
//    @SerializedName("professions")
//    @Expose
//    public List<String> professions = null;
//
//    public final static Parcelable.Creator<PreferencesByParentlist> CREATOR = new Creator<PreferencesByParentlist>() {
//
//
//        @SuppressWarnings({
//                "unchecked"
//        })
//        public PreferencesByParentlist createFromParcel(Parcel in) {
//            return new PreferencesByParentlist(in);
//        }
//
//        public PreferencesByParentlist[] newArray(int size) {
//            return (new PreferencesByParentlist[size]);
//        }
//
//    }
//            ;
//
//    protected PreferencesByParentlist(Parcel in) {
//        this.preferenceName = ((String) in.readValue((String.class.getClassLoader())));
//        in.readList(this.professions, (java.lang.String.class.getClassLoader()));
//    }
//
//    public PreferencesByParentlist() {
//    }
//
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeValue(preferenceName);
//        dest.writeList(professions);
//    }
//
//    public int describeContents() {
//        return 0;
//    }
//
//}
