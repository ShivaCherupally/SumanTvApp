package info.dkapp.flow.utils.expendableRecyclerviewNew;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class PreferenceNewCategory implements ParentListItem, Parcelable {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("preferenceName")
    @Expose
    private String preferenceName;
    @SerializedName("updated_at")
    @Expose
    private Object updatedAt;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("countries")
    @Expose
    private List<String> countries = null;
    @SerializedName("professions")
    @Expose
    private List<String> professions = null;
    @SerializedName("logoURL")
    @Expose
    private String logoURL;
    @SerializedName("parentPreferenceId")
    @Expose
    private Object parentPreferenceId;
    @SerializedName("__v")
    @Expose
    private Integer v;
    @SerializedName("Categories")
    @Expose
    private ArrayList<PreferenceNew> categories = null;


    @SerializedName("isSelectedAll")
    @Expose
    private Boolean isSelected;


    public ArrayList<String> selection;


    public PreferenceNewCategory() {

        selection = new ArrayList<String>();
    }


    public final static Parcelable.Creator<PreferenceNewCategory> CREATOR = new Creator<PreferenceNewCategory>() {


        @SuppressWarnings({
                "unchecked"
        })
        public PreferenceNewCategory createFromParcel(Parcel in) {
            return new PreferenceNewCategory(in);
        }

        public PreferenceNewCategory[] newArray(int size) {
            return (new PreferenceNewCategory[size]);
        }

    };

    protected PreferenceNewCategory(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.preferenceName = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedAt = ((Object) in.readValue((Object.class.getClassLoader())));
        this.createdAt = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.countries, (java.lang.String.class.getClassLoader()));
        in.readList(this.professions, (java.lang.String.class.getClassLoader()));
        this.logoURL = ((String) in.readValue((String.class.getClassLoader())));
        this.parentPreferenceId = ((Object) in.readValue((Object.class.getClassLoader())));
        this.v = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.isSelected = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        in.readList(this.categories, (PreferenceNew.class.getClassLoader()));
    }

    public PreferenceNewCategory(String preferenceName, String imageLogoUrl, ArrayList<PreferenceNew> categories) {
        this.preferenceName = preferenceName;
        this.logoURL = imageLogoUrl;
        this.categories = categories;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPreferenceName() {
        return preferenceName;
    }

    public void setPreferenceName(String preferenceName) {
        this.preferenceName = preferenceName;
    }

    public Object getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Object updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public List<String> getCountries() {
        return countries;
    }

    public void setCountries(List<String> countries) {
        this.countries = countries;
    }

    public List<String> getProfessions() {
        return professions;
    }

    public void setProfessions(List<String> professions) {
        this.professions = professions;
    }

    public String getLogoURL() {
        return logoURL;
    }

    public void setLogoURL(String logoURL) {
        this.logoURL = logoURL;
    }

    public Object getParentPreferenceId() {
        return parentPreferenceId;
    }

    public void setParentPreferenceId(Object parentPreferenceId) {
        this.parentPreferenceId = parentPreferenceId;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public ArrayList<PreferenceNew> getCategories() {
        return categories;
    }

    public Boolean getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(Boolean isSelected) {
        this.isSelected = isSelected;
    }

    public void setCategories(ArrayList<PreferenceNew> categories) {
        this.categories = categories;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(preferenceName);
        dest.writeValue(updatedAt);
        dest.writeValue(createdAt);
        dest.writeList(countries);
        dest.writeList(professions);
        dest.writeValue(logoURL);
        dest.writeValue(parentPreferenceId);
        dest.writeValue(v);
        dest.writeValue(isSelected);
        dest.writeList(categories);
    }

    public int describeContents() {
        return 0;
    }


    public ArrayList<String> getSelection() {
        return selection;
    }

    public void setSelection(ArrayList<String> selection) {
        this.selection = selection;
    }

    public static Creator<PreferenceNewCategory> getCREATOR() {
        return CREATOR;
    }

    @Override
    public List<?> getChildItemList() {
        return categories;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }
}
