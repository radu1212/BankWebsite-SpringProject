package ro.sd.a2.strategies;

import ro.sd.a2.entity.Transaction;

import java.util.List;

public class ExportContext {

    private ExportStrategy exportStrategy;

    public ExportContext(ExportStrategy exportStrategy) {
        this.exportStrategy = exportStrategy;
    }

    /**
     * the context used to chose the strategy
     * @param transactions - list of transactions to be exported
     * @param from - the date representing the beginning of the range
     * @param to - the date representing the end of the range
     */
    public void executeStrategy(List<Transaction> transactions, String from, String to){
        exportStrategy.export(transactions,from,to);
    }

}
