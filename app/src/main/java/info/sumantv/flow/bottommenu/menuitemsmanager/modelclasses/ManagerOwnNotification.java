package info.sumantv.flow.bottommenu.menuitemsmanager.modelclasses;

public class ManagerOwnNotification {

    String managerName;
    String managerExp;
    String managerId;
    String reqStatus;



    String rejectStatus;
    int managerPic;

    public ManagerOwnNotification(String managerName, String managerExp, String managerId, int managerPic) {
        this.managerName = managerName;
        this.managerExp = managerExp;
        this.managerId = managerId;
        this.managerPic = managerPic;
    }

    public ManagerOwnNotification(String managerName, int managerPic) {
        this.managerName = managerName;
        this.managerPic = managerPic;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getManagerExp() {
        return managerExp;
    }

    public void setManagerExp(String managerExp) {
        this.managerExp = managerExp;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public int getManagerPic() {
        return managerPic;
    }

    public void setManagerPic(int managerPic) {
        this.managerPic = managerPic;
    }
    public String getReqStatus() {
        return reqStatus;
    }

    public void setReqStatus(String reqStatus) {
        this.reqStatus = reqStatus;
    }

    public String getRejectStatus() {
        return rejectStatus;
    }

    public void setRejectStatus(String rejectStatus) {
        this.rejectStatus = rejectStatus;
    }
}
