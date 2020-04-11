package info.sumantv.flow.bottommenu.preferencemanager.newpreferene;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Uday Kumay Vurukonda on 11/30/2018.
 * <p>
 * Mr.Psycho
 */
public class SendDataModel {


    @SerializedName("userId")
    @Expose
    String  userId;

    @SerializedName("preferences")
    @Expose
    List<String> preferences;

    public SendDataModel(String userId, List<String> preferences) {
        this.userId = userId;
        this.preferences = preferences;
    }


}
