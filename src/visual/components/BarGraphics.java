package visual.components;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import java.util.List;
import java.util.Map;

public class BarGraphics {
    public ChartPanel creteGrapsFromList(List<String> names, List<Double> numbers) {
         if (names == null || numbers == null || names.size() != numbers.size()) {
            throw new IllegalArgumentException("Las listas deben tener el mismo tamaño");
        }

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < names.size(); i++) {
            dataset.addValue(numbers.get(i), "Porcentaje", names.get(i));
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Gráfico de Porcentajes",
                "Categorías",
                "Porcentaje (%)",
                dataset
        );

        return new ChartPanel(chart);
    }
    public ChartPanel createBarGraphic(Map<String, Double> datos) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<String, Double> entrada : datos.entrySet()) {
            dataset.addValue(entrada.getValue(), "Porcentaje", entrada.getKey());
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Gráfico de Porcentajes",  // título
                "Porcentaje de Participacion de Reaseguradoras",              // eje X
                "Porcentaje (%)",          // eje Y
                dataset
        );
       return new ChartPanel(chart);
    }
}


