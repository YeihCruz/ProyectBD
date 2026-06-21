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
            throw new IllegalArgumentException("Both List must be the same size");
        }

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < names.size(); i++) {
            dataset.addValue(numbers.get(i), "Percentage", names.get(i));
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Percentage Graphics",
                "Categories",
                "Percentage (%)",
                dataset
        );

        return new ChartPanel(chart);
    }
    public ChartPanel createBarGraphic(Map<String, Double> datos) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<String, Double> entrada : datos.entrySet()) {
            dataset.addValue(entrada.getValue(), "Percentage", entrada.getKey());
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Percentage Graphics",
                "Percentage or Reinsurer Participations",
                "Percentage (%)",          // eje Y
                dataset
        );
       return new ChartPanel(chart);
    }
}


