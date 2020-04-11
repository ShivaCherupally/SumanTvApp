package info.sumantv.flow.celebflow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> cricket = new ArrayList<String>();
        cricket.add("Cricket");
        cricket.add("FootBall");
        cricket.add("Hockey");
        cricket.add("Tennis");
        cricket.add("BasketBall");

        List<String> football = new ArrayList<String>();
        football.add("TollyWood");
        football.add("BollyWood");
        football.add("HollyWood");

        expandableListDetail.put("Sports", cricket);
        expandableListDetail.put("PreferenceNew", football);
        return expandableListDetail;
    }
}
