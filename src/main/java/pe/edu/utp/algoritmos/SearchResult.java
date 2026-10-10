package pe.edu.utp.algoritmos;

import java.util.List;

public class SearchResult {
    private final List<CsvRecord> records;
    private final SearchMetrics metrics;

    public SearchResult(List<CsvRecord> records, SearchMetrics metrics) {
        this.records = records;
        this.metrics = metrics;
    }

    public List<CsvRecord> getRecords() {
        return records;
    }

    public SearchMetrics getMetrics() {
        return metrics;
    }
}
