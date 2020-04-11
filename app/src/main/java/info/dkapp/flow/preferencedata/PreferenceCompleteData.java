package info.dkapp.flow.preferencedata;

/**
 * Created by user on 3/17/2018.
 */

public class PreferenceCompleteData {

    public String _id;
    public String parentPreferenceId;
    public String logoURL;
    public String preferenceName;
    public String created_at;
    public String updated_at;
    public Boolean isChecked = false;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getParentPreferenceId() {
        return parentPreferenceId;
    }

    public void setParentPreferenceId(String parentPreferenceId) {
        this.parentPreferenceId = parentPreferenceId;
    }

    public String getLogoURL() {
        return logoURL;
    }

    public void setLogoURL(String logoURL) {
        this.logoURL = logoURL;
    }

    public String getPreferenceName() {
        return preferenceName;
    }

    public void setPreferenceName(String preferenceName) {
        this.preferenceName = preferenceName;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }

    public PreferenceCompleteData(String _id, String parentPreferenceId, String logoURL, String preferenceName) {
        this._id = _id;
        this.parentPreferenceId = parentPreferenceId;
        this.logoURL = logoURL;
        this.preferenceName = preferenceName;
    }
}
