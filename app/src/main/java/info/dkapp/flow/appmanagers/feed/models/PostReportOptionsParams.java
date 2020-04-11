package info.dkapp.flow.appmanagers.feed.models;

import android.os.Parcel;
import android.os.Parcelable;

public class PostReportOptionsParams implements Parcelable {
    public String memberId;
    public String feedId;
    public String reportReasonId;

    public PostReportOptionsParams() {
    }

    protected PostReportOptionsParams(Parcel in) {
        memberId = in.readString();
        feedId = in.readString();
        reportReasonId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(memberId);
        dest.writeString(feedId);
        dest.writeString(reportReasonId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PostReportOptionsParams> CREATOR = new Creator<PostReportOptionsParams>() {
        @Override
        public PostReportOptionsParams createFromParcel(Parcel in) {
            return new PostReportOptionsParams(in);
        }

        @Override
        public PostReportOptionsParams[] newArray(int size) {
            return new PostReportOptionsParams[size];
        }
    };
}
