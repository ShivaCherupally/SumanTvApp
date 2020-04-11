package info.sumantv.flow.ModelClass;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Uday Kumay Vurukonda on 26-Aug-19.
 * <p>
 * Mr.Psycho
 */
public class ContractCreditsData implements Parcelable {

    @SerializedName("chat")
    @Expose
    public Integer chat;
    @SerializedName("audio")
    @Expose
    public Integer audio;
    @SerializedName("video")
    @Expose
    public Integer video;
    @SerializedName("fan")
    @Expose
    public Integer fan;

    @SerializedName("schedules")
    @Expose
    public boolean schedules;


    protected ContractCreditsData(Parcel in) {
        if (in.readByte() == 0) {
            chat = null;
        } else {
            chat = in.readInt();
        }
        if (in.readByte() == 0) {
            audio = null;
        } else {
            audio = in.readInt();
        }
        if (in.readByte() == 0) {
            video = null;
        } else {
            video = in.readInt();
        }
        if (in.readByte() == 0) {
            fan = null;
        } else {
            fan = in.readInt();
        }
        schedules = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (chat == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(chat);
        }
        if (audio == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(audio);
        }
        if (video == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(video);
        }
        if (fan == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(fan);
        }
        dest.writeByte((byte) (schedules ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ContractCreditsData> CREATOR = new Creator<ContractCreditsData>() {
        @Override
        public ContractCreditsData createFromParcel(Parcel in) {
            return new ContractCreditsData(in);
        }

        @Override
        public ContractCreditsData[] newArray(int size) {
            return new ContractCreditsData[size];
        }
    };
}
