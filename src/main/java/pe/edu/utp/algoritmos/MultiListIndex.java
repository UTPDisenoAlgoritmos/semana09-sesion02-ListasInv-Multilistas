package pe.edu.utp.algoritmos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Multilista didáctica:
 * cada registro puede participar en varias cadenas enlazadas.
 * Una cadena puede ser marca=HP, otra categoria=Laptop y otra estado=Disponible.
 */
public class MultiListIndex {
    private static class Node {
        CsvRecord record;
        Map<String, Node> nextByKey = new HashMap<>();

        Node(CsvRecord record) {
            this.record = record;
        }
    }

    private final Map<String, Node> heads = new HashMap<>();
    private final Map<String, Node> tails = new HashMap<>();

    public void build(List<CsvRecord> rows, List<String> linkedColumns) {
        heads.clear();
        tails.clear();

        for (CsvRecord row : rows) {
            Node node = new Node(row);

            for (String column : linkedColumns) {
                String key = key(column, row.get(column));
                Node last = tails.get(key);

                if (last == null) {
                    heads.put(key, node);
                } else {
                    last.nextByKey.put(key, node);
                }
                tails.put(key, node);
            }
        }
    }

    public SearchResult traverse(String column, String value) {
        SearchMetrics metrics = new SearchMetrics();
        long start = System.nanoTime();
        String key = key(column, value);
        List<CsvRecord> found = new ArrayList<>();

        Node current = heads.get(key);
        while (current != null) {
            metrics.visitedNodes++;
            metrics.comparisons++;
            found.add(current.record);
            current = current.nextByKey.get(key);
        }

        metrics.resultCount = found.size();
        metrics.timeNanos = System.nanoTime() - start;
        return new SearchResult(found, metrics);
    }

    private String key(String column, String value) {
        return column + "=" + value;
    }
}
