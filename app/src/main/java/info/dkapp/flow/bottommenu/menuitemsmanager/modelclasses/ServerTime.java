package info.dkapp.flow.bottommenu.menuitemsmanager.modelclasses;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Uday Kumay Vurukonda on 26-Nov-19.
 * <p>
 * Mr.Psycho
 */
public class ServerTime implements Parcelable
{

    @SerializedName("currentTime")
    @Expose
    public String currentTime;
    public final static Parcelable.Creator<ServerTime> CREATOR = new Creator<ServerTime>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ServerTime createFromParcel(Parcel in) {
            return new ServerTime(in);
        }

        public ServerTime[] newArray(int size) {
            return (new ServerTime[size]);
        }

    }
            ;

    protected ServerTime(Parcel in) {
        this.currentTime = ((String) in.readValue((String.class.getClassLoader())));
    }

    public ServerTime() {
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(currentTime);
    }

    public int describeContents() {
        return 0;
    }

}
