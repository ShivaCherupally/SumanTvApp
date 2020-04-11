package info.dkapp.flow.celebflow;

/**
 * Created by user on 3/19/2018.
 */


/**
 * Created by Praveen Lakkireddy on 15-03-2018.
 */

public class MySchedules {


    String startTime;
    String duration;
    String chatType;
    String endTime;
    String createdAt;
    String avtar_imgPath;
    String serviceSchduleStatus;
    String user_name;
    String user_lastname;

    public String getServiceSchduleStatus() {
        return serviceSchduleStatus;
    }

    public void setServiceSchduleStatus(String serviceSchduleStatus) {
        this.serviceSchduleStatus = serviceSchduleStatus;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_lastname() {
        return user_lastname;
    }

    public void setUser_lastname(String user_lastname) {
        this.user_lastname = user_lastname;
    }

    public String getAvtar_imgPath() {
        return avtar_imgPath;
    }

    public void setAvtar_imgPath(String avtar_imgPath) {
        this.avtar_imgPath = avtar_imgPath;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getChatType() {
        return chatType;
    }

    public void setChatType(String chatType) {
        this.chatType = chatType;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    //
//    public MySchedules(String day, String date, String time, String duration) {
//
//        this.duration = duration;
//    }
}
