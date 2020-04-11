package info.dkapp.flow.appmanagers.feed.models;

import android.os.Parcel;
import android.os.Parcelable;

public class CommentReportOption implements Parcelable {
    public String _id;
    public String feedbackItem;

    public CommentReportOption(){}

    protected CommentReportOption(Parcel in) {
        _id = in.readString();
        feedbackItem = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(feedbackItem);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CommentReportOption> CREATOR = new Creator<CommentReportOption>() {
        @Override
        public CommentReportOption createFromParcel(Parcel in) {
            return new CommentReportOption(in);
        }

        @Override
        public CommentReportOption[] newArray(int size) {
            return new CommentReportOption[size];
        }
    };
}
