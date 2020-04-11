package info.dkapp.flow.bottommenu.menuitemsmanager.modelclasses;

public class ManagerNotifition {

    String managerName;
    String managerType,disc;

    public ManagerNotifition(String managerName, String managerType, String disc) {
        this.managerName = managerName;
        this.managerType = managerType;
        this.disc = disc;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getManagerType() {
        return managerType;
    }

    public void setManagerType(String managerType) {
        this.managerType = managerType;
    }

    public String getDisc() {
        return disc;
    }

    public void setDisc(String disc) {
        this.disc = disc;
    }



}
