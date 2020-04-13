package ro.sd.a2.utils;

import java.util.ArrayList;
import java.util.Random;

public class IbanGenerator {

    /**
     * Iban generator using the rules presented
     * uses Random().nextInt() as random producer
     */
    public static String generateIban(String type, String firstname, String lastname, String creationDate, String currency, String address){
        StringBuilder iban = new StringBuilder("HNG");
        if(type.equals("Spending")) iban.append("#");
        else if(type.equals("Saving")) iban.append("*");

        ArrayList<String> nations = new ArrayList<>();
        nations.add("RO");
        nations.add("EN");
        nations.add("GB");
        nations.add("HU");
        nations.add("IT");
        iban.append(nations.get(new Random().nextInt(5)));

        for(int i = 0; i < 5; i ++){
            iban.append(String.valueOf(new Random().nextInt(10)));
        }

        iban.append(firstname.substring(0,2).toUpperCase());
        iban.append(lastname.substring(0,2).toUpperCase());

        String[] dateParts = creationDate.split(" {2}");
        String date = dateParts[0];
        String time = dateParts[1];

        String[] timeParts = time.split(":");
        String hour = timeParts[0];
        String minute = timeParts[1];

        iban.append(String.valueOf(Integer.parseInt(hour) + Integer.parseInt(minute)));

        iban.append(currency.toUpperCase());

        for(int i = 0; i < 6; i ++){
            iban.append(String.valueOf(new Random().nextInt(10)));
        }

        iban.append(address);
        return iban.toString();
    }

}
