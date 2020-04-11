package info.sumantv.flow.appmanagers.feed.models;

import android.os.Parcel;
import android.os.Parcelable;

public class CommentReportOptionPostParams implements Parcelable {
    public String commentId;
    public String feedbackPostedBy;
    public String feedbackItemID;
    public String remark;

    public CommentReportOptionPostParams(){}

    protected CommentReportOptionPostParams(Parcel in) {
        commentId = in.readString();
        feedbackPostedBy = in.readString();
        feedbackItemID = in.readString();
        remark = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(commentId);
        dest.writeString(feedbackPostedBy);
        dest.writeString(feedbackItemID);
        dest.writeString(remark);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CommentReportOptionPostParams> CREATOR = new Creator<CommentReportOptionPostParams>() {
        @Override
        public CommentReportOptionPostParams createFromParcel(Parcel in) {
            return new CommentReportOptionPostParams(in);
        }

        @Override
        public CommentReportOptionPostParams[] newArray(int size) {
            return new CommentReportOptionPostParams[size];
        }
    };
}
