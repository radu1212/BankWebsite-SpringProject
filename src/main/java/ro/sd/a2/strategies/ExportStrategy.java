package ro.sd.a2.strategies;

import ro.sd.a2.entity.Transaction;

import java.util.List;

public interface ExportStrategy {

    /**
     * the interface used to implement the strategy design pattern
     * @param transactions - list of transactions to be exported
     * @param from - the date representing the beginning of the range
     * @param to - the date representing the end of the range
     */
    public void export(List<Transaction> transactions, String from, String to);

}
