package info.sumantv.flow.bottommenu.menuitemsmanager.modelclasses;

public class PermissionView  {

    String celebName,accessStatus,permissions;

    public PermissionView() {
    }

    public PermissionView(String celebName, String accessStatus, String permissions) {
        this.celebName = celebName;
        this.accessStatus = accessStatus;
        this.permissions = permissions;
    }

    public String getCelebName() {
        return celebName;
    }

    public void setCelebName(String celebName) {
        this.celebName = celebName;
    }

    public String getAccessStatus() {
        return accessStatus;
    }

    public void setAccessStatus(String accessStatus) {
        this.accessStatus = accessStatus;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }
}
