package info.dkapp.flow.celebflow.EditProfileActivity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Uday Kumay Vurukonda on 16-Sep-19.
 * <p>
 * Mr.Psycho
 */
public class CategoriesPreference implements Parcelable {

    public String _id;
    public String preferenceName;

    public CategoriesPreference(String _id, String preferenceName) {
        this._id = _id;
        this.preferenceName = preferenceName;
    }

    protected CategoriesPreference(Parcel in) {
        _id = in.readString();
        preferenceName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(preferenceName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CategoriesPreference> CREATOR = new Creator<CategoriesPreference>() {
        @Override
        public CategoriesPreference createFromParcel(Parcel in) {
            return new CategoriesPreference(in);
        }

        @Override
        public CategoriesPreference[] newArray(int size) {
            return new CategoriesPreference[size];
        }
    };
}
