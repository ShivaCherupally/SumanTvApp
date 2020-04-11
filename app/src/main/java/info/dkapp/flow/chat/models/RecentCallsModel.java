package info.dkapp.flow.chat.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class RecentCallsModel implements Parcelable {
    public CallListUserModel _id;
    public Integer numberOfCalls;
    public ArrayList<AllCallsHistoryModel> allCallsHistory;
    public AllCallsHistoryModel lastCallStatus;

    protected RecentCallsModel(Parcel in) {
        _id = in.readParcelable(CallListUserModel.class.getClassLoader());
        if (in.readByte() == 0) {
            numberOfCalls = null;
        } else {
            numberOfCalls = in.readInt();
        }
        allCallsHistory = in.createTypedArrayList(AllCallsHistoryModel.CREATOR);
        lastCallStatus = in.readParcelable(AllCallsHistoryModel.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(_id, flags);
        if (numberOfCalls == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(numberOfCalls);
        }
        dest.writeTypedList(allCallsHistory);
        dest.writeParcelable(lastCallStatus, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RecentCallsModel> CREATOR = new Creator<RecentCallsModel>() {
        @Override
        public RecentCallsModel createFromParcel(Parcel in) {
            return new RecentCallsModel(in);
        }

        @Override
        public RecentCallsModel[] newArray(int size) {
            return new RecentCallsModel[size];
        }
    };
}
