package com.currencyfair;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by davidb on 3/23/15.
 */

@RestController
public class MarketTradeController {

//    @RequestMapping(value = "/", headers = {"name=currencyfair", "id=501", "x-requested-with=2"})
    Random randInt = null;
    static Random randDouble = null;
    double MEAN = 0.5f;
    double VARIANCE = 0.05f;
    
    int START = 500;
    int END = 2000;
    
@RequestMapping(value = "/")
    public ResponseEntity<MarketTrader> get() {
    
        randInt = new Random();
        randDouble = new Random();

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy hh:mm:ss");
        //"24-JAN-15 10:27:44"

        MarketTrader marketTrader = new MarketTrader();

        marketTrader.setUserId("134256");
        marketTrader.setCurrencyFrom("EUR");
        marketTrader.setCurrencyTo("GBP");
        marketTrader.setAmountSell(1000);
        marketTrader.setAmountBuy((float) 747.1);
        marketTrader.setRate((float) 0.7471);
        marketTrader.setOriginatingCountry("FR");
        marketTrader.setTimePlaced("24-JAN-15 10:27:44");

        return new ResponseEntity<MarketTrader>(marketTrader, HttpStatus.OK);

    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
        public ResponseEntity<MarketTrader> update(@RequestBody MarketTrader marketTrader) {
        
        if (marketTrader != null) {
            int buy = getRandomInteger(this.START, this.END, this.randInt);
            int sell = getRandomInteger(this.START, this.END, this.randInt);
            double rate = getRandomDouble(this.MEAN, this.VARIANCE);
//            marketTrader.setAmountBuy(marketTrader.getAmountBuy() + (float) buy);
            marketTrader.setAmountBuy((float) buy);
//            marketTrader.setAmountSell((int) (marketTrader.getAmountSell() + sell));
            marketTrader.setAmountSell(sell);
            marketTrader.setRate((float) rate);
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy hh:mm:ss");
            marketTrader.setTimePlaced(sdf.format(new Date()));
        }

        return new ResponseEntity<MarketTrader>(marketTrader, HttpStatus.OK);
    }

    private static int getRandomInteger(int aStart, int aEnd, Random aRandom){
        if (aStart > aEnd) {
            throw new IllegalArgumentException("Start cannot exceed End.");
        }
        //get the range, casting to long to avoid overflow problems
        long range = (long)aEnd - (long)aStart + 1;
        // compute a fraction of the range, 0 <= frac < range
        long fraction = (long)(range * aRandom.nextDouble());
        int randomNumber =  (int)(fraction + aStart);
        return randomNumber;
    }

    private static double getRandomDouble(double aMean, double aVariance){
        double retVal = aMean + randDouble.nextGaussian() * aVariance;
        if (retVal < 0.0f)
            retVal = -retVal;
        return retVal;
    }
}
