package info.sumantv.flow.utils.expendableRecyclerviewNew;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PreferenceNew  implements Parcelable
    {

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
        @SerializedName("logoURL")
        @Expose
        private String logoURL;
        @SerializedName("parentPreferenceId")
        @Expose
        private String parentPreferenceId;
        @SerializedName("__v")
        @Expose
        private Integer v;
        @SerializedName("isSelected")
        @Expose
        private Boolean isSelected;
        @SerializedName("professions")
        @Expose
        private List<String> professions = null;
        public final static Parcelable.Creator<PreferenceNew> CREATOR = new Creator<PreferenceNew>() {


            @SuppressWarnings({
                    "unchecked"
            })
            public PreferenceNew createFromParcel(Parcel in) {
                return new PreferenceNew(in);
            }

            public PreferenceNew[] newArray(int size) {
                return (new PreferenceNew[size]);
            }

        };

        public  PreferenceNew (String preferenceName){
            this.preferenceName = preferenceName;
        }
        protected PreferenceNew(Parcel in) {
            this.id = ((String) in.readValue((String.class.getClassLoader())));
            this.preferenceName = ((String) in.readValue((String.class.getClassLoader())));
            this.updatedAt = ((Object) in.readValue((Object.class.getClassLoader())));
            this.createdAt = ((String) in.readValue((String.class.getClassLoader())));
            in.readList(this.countries, (java.lang.String.class.getClassLoader()));
            this.logoURL = ((String) in.readValue((String.class.getClassLoader())));
            this.parentPreferenceId = ((String) in.readValue((String.class.getClassLoader())));
            this.v = ((Integer) in.readValue((Integer.class.getClassLoader())));
            this.isSelected = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
            in.readList(this.professions, (java.lang.String.class.getClassLoader()));
        }

        public PreferenceNew() {
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

        public String getLogoURL() {
            return logoURL;
        }

        public void setLogoURL(String logoURL) {
            this.logoURL = logoURL;
        }

        public String getParentPreferenceId() {
            return parentPreferenceId;
        }

        public void setParentPreferenceId(String parentPreferenceId) {
            this.parentPreferenceId = parentPreferenceId;
        }

        public Integer getV() {
            return v;
        }

        public void setV(Integer v) {
            this.v = v;
        }

        public Boolean getIsSelected() {
            return isSelected;
        }

        public void setIsSelected(Boolean isSelected) {
            this.isSelected = isSelected;
        }

        public List<String> getProfessions() {
            return professions;
        }

        public void setProfessions(List<String> professions) {
            this.professions = professions;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeValue(id);
            dest.writeValue(preferenceName);
            dest.writeValue(updatedAt);
            dest.writeValue(createdAt);
            dest.writeList(countries);
            dest.writeValue(logoURL);
            dest.writeValue(parentPreferenceId);
            dest.writeValue(v);
            dest.writeValue(isSelected);
            dest.writeList(professions);
        }

        public int describeContents() {
            return  0;
        }

    }


