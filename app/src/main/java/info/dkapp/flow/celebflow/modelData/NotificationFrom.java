
package info.dkapp.flow.celebflow.modelData;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationFrom implements Parcelable
{

    @SerializedName("_id")
    @Expose
    public String id;
    @SerializedName("lastName")
    @Expose
    public String lastName;
    @SerializedName("firstName")
    @Expose
    public String firstName;
    @SerializedName("avtar_imgPath")
    @Expose
    public String avtarImgPath;
    public final static Creator<NotificationFrom> CREATOR = new Creator<NotificationFrom>() {


        @SuppressWarnings({
            "unchecked"
        })
        public NotificationFrom createFromParcel(Parcel in) {
            return new NotificationFrom(in);
        }

        public NotificationFrom[] newArray(int size) {
            return (new NotificationFrom[size]);
        }

    }
    ;

    protected NotificationFrom(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.lastName = ((String) in.readValue((String.class.getClassLoader())));
        this.firstName = ((String) in.readValue((String.class.getClassLoader())));
        this.avtarImgPath = ((String) in.readValue((String.class.getClassLoader())));
    }

    public NotificationFrom() {
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(lastName);
        dest.writeValue(firstName);
        dest.writeValue(avtarImgPath);
    }

    public int describeContents() {
        return  0;
    }

}
