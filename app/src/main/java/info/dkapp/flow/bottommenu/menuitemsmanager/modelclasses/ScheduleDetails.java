package info.dkapp.flow.bottommenu.menuitemsmanager.modelclasses;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Uday Kumay Vurukonda on 20-Sep-19.
 * <p>
 * Mr.Psycho
 */
public class ScheduleDetails implements Parcelable {

    public CalenderSlot slotDetails;
    public Integer availableSlotsCount;
    public Integer totalSlots;
    public Boolean isBooked;
    public Boolean isDeleted;
    public Boolean isFan;
    public Integer totalSlotVal;


    protected ScheduleDetails(Parcel in) {
        slotDetails = in.readParcelable(CalenderSlot.class.getClassLoader());
        if (in.readByte() == 0) {
            availableSlotsCount = null;
        } else {
            availableSlotsCount = in.readInt();
        }
        if (in.readByte() == 0) {
            totalSlots = null;
        } else {
            totalSlots = in.readInt();
        }
        byte tmpIsBooked = in.readByte();
        isBooked = tmpIsBooked == 0 ? null : tmpIsBooked == 1;
        byte tmpIsDeleted = in.readByte();
        isDeleted = tmpIsDeleted == 0 ? null : tmpIsDeleted == 1;
        byte tmpIsFan = in.readByte();
        isFan = tmpIsFan == 0 ? null : tmpIsFan == 1;
        if (in.readByte() == 0) {
            totalSlotVal = null;
        } else {
            totalSlotVal = in.readInt();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(slotDetails, flags);
        if (availableSlotsCount == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(availableSlotsCount);
        }
        if (totalSlots == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(totalSlots);
        }
        dest.writeByte((byte) (isBooked == null ? 0 : isBooked ? 1 : 2));
        dest.writeByte((byte) (isDeleted == null ? 0 : isDeleted ? 1 : 2));
        dest.writeByte((byte) (isFan == null ? 0 : isFan ? 1 : 2));
        if (totalSlotVal == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(totalSlotVal);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ScheduleDetails> CREATOR = new Creator<ScheduleDetails>() {
        @Override
        public ScheduleDetails createFromParcel(Parcel in) {
            return new ScheduleDetails(in);
        }

        @Override
        public ScheduleDetails[] newArray(int size) {
            return new ScheduleDetails[size];
        }
    };
}
