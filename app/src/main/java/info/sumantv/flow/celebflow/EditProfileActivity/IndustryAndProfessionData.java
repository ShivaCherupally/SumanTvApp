package info.sumantv.flow.celebflow.EditProfileActivity;

import java.util.ArrayList;

/**
 * Created by Shiva on 3/28/2018.
 */

public class IndustryAndProfessionData {
    String preferenceName;

    ArrayList<String> professions;
    ArrayList<CategoriesPreference> categories;

    public String getPreferenceName() {
        return preferenceName;
    }

    public void setPreferenceName(String preferenceName) {
        this.preferenceName = preferenceName;
    }

    public ArrayList<String> getProfessions() {
        return professions;
    }

    public void setProfessions(ArrayList<String> professions) {
        this.professions = professions;
    }

    public ArrayList<CategoriesPreference> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<CategoriesPreference> categories) {
        this.categories = categories;
    }
}
