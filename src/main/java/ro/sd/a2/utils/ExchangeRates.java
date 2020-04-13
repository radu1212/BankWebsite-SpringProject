package ro.sd.a2.utils;

public class ExchangeRates {


    /**
     * @param from - the currency from which you wish to exchange
     * @param to - the currency in which you wish to exchange
     * @return a double representing the exchange rate
     */
    public Double computeExchangeRates(String from, String to) {
        double rate = 0.0;
        if(from.equals("RON") && to.equals("EUR")) rate = 4.85;
        if(from.equals("RON") && to.equals("USD")) rate = 4.43;
        if(from.equals("RON") && to.equals("FRT")) rate = 0.014;
        if(from.equals("EUR") && to.equals("RON")) rate = 0.21;
        if(from.equals("EUR") && to.equals("USD")) rate = 0.92;
        if(from.equals("EUR") && to.equals("FRT")) rate = 0.0028;
        if(from.equals("USD") && to.equals("EUR")) rate = 1.09;
        if(from.equals("USD") && to.equals("RON")) rate = 0.23;
        if(from.equals("USD") && to.equals("FRT")) rate = 0.0031;
        if(from.equals("FRT") && to.equals("EUR")) rate = 356.82;
        if(from.equals("FRT") && to.equals("RON")) rate = 73.85;
        if(from.equals("FRT") && to.equals("USD")) rate = 327.04;
        return rate;
    }
}
