package info.sumantv.flow.celebflow.Fragments.model;

import android.os.Parcel;
import android.os.Parcelable;
import info.sumantv.flow.bottommenu.models.MemberMedium;
import info.sumantv.flow.bottommenu.models.UserDetails;

public class CelebUserMedia implements Parcelable {
    public UserDetails userDetails;
    public MemberMedium memberMedia;

    protected CelebUserMedia(Parcel in) {
        userDetails = in.readParcelable(UserDetails.class.getClassLoader());
        memberMedia = in.readParcelable(MemberMedium.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(userDetails, flags);
        dest.writeParcelable(memberMedia, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CelebUserMedia> CREATOR = new Creator<CelebUserMedia>() {
        @Override
        public CelebUserMedia createFromParcel(Parcel in) {
            return new CelebUserMedia(in);
        }

        @Override
        public CelebUserMedia[] newArray(int size) {
            return new CelebUserMedia[size];
        }
    };
}
