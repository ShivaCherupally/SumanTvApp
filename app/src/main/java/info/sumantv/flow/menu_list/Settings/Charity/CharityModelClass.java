package info.sumantv.flow.menu_list.Settings.Charity;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Chenna on 15-05-2018.
 */

public class CharityModelClass {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("memberId")
    @Expose
    private String memberId;
    @SerializedName("charityName")
    @Expose
    private String charityName;
    @SerializedName("charityRegistrationID")
    @Expose
    private String charityRegistrationID;
    @SerializedName("routingNo")
    @Expose
    private Integer routingNo;
    @SerializedName("accountNo")
    @Expose
    private Integer accountNo;
    @SerializedName("__v")
    @Expose
    private Integer v;
    @SerializedName("updatedAt")
    @Expose
    private Object updatedAt;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("payoutSettings")
    @Expose
    private List<PayoutSetting> payoutSettings = null;
    @SerializedName("charityTrustDesc")
    @Expose
    private String charityTrustDesc;
    @SerializedName("swiftOrIfscCode")
    @Expose
    private String swiftOrIfscCode;
    @SerializedName("branchName")
    @Expose
    private String branchName;
    @SerializedName("beneficiaryName")
    @Expose
    private String beneficiaryName;
    @SerializedName("bankName")
    @Expose
    private String bankName;
    @SerializedName("certificateURL")
    @Expose
    private String certificateURL;
    @SerializedName("address")
    @Expose
    private String address;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getCharityName() {
        return charityName;
    }

    public void setCharityName(String charityName) {
        this.charityName = charityName;
    }

    public String getCharityRegistrationID() {
        return charityRegistrationID;
    }

    public void setCharityRegistrationID(String charityRegistrationID) {
        this.charityRegistrationID = charityRegistrationID;
    }

    public Integer getRoutingNo() {
        return routingNo;
    }

    public void setRoutingNo(Integer routingNo) {
        this.routingNo = routingNo;
    }

    public Integer getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(Integer accountNo) {
        this.accountNo = accountNo;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
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

    public List<PayoutSetting> getPayoutSettings() {
        return payoutSettings;
    }

    public void setPayoutSettings(List<PayoutSetting> payoutSettings) {
        this.payoutSettings = payoutSettings;
    }

    public String getCharityTrustDesc() {
        return charityTrustDesc;
    }

    public void setCharityTrustDesc(String charityTrustDesc) {
        this.charityTrustDesc = charityTrustDesc;
    }

    public String getSwiftOrIfscCode() {
        return swiftOrIfscCode;
    }

    public void setSwiftOrIfscCode(String swiftOrIfscCode) {
        this.swiftOrIfscCode = swiftOrIfscCode;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBeneficiaryName() {
        return beneficiaryName;
    }

    public void setBeneficiaryName(String beneficiaryName) {
        this.beneficiaryName = beneficiaryName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCertificateURL() {
        return certificateURL;
    }

    public void setCertificateURL(String certificateURL) {
        this.certificateURL = certificateURL;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
