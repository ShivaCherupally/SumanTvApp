package info.dkapp.flow.celebflow.HelperItem;

import java.util.Date;

/**
 * Created by Shiva on 1/8/2018.
 */

public class ChatMessage {

    private String messageText;
    private String messageUser;
    private long messageTime;
    private String sendername;
    private String receivername;
    private String meuuid;
    private String frienduuid;
    private String msgtime;

    public String getCelebmsg() {
        return celebmsg;
    }

    public void setCelebmsg(String celebmsg) {
        this.celebmsg = celebmsg;
    }

    private String celebmsg;

    public String getMeprofilepic() {
        return meprofilepic;
    }

    public void setMeprofilepic(String meprofilepic) {
        this.meprofilepic = meprofilepic;
    }

    public String getFriendprofilepic() {
        return friendprofilepic;
    }

    public void setFriendprofilepic(String friendprofilepic) {
        this.friendprofilepic = friendprofilepic;
    }

    private String meprofilepic,friendprofilepic;
    public ChatMessage(String messageText, String messageUser, String sendername) {
        this.messageText = messageText;
        this.messageUser = messageUser;
        messageTime = new Date().getTime();
        this.sendername = sendername;
    }

    public ChatMessage() {

    }

    public String getSendername() {
        return sendername;
    }

    public void setSendername(String sendername) {
        this.sendername = sendername;
    }

    public String getReceivername() {
        return receivername;
    }

    public void setReceivername(String receivername) {
        this.receivername = receivername;
    }

    public String getMeuuid() {
        return meuuid;
    }

    public void setMeuuid(String meuuid) {
        this.meuuid = meuuid;
    }

    public String getFrienduuid() {
        return frienduuid;
    }

    public void setFrienduuid(String frienduuid) {
        this.frienduuid = frienduuid;
    }

    public String getMsgtime() {
        return msgtime;
    }

    public void setMsgtime(String msgtime) {
        this.msgtime = msgtime;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageUser() {
        return messageUser;
    }

    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }

    public String getSender() {
        return messageUser;
    }

    public void setSender(String messageUser) {
        this.messageUser = messageUser;
    }
}
