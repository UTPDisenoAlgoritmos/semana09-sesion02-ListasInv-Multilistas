package pe.edu.utp.algoritmos;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Representa una fila genérica del CSV.
 * No depende de una clase Producto, Ticket o Libro.
 * Cada columna se guarda como clave y valor.
 */
public class CsvRecord {
    private final Map<String, String> fields = new LinkedHashMap<>();

    public void put(String column, String value) {
        fields.put(column, value);
    }

    public String get(String column) {
        return fields.getOrDefault(column, "");
    }

    public Map<String, String> getFields() {
        return fields;
    }

    @Override
    public String toString() {
        return fields.toString();
    }
}
