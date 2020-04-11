
package info.sumantv.flow.userflow.UserActivitys.LoginActivity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

public class CelebritiesWorkedFor implements Parcelable
{

    private String id;
    private String experienceInMonths;
    private String experienceInYear;
    private String celebrityName;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    public final static Creator<CelebritiesWorkedFor> CREATOR = new Creator<CelebritiesWorkedFor>() {


        @SuppressWarnings({
            "unchecked"
        })
        public CelebritiesWorkedFor createFromParcel(Parcel in) {
            return new CelebritiesWorkedFor(in);
        }

        public CelebritiesWorkedFor[] newArray(int size) {
            return (new CelebritiesWorkedFor[size]);
        }

    }
    ;

    protected CelebritiesWorkedFor(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.experienceInMonths = ((String) in.readValue((String.class.getClassLoader())));
        this.experienceInYear = ((String) in.readValue((String.class.getClassLoader())));
        this.celebrityName = ((String) in.readValue((String.class.getClassLoader())));
        this.additionalProperties = ((Map<String, Object> ) in.readValue((Map.class.getClassLoader())));
    }

    public CelebritiesWorkedFor() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExperienceInMonths() {
        return experienceInMonths;
    }

    public void setExperienceInMonths(String experienceInMonths) {
        this.experienceInMonths = experienceInMonths;
    }

    public String getExperienceInYear() {
        return experienceInYear;
    }

    public void setExperienceInYear(String experienceInYear) {
        this.experienceInYear = experienceInYear;
    }

    public String getCelebrityName() {
        return celebrityName;
    }

    public void setCelebrityName(String celebrityName) {
        this.celebrityName = celebrityName;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(experienceInMonths);
        dest.writeValue(experienceInYear);
        dest.writeValue(celebrityName);
        dest.writeValue(additionalProperties);
    }

    public int describeContents() {
        return  0;
    }

}
