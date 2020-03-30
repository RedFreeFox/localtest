package com.xfdmao.fcat.coin.po;

/**
 * Created by 翔飞君 on 2019/8/4.
 */
public class PositionInfo {
    private String symbol;
    private String contract_code;
    private String contract_type;
    private double volume;
    private double available;
    private double frozen;
    private double cost_open;
    private double cost_hold;
    private double profit_unreal;
    private double profit_rate;
    private double profit;
    private double position_margin;
    private int lever_rate;
    private String direction;
    private double last_price;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getContract_code() {
        return contract_code;
    }

    public void setContract_code(String contract_code) {
        this.contract_code = contract_code;
    }

    public String getContract_type() {
        return contract_type;
    }

    public void setContract_type(String contract_type) {
        this.contract_type = contract_type;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getAvailable() {
        return available;
    }

    public void setAvailable(double available) {
        this.available = available;
    }

    public double getFrozen() {
        return frozen;
    }

    public void setFrozen(double frozen) {
        this.frozen = frozen;
    }

    public double getCost_open() {
        return cost_open;
    }

    public void setCost_open(double cost_open) {
        this.cost_open = cost_open;
    }

    public double getCost_hold() {
        return cost_hold;
    }

    public void setCost_hold(double cost_hold) {
        this.cost_hold = cost_hold;
    }

    public double getProfit_unreal() {
        return profit_unreal;
    }

    public void setProfit_unreal(double profit_unreal) {
        this.profit_unreal = profit_unreal;
    }

    public double getProfit_rate() {
        return profit_rate;
    }

    public void setProfit_rate(double profit_rate) {
        this.profit_rate = profit_rate;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public double getPosition_margin() {
        return position_margin;
    }

    public void setPosition_margin(double position_margin) {
        this.position_margin = position_margin;
    }

    public int getLever_rate() {
        return lever_rate;
    }

    public void setLever_rate(int lever_rate) {
        this.lever_rate = lever_rate;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public double getLast_price() {
        return last_price;
    }

    public void setLast_price(double last_price) {
        this.last_price = last_price;
    }
}
