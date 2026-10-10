package pe.edu.utp.algoritmos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Lista invertida:
 * valor de atributo -> lista de registros que tienen ese valor.
 * Ejemplo: marca=HP -> [P0001, P0020, P0100]
 */
public class InvertedIndex {
    private final Map<String, Map<String, List<CsvRecord>>> index = new HashMap<>();

    public void build(List<CsvRecord> rows, List<String> indexedColumns) {
        index.clear();
        for (String column : indexedColumns) {
            index.put(column, new HashMap<>());
        }

        for (CsvRecord row : rows) {
            for (String column : indexedColumns) {
                String value = row.get(column);
                index.get(column)
                        .computeIfAbsent(value, key -> new ArrayList<>())
                        .add(row);
            }
        }
    }

    public SearchResult search(String column, String value) {
        SearchMetrics metrics = new SearchMetrics();
        long start = System.nanoTime();

        Map<String, List<CsvRecord>> byValue = index.getOrDefault(column, Map.of());
        List<CsvRecord> found = byValue.getOrDefault(value, List.of());

        metrics.comparisons = 1;
        metrics.visitedNodes = found.size();
        metrics.resultCount = found.size();
        metrics.timeNanos = System.nanoTime() - start;
        return new SearchResult(found, metrics);
    }
}
