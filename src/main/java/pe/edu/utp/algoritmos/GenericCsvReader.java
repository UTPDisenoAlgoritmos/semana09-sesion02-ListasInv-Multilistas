package pe.edu.utp.algoritmos;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Componente reutilizable de lectura CSV.
 * Lee cualquier archivo con cabecera, separador coma y valores simples.
 * Para clases introductorias se mantiene básico y fácil de explicar.
 */
public class GenericCsvReader {

    public CsvTable read(Path path) throws IOException {
        List<CsvRecord> rows = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String headerLine = reader.readLine();
            if (headerLine == null || headerLine.isBlank()) {
                throw new IOException("El CSV está vacío o no tiene cabecera.");
            }

            List<String> headers = Arrays.stream(headerLine.split(","))
                    .map(String::trim)
                    .toList();

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] values = line.split(",", -1);
                CsvRecord record = new CsvRecord();

                for (int i = 0; i < headers.size(); i++) {
                    String value = i < values.length ? values[i].trim() : "";
                    record.put(headers.get(i), value);
                }
                rows.add(record);
            }
            return new CsvTable(headers, rows);
        }
    }
}
