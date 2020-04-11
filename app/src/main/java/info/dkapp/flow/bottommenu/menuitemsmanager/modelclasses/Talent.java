package info.dkapp.flow.bottommenu.menuitemsmanager.modelclasses;

public class Talent {

        int image;
        String celebName,access,manager,permission;



    public Talent() {
    }

    public Talent(int image, String celebName, String access, String manager,String permission) {
        this.image = image;
        this.celebName = celebName;
        this.access = access;
        this.manager = manager;
        this.permission = permission;
    }
    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getCelebName() {
        return celebName;
    }

    public void setCelebName(String celebName) {
        this.celebName = celebName;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }
}
