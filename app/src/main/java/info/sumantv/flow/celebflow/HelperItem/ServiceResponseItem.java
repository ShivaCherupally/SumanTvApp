package info.sumantv.flow.celebflow.HelperItem;

/**
 * Created by Shiva on 12/27/2017.
 */

public class ServiceResponseItem {
    public String id;
    public String username;
    public String startTime;
    public String endTime;
    public String typeOfEvent;
    public String updated_at;

    /* [{"_id":"5a43794f72256552bad5cd67","username":"soumya","startTime":"2017-12-27T17:12:00.000Z","endTime":"2017-12-27T17:25:00.000Z","typeOfEvent":"Chat","__v":0,"updated_at":"2017-12-27T10:43:27.891Z"}]*/
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTypeOfEvent() {
        return typeOfEvent;
    }

    public void setTypeOfEvent(String typeOfEvent) {
        this.typeOfEvent = typeOfEvent;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
