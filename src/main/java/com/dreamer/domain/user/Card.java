package com.dreamer.domain.user;

public class Card {

    private Integer id;

    private Agent agent;//属于哪个代理

    private String bank;//银行

    private String branch;//支行

    private String name;//开户人

    private String number;//开户卡号

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(name);
        sb.append(" ");
        sb.append(bank);
        sb.append(" ");
        sb.append(branch);
        sb.append(" ");
        sb.append(number);
        return sb.toString();
    }
}
