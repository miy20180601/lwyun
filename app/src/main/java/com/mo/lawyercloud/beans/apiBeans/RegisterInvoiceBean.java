package com.mo.lawyercloud.beans.apiBeans;

/**
 * @author cui
 * @date 2018/6/11
 * @details 申请发票需要的参数bean
 */

public class RegisterInvoiceBean {

    /**
     * invoiceType : 2
     * invoiceTitle : 广州飞进信息科技有限公司
     * invoiceTaxpayersNo : 5464654646
     * order : {"id":13}
     * bank : 中国银行
     * mail : 123123123@qq.com
     */

    private int invoiceType;
    private String invoiceTitle;
    private String invoiceTaxpayersNo;
    private OrderBean order;
    private String bank;
    private String mail;

    public int getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(int invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    public String getInvoiceTaxpayersNo() {
        return invoiceTaxpayersNo;
    }

    public void setInvoiceTaxpayersNo(String invoiceTaxpayersNo) {
        this.invoiceTaxpayersNo = invoiceTaxpayersNo;
    }

    public OrderBean getOrder() {
        return order;
    }

    public void setOrder(OrderBean order) {
        this.order = order;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public static class OrderBean {
        /**
         * id : 13
         */

        private int id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
