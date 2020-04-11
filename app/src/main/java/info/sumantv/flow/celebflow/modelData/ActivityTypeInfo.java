package info.sumantv.flow.celebflow.modelData;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ActivityTypeInfo implements Parcelable
{

    @SerializedName("thirdMessagePart")
    @Expose
    public String thirdMessagePart;
    @SerializedName("secondMessagePart")
    @Expose
    public String secondMessagePart;
    @SerializedName("firstMessagePart")
    @Expose
    public String firstMessagePart;
    @SerializedName("iconUrl")
    @Expose
    public String iconUrl;
    @SerializedName("name")
    @Expose
    public String name;
    public final static Parcelable.Creator<ActivityTypeInfo> CREATOR = new Creator<ActivityTypeInfo>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ActivityTypeInfo createFromParcel(Parcel in) {
            return new ActivityTypeInfo(in);
        }

        public ActivityTypeInfo[] newArray(int size) {
            return (new ActivityTypeInfo[size]);
        }

    }
            ;

    protected ActivityTypeInfo(Parcel in) {
        this.thirdMessagePart = ((String) in.readValue((String.class.getClassLoader())));
        this.secondMessagePart = ((String) in.readValue((String.class.getClassLoader())));
        this.firstMessagePart = ((String) in.readValue((String.class.getClassLoader())));
        this.iconUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
    }

    public ActivityTypeInfo() {
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(thirdMessagePart);
        dest.writeValue(secondMessagePart);
        dest.writeValue(firstMessagePart);
        dest.writeValue(iconUrl);
        dest.writeValue(name);
    }

    public int describeContents() {
        return 0;
    }

}
