package pe.edu.utp.algoritmos;

import java.util.List;

/**
 * Agrupa la cabecera y las filas leídas desde cualquier CSV.
 */
public class CsvTable {
    private final List<String> headers;
    private final List<CsvRecord> rows;

    public CsvTable(List<String> headers, List<CsvRecord> rows) {
        this.headers = headers;
        this.rows = rows;
    }

    public List<String> getHeaders() {
        return headers;
    }

    public List<CsvRecord> getRows() {
        return rows;
    }
}
