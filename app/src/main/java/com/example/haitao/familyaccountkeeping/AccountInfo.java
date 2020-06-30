package com.example.haitao.familyaccountkeeping;

/**
 * Created by haitao on 2019/12/13.
 */

public class AccountInfo {
    private int ID;
    public String account;
    public String income_expenditure;
    public String budget;
    public String money;
    public String pay_way;
    public String bookkeeping_date;
    public String remarks;
    public AccountInfo(String account,String income_expenditure,String budget,String money,String pay_way,String bookkeeping_date,String remarks){
        this.account=account;
        this.income_expenditure=income_expenditure;
        this.budget=budget;
        this.money=money;
        this.pay_way=pay_way;
        this.bookkeeping_date=bookkeeping_date;
        this.remarks=remarks;

    }

    public int getID() {
        return ID;
    }

    public String getAccount() {
        return account;
    }

    public String getIncome_expenditure() {
        return income_expenditure;
    }

    public String getBudget() {
        return budget;
    }

    public String getMoney() {
        return money;
    }

    public String getPay_way() {
        return pay_way;
    }

    public String getBookkeeping_date() {
        return bookkeeping_date;
    }
    public String getRemarks(){
        return remarks;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setIncome_expenditure(String income_expenditure) {
        this.income_expenditure = income_expenditure;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public void setPay_way(String pay_way) {
        this.pay_way = pay_way;
    }

    public void setBookkeeping_date(String bookkeeping_date) {
        this.bookkeeping_date = bookkeeping_date;
    }
    public void setRemarks(String remarks){
        this.remarks=remarks;
    }
}
