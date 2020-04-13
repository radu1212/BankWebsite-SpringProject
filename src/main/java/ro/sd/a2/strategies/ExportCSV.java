package ro.sd.a2.strategies;

import ro.sd.a2.entity.Transaction;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ExportCSV implements ExportStrategy {

    /**
     * the class used to implement the strategy design pattern if the strategy chosen is export via CSV
     * @param transactions - list of transactions to be exported
     * @param from - the date representing the beginning of the range
     * @param to - the date representing the end of the range
     */
    @Override
    public void export(List<Transaction> transactions, String from, String to) {
        String csvFile = "C:\\Users\\Radu\\Desktop\\CSV_export.csv";
        try {
            FileWriter writer = new FileWriter(csvFile);
            writeLine(writer, Collections.singletonList("Down below you will find the required transactions"));
            writeLine(writer, Collections.singletonList("Period: " + from + " - " + to));
            writeLine(writer, Collections.singletonList("User: " + transactions.get(0).getBankAccount().getUser().getUsername()));
            writeLine(writer, Collections.singletonList("Account " + transactions.get(0).getBankAccount().getIban()));
            for(Transaction transaction : transactions){
                writeLine(writer, Arrays.asList(transaction.getType(), transaction.getRecipient(), transaction.getValue().toString(), transaction.getDate()));
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static final char DEFAULT_SEPARATOR = ',';

    public static void writeLine(Writer w, List<String> values) throws IOException {
        writeLine(w, values, DEFAULT_SEPARATOR, ' ');
    }

    public static void writeLine(Writer w, List<String> values, char separators) throws IOException {
        writeLine(w, values, separators, ' ');
    }

    //https://tools.ietf.org/html/rfc4180
    private static String followCVSformat(String value) {

        String result = value;
        if (result.contains("\"")) {
            result = result.replace("\"", "\"\"");
        }
        return result;

    }

    public static void writeLine(Writer w, List<String> values, char separators, char customQuote) throws IOException {

        boolean first = true;

        //default customQuote is empty

        if (separators == ' ') {
            separators = DEFAULT_SEPARATOR;
        }

        StringBuilder sb = new StringBuilder();
        for (String value : values) {
            if (!first) {
                sb.append(separators);
            }
            if (customQuote == ' ') {
                sb.append(value);
            } else {
                sb.append(customQuote).append(value).append(customQuote);
            }

            first = false;
        }
        sb.append("\n");
        w.append(sb.toString());
    }


}
