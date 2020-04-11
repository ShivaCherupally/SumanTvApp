package info.sumantv.flow.retrofitcall;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AwardsModel  {

    @SerializedName("awardTypeName")
    @Expose
    private String awardTypeName;
    @SerializedName("_id")
    @Expose
    private String id;


    public String getAwardTypeName() {
        return awardTypeName;
    }

    public void setAwardTypeName(String awardTypeName) {
        this.awardTypeName = awardTypeName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
