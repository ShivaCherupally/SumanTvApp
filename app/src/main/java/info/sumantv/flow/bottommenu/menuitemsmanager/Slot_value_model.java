package info.sumantv.flow.bottommenu.menuitemsmanager;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Uday Kumay Vurukonda on 16-Apr-19.
 * <p>
 * Mr.Psycho
 */
public class Slot_value_model implements Parcelable {

    public String value;
    public Boolean isSelect;

    public  Slot_value_model (){
    }

    public  Slot_value_model (String value, Boolean isSelect){
        this.isSelect = isSelect;
        this.value = value;
    }
    protected Slot_value_model(Parcel in) {
        value = in.readString();
        byte tmpIsSelect = in.readByte();
        isSelect = tmpIsSelect == 0 ? null : tmpIsSelect == 1;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(value);
        dest.writeByte((byte) (isSelect == null ? 0 : isSelect ? 1 : 2));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Slot_value_model> CREATOR = new Creator<Slot_value_model>() {
        @Override
        public Slot_value_model createFromParcel(Parcel in) {
            return new Slot_value_model(in);
        }

        @Override
        public Slot_value_model[] newArray(int size) {
            return new Slot_value_model[size];
        }
    };
}
