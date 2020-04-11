package info.dkapp.flow.singup;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.File;

/**
 * Created by Uday Kumay Vurukonda on 24-Apr-19.
 * <p>
 * Mr.Psycho
 */
public class RegistrationMemberImage implements Parcelable
{

    @SerializedName("profile")
    @Expose
    public Profile profile;

    @SerializedName("imageRatio")
    @Expose
    public float imageRatio;

    @SerializedName("image")
    @Expose
    public File image;

    @SerializedName("mode")
    @Expose
    public String mode;




    public boolean isUserName;

      public RegistrationMemberImage(Profile profile,
                                     File image,float imageRatio) {
        this.profile = profile;
        this.imageRatio = imageRatio;
        this.image = image;

    }

    public RegistrationMemberImage(Profile profile ,boolean isUserName) {
        this.profile = profile;
        this.isUserName = isUserName;

    }

    public RegistrationMemberImage(Profile profile, String mode) {
        this.profile = profile;
        this.isUserName = isUserName;
        this.mode = mode;

    }


    protected RegistrationMemberImage(Parcel in) {
        profile = in.readParcelable(Profile.class.getClassLoader());
        imageRatio = in.readFloat();
        mode = in.readString();
        isUserName = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(profile, flags);
        dest.writeFloat(imageRatio);
        dest.writeString(mode);
        dest.writeByte((byte) (isUserName ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RegistrationMemberImage> CREATOR = new Creator<RegistrationMemberImage>() {
        @Override
        public RegistrationMemberImage createFromParcel(Parcel in) {
            return new RegistrationMemberImage(in);
        }

        @Override
        public RegistrationMemberImage[] newArray(int size) {
            return new RegistrationMemberImage[size];
        }
    };
}
