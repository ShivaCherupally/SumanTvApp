package info.dkapp.flow.ipoll.interfaces.feeds.comments;


import info.dkapp.flow.appmanagers.feed.models.Comment;
import info.dkapp.flow.appmanagers.feed.models.CommentReportOption;
import info.dkapp.flow.appmanagers.feed.models.Profile;

/**
 * Created by User on 03-08-2018.
 **/

public interface ICommentsAdapter
{
    public void onCommentClick(Comment comment, int position);
    public void onProfileClick(Comment comment, int position);
    void selfProfile(Profile like);
    void celebUserProfile(Profile profile);
    public void retryLoadMore();
    void editComment(Comment comments,int position);
    void deleteComments(Comment comments, int position);
    void reportComments(Comment comments, int position);
    void reportSubmit(Comment comments, CommentReportOption commentReportOption,String remark);
}
