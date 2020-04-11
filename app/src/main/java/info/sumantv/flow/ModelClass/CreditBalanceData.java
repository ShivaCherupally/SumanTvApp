package info.sumantv.flow.ModelClass;

import com.google.gson.annotations.SerializedName;

public class CreditBalanceData {


    @SerializedName("memberId")
    private String memberId;

    @SerializedName("celebCredits")
    private String celebCredits;

    @SerializedName("cumulativeCreditValue")
    private Double cumulativeCreditValue;

    @SerializedName("referralCreditValue")
    private Double referralCreditValue;


    public String getCelebCredits() {
        return celebCredits;
    }

    public void setCelebCredits(String celebCredits) {
        this.celebCredits = celebCredits;
    }


    public Double getCumulativeCreditValue() {
        return cumulativeCreditValue;
    }

    public void setCumulativeCreditValue(Double cumulativeCreditValue) {
        this.cumulativeCreditValue = cumulativeCreditValue;
    }

    public Double getReferralCreditValue() {
        return referralCreditValue;
    }

    public void setReferralCreditValue(Double referralCreditValue) {
        this.referralCreditValue = referralCreditValue;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
}
