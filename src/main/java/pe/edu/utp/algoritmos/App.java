package pe.edu.utp.algoritmos;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Aplicación de consola para ejecutar casos de listas invertidas y multilistas.
 */
public class App {
    private static CsvTable table;
    private static final GenericCsvReader csvReader = new GenericCsvReader();
    private static final InvertedIndex invertedIndex = new InvertedIndex();
    private static final MultiListIndex multiListIndex = new MultiListIndex();

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Semana 09 - Búsqueda Externa: Listas Invertidas y Multilistas ===");

        boolean running = true;
        while (running) {
            printMenu();
            String option = scanner.nextLine().trim();

            switch (option) {
                case "1" -> loadCsv(scanner);
                case "2" -> showFirstRows();
                case "3" -> buildInverted(scanner);
                case "4" -> searchInverted(scanner);
                case "5" -> buildMultilist(scanner);
                case "6" -> searchMultilist(scanner);
                case "0" -> running = false;
                default -> System.out.println("Opción no válida.");
            }
        }
        System.out.println("Fin de la ejecución.");
    }

    private static void printMenu() {
        System.out.println("\nMenú");
        System.out.println("1. Cargar CSV");
        System.out.println("2. Mostrar cabecera y 10 primeros registros");
        System.out.println("3. Construir listas invertidas");
        System.out.println("4. Buscar con lista invertida");
        System.out.println("5. Construir multilistas");
        System.out.println("6. Buscar con multilista");
        System.out.println("0. Salir");
        System.out.print("Seleccione: ");
    }

    private static void loadCsv(Scanner scanner) throws Exception {
        System.out.print("Ruta del CSV: ");
        String path = scanner.nextLine().trim();
        table = csvReader.read(Path.of(path));
        System.out.println("CSV cargado correctamente.");
        System.out.println("Columnas: " + table.getHeaders());
        System.out.println("Registros: " + table.getRows().size());
    }

    private static void showFirstRows() {
        if (!isLoaded()) return;
        System.out.println("Columnas: " + table.getHeaders());
        table.getRows().stream().limit(10).forEach(System.out::println);
    }

    private static void buildInverted(Scanner scanner) {
        if (!isLoaded()) return;
        List<String> columns = askColumns(scanner);
        invertedIndex.build(table.getRows(), columns);
        System.out.println("Listas invertidas construidas para: " + columns);
    }

    private static void searchInverted(Scanner scanner) {
        if (!isLoaded()) return;
        String column = ask(scanner, "Columna a buscar");
        String value = ask(scanner, "Valor exacto");
        SearchResult result = invertedIndex.search(column, value);
        printResult(result);
    }

    private static void buildMultilist(Scanner scanner) {
        if (!isLoaded()) return;
        List<String> columns = askColumns(scanner);
        multiListIndex.build(table.getRows(), columns);
        System.out.println("Multilistas construidas para: " + columns);
    }

    private static void searchMultilist(Scanner scanner) {
        if (!isLoaded()) return;
        String column = ask(scanner, "Columna a recorrer");
        String value = ask(scanner, "Valor exacto");
        SearchResult result = multiListIndex.traverse(column, value);
        printResult(result);
    }

    private static List<String> askColumns(Scanner scanner) {
        System.out.println("Columnas disponibles: " + table.getHeaders());
        System.out.print("Columnas a indexar/enlazar separadas por coma: ");
        String[] parts = scanner.nextLine().split(",");
        List<String> columns = new ArrayList<>();
        for (String part : parts) {
            String column = part.trim();
            if (!column.isEmpty()) columns.add(column);
        }
        return columns;
    }

    private static String ask(Scanner scanner, String label) {
        System.out.print(label + ": ");
        return scanner.nextLine().trim();
    }

    private static void printResult(SearchResult result) {
        System.out.println("Métricas: " + result.getMetrics());
        result.getRecords().stream().limit(10).forEach(System.out::println);
        if (result.getRecords().size() > 10) {
            System.out.println("... se muestran solo los 10 primeros resultados.");
        }
    }

    private static boolean isLoaded() {
        if (table == null) {
            System.out.println("Primero cargue un CSV.");
            return false;
        }
        return true;
    }
}
