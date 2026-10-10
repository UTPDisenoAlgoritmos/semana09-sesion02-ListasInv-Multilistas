package pe.edu.utp.algoritmos;

/**
 * Métricas simples para comparar búsquedas en clase.
 */
public class SearchMetrics {
    public long comparisons;
    public long visitedNodes;
    public long timeNanos;
    public int resultCount;

    public double timeMillis() {
        return timeNanos / 1_000_000.0;
    }

    @Override
    public String toString() {
        return "resultados=" + resultCount +
                ", comparaciones=" + comparisons +
                ", nodosVisitados=" + visitedNodes +
                ", tiempoMs=" + String.format("%.4f", timeMillis());
    }
}
