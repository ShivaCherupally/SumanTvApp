package info.dkapp.flow.bottommenu.models;

/**
 * Created by Uday Kumay Vurukonda on 12/17/2018.
 * <p>
 * Mr.Psycho
 */

import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EditProfileUserDetails implements Parcelable {

    @SerializedName("userDetails")
    @Expose
    public UserDetails userDetails;

    @SerializedName("credits")
    @Expose
    public Credits credits;

    @SerializedName("fanFollowingFollowerFeedCount")
    @Expose
    public FanFollowingFollowerFeedCount fanFollowingFollowerFeedCount;

    @SerializedName("creditDetails")
    @Expose
    public CreditDetails creditDetails;

    @SerializedName("celebContracts")
    @Expose
    public List<CelebContract> celebContracts = null;




    protected EditProfileUserDetails(Parcel in) {
        userDetails = in.readParcelable(UserDetails.class.getClassLoader());
        credits = in.readParcelable(Credits.class.getClassLoader());
        fanFollowingFollowerFeedCount = in.readParcelable(FanFollowingFollowerFeedCount.class.getClassLoader());
        creditDetails = in.readParcelable(CreditDetails.class.getClassLoader());
        celebContracts = in.createTypedArrayList(CelebContract.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(userDetails, flags);
        dest.writeParcelable(credits, flags);
        dest.writeParcelable(fanFollowingFollowerFeedCount, flags);
        dest.writeParcelable(creditDetails, flags);
        dest.writeTypedList(celebContracts);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<EditProfileUserDetails> CREATOR = new Creator<EditProfileUserDetails>() {
        @Override
        public EditProfileUserDetails createFromParcel(Parcel in) {
            return new EditProfileUserDetails(in);
        }

        @Override
        public EditProfileUserDetails[] newArray(int size) {
            return new EditProfileUserDetails[size];
        }
    };

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public Credits getCredits() {
        return credits;
    }

    public void setCredits(Credits credits) {
        this.credits = credits;
    }

    public FanFollowingFollowerFeedCount getFanFollowingFollowerFeedCount() {
        return fanFollowingFollowerFeedCount;
    }

    public void setFanFollowingFollowerFeedCount(FanFollowingFollowerFeedCount fanFollowingFollowerFeedCount) {
        this.fanFollowingFollowerFeedCount = fanFollowingFollowerFeedCount;
    }

    public CreditDetails getCreditDetails() {
        return creditDetails;
    }

    public void setCreditDetails(CreditDetails creditDetails) {
        this.creditDetails = creditDetails;
    }

    public List<CelebContract> getCelebContracts() {
        return celebContracts;
    }

    public void setCelebContracts(List<CelebContract> celebContracts) {
        this.celebContracts = celebContracts;
    }

    public static Creator<EditProfileUserDetails> getCREATOR() {
        return CREATOR;
    }
}
