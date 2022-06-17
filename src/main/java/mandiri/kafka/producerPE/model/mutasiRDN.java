package mandiri.kafka.producerPE.model;

public class mutasiRDN {

    private String extref;
    private String accountNumber;
    private String currency;
    private String valueDate;
    private String openBalance;
    private String closeBalance;
    private String note;
    private String seqnum;
    private String mutasiCode;
    private String trxType;
    private String trxAmount;
    private String trxDescription;
    private String referenceRemark;
    private String destinationUrl;

    public mutasiRDN() {
    }

    public mutasiRDN(String extref, String accountNumber, String currency, String valueDate, String openBalance, String closeBalance, String note, String seqnum, String mutasiCode, String trxType, String trxAmount, String trxDescription, String referenceRemark, String destinationUrl) {
        this.extref = extref;
        this.accountNumber = accountNumber;
        this.currency = currency;
        this.valueDate = valueDate;
        this.openBalance = openBalance;
        this.closeBalance = closeBalance;
        this.note = note;
        this.seqnum = seqnum;
        this.mutasiCode = mutasiCode;
        this.trxType = trxType;
        this.trxAmount = trxAmount;
        this.trxDescription = trxDescription;
        this.referenceRemark = referenceRemark;
        this.destinationUrl = destinationUrl;
    }

    public String getExtref() {
        return extref;
    }

    public void setExtref(String extref) {
        this.extref = extref;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getValueDate() {
        return valueDate;
    }

    public void setValueDate(String valueDate) {
        this.valueDate = valueDate;
    }

    public String getOpenBalance() {
        return openBalance;
    }

    public void setOpenBalance(String openBalance) {
        this.openBalance = openBalance;
    }

    public String getCloseBalance() {
        return closeBalance;
    }

    public void setCloseBalance(String closeBalance) {
        this.closeBalance = closeBalance;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getSeqnum() {
        return seqnum;
    }

    public void setSeqnum(String seqnum) {
        this.seqnum = seqnum;
    }

    public String getMutasiCode() {
        return mutasiCode;
    }

    public void setMutasiCode(String mutasiCode) {
        this.mutasiCode = mutasiCode;
    }

    public String getTrxType() {
        return trxType;
    }

    public void setTrxType(String trxType) {
        this.trxType = trxType;
    }

    public String getTrxAmount() {
        return trxAmount;
    }

    public void setTrxAmount(String trxAmount) {
        this.trxAmount = trxAmount;
    }

    public String getTrxDescription() {
        return trxDescription;
    }

    public void setTrxDescription(String trxDescription) {
        this.trxDescription = trxDescription;
    }

    public String getReferenceRemark() {
        return referenceRemark;
    }

    public void setReferenceRemark(String referenceRemark) {
        this.referenceRemark = referenceRemark;
    }

    public String getDestinationUrl() {
        return destinationUrl;
    }

    public void setDestinationUrl(String destinationUrl) {
        this.destinationUrl = destinationUrl;
    }

    @Override
    public String toString() {
        return "{\"" +
                "Extref / ID\":\"" + extref + '\"' +
                ", \"account no\":\"" + accountNumber + '\"' +
                ", \"currency\":\"" + currency + '\"' +
                ", \"value date\":\"" + valueDate + '\"' +
                ", \"open balance\":\"" + openBalance + '\"' +
                ", \"close balance\":\"" + closeBalance + '\"' +
                ", \"NOTE\":\"" + note + '\"' +
                ", \"seqnum / sequence number\":\"" + seqnum + '\"' +
                ", \"mutasi code (D/C)\":\"" + mutasiCode + '\"' +
                ", \"trx type\":\"" + trxType + '\"' +
                ", \"trx amount\":\"" + trxAmount + '\"' +
                ", \"trx description\":\"" + trxDescription + '\"' +
                ", \"reference remark\":\"" + referenceRemark + '\"' +
                ", \"DESTINATIONURL\":\"" + destinationUrl + '\"' +
                '}';
    }

    public String toInvalidJsonString(){
        return "{\"" +
                "Extref / ID\":\"" + extref + '\"' +
                ", \"account no\":\"" + accountNumber + '\"' +
                ", \"currency\":\"" + currency + '\"' +
                ", \"value date\":\"" + valueDate + '\"' +
                ", \"NOTE\":\"" + note + '\"' +
                ", \"seqnum / sequence number\":\"" + seqnum + '\"' +
                ", \"mutasi code (D/C)\":\"" + mutasiCode + '\"' +
                ", \"trx type\":\"" + trxType + '\"' +
                ", \"trx amount\":\"" + trxAmount + '\"' +
                ", \"trx description\":\"" + trxDescription + '\"' +
                ", \"reference remark\":\"" + referenceRemark + '\"' +
                ", \"DESTINATIONURL\":\"" + destinationUrl + '\"' +
                '}';
    }
}
