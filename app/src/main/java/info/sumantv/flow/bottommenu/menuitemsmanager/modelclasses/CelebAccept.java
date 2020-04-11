package info.sumantv.flow.bottommenu.menuitemsmanager.modelclasses;

import com.google.gson.annotations.SerializedName;

public class CelebAccept {

    @SerializedName("mode")
    private String mode;

    @SerializedName("updatedBy")
    private String updatedBy;

    @SerializedName("message")
    private String message;

    @SerializedName("error")
    private String error;

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public CelebAccept(String mode, String updatedBy) {
        this.mode = mode;
        this.updatedBy = updatedBy;
    }
}
