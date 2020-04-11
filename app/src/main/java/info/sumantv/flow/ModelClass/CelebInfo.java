package info.sumantv.flow.ModelClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Uday Kumay Vurukonda on 2/5/2019.
 * <p>
 * Mr.Psycho
 */
public class CelebInfo {

    @SerializedName("firstName")
    @Expose
    private String firstName;


    @SerializedName("_id")
    @Expose
    private String _id;


    @SerializedName("lastName")
    @Expose
    private String lastName;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
