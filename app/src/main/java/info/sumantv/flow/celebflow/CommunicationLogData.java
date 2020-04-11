package info.sumantv.flow.celebflow;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Shiva on 2/28/2018.
 */

public class CommunicationLogData {

    @SerializedName("message")
    private String message;

    @SerializedName("event")
    private String event;

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    @SerializedName("mode_ids")
    private ArrayList<String> mode_ids;

    public ArrayList<String> getMode_ids() {
        return mode_ids;
    }

    public void setMode_ids(ArrayList<String> mode_ids) {
        this.mode_ids = mode_ids;
    }

    @SerializedName("to_addr")
    private String to_addr;


    @SerializedName("content")
    private String content;

    public String getTo_addr() {
        return to_addr;
    }

    public void setTo_addr(String to_addr) {
        this.to_addr = to_addr;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public CommunicationLogData(ArrayList<String> mode_ids, String to_addr,
                                String content) {
        this.mode_ids = mode_ids;
        this.to_addr = to_addr;
        this.content = content;
        Log.e("paramsSend", toString() + "");
    }

    public CommunicationLogData(String event, ArrayList<String> mode_ids, String to_addr, String content) {
        this.event = event;
        this.mode_ids = mode_ids;
        this.to_addr = to_addr;
        this.content = content;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "CommunicationLogData{" +
                "mode_ids=" + mode_ids +
                ", to_addr='" + to_addr + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
