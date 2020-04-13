package ro.sd.a2.utils;

import ro.sd.a2.entity.Transaction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CheckDateInRange {

    /**
     * @param transaction - the transaction to be checked if within given range
     * @param from - the date representing the beginning of the range
     * @param to - the date representing the end of the range
     * @return true - if within range, else false
     */
    public static boolean checkIfInRange(Transaction transaction, String from, String to) {
        String date = transaction.getDate();
        String[] dateParts = date.split(" {2}");
        String tr = dateParts[0];
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date1 = format.parse(from);
            Date date2 = format.parse(to);
            Date x = formatter.parse(tr);
            return !(x.before(date1) || x.after(date2));
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean checkIfOneDateAfterAnother(String from, String to){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date1 = format.parse(from);
            Date date2 = format.parse(to);
            return !date2.before(date1);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }
}
