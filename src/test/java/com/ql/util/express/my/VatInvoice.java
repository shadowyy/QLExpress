package com.ql.util.express.my;

/**
 * @author shadowyy
 * @version 2020/3/19 11:06
 */
public class VatInvoice {
    /**
     * 发票代码
     */
    private String invoiceCode;
    /**
     * 购买方识别号
     */
    private String buyerId;
    /**
     * 购买方名称
     */
    private String buyerName;

    public String getInvoiceCode() {
        return invoiceCode;
    }

    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }
}
