
package info.sumantv.flow.ModelClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FanSuccessData implements Serializable {

    @SerializedName("creditInfo")
    @Expose
    public CreditInfoData creditInfo;

    @SerializedName("celebInfo")
    @Expose
    public CelebInfo celebInfo;



    public CreditInfoData getCreditInfo() {
        return creditInfo;
    }

    public void setCreditInfo(CreditInfoData creditInfo) {
        this.creditInfo = creditInfo;
    }

    public CelebInfo getCelebInfo() {
        return celebInfo;
    }

    public void setCelebInfo(CelebInfo celebInfo) {
        this.celebInfo = celebInfo;
    }
}
