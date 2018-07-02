package com.mo.lawyercloud.beans.apiBeans;

/**
 * Created by mo on 2018/7/2.
 */

public class BankCardInfo {

    /**
     * id : 7
     * cardNumber : 123123123123
     * account : 张三
     * bank : 中国银行
     */

    private int id;
    private String cardNumber;
    private String account;
    private String bank;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }
}
