package com.currencyfair;

/**
 * Created by davidb on 3/23/15.
 */
public class MarketTrader {
    
    String  userId;
    String  currencyFrom;
    String  currencyTo;
    int     amountSell;
    float   amountBuy;

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    float   rate;
    String  timePlaced;
    String  originatingCountry;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCurrencyFrom() {
        return currencyFrom;
    }

    public void setCurrencyFrom(String currencyFrom) {
        this.currencyFrom = currencyFrom;
    }

    public String getCurrencyTo() {
        return currencyTo;
    }

    public void setCurrencyTo(String currencyTo) {
        this.currencyTo = currencyTo;
    }

    public int getAmountSell() {
        return amountSell;
    }

    public void setAmountSell(int amountSell) {
        this.amountSell = amountSell;
    }

    public float getAmountBuy() {
        return amountBuy;
    }

    public void setAmountBuy(float amountBuy) {
        this.amountBuy = amountBuy;
    }

    public String getTimePlaced() {
        return this.timePlaced;
    }

    public void setTimePlaced(String timePlaced) {

        this.timePlaced = timePlaced;
    }

    public String getOriginatingCountry() {
        return originatingCountry;
    }

    public void setOriginatingCountry(String originatingCountry) {
        this.originatingCountry = originatingCountry;
    }
}
