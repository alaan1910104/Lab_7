import javafx.scene.chart.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GraphMaker {

    public Chart createChart(File mainFile, String type) throws IOException {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();

        xAxis.setLabel("Months");
        yAxis.setLabel("Temperature");

        XYChart chart;

        switch (type){
            case "line": chart = new LineChart<>(xAxis, yAxis);
            break;
            case "bar":  chart = new BarChart<>(xAxis, yAxis);
            break;
            case "area": chart = new AreaChart<>(xAxis,yAxis);
            break;
            default: return null;
        }

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Temperature par mois");

        series.getData().addAll(read(mainFile));

        chart.getData().add(series);

        return chart;
    }


    private List<XYChart.Data<String, Number>> read(File mainFile) throws IOException {

        List<XYChart.Data<String, Number>> mainList = new ArrayList<>();

        if(!Files.readAllLines(mainFile.toPath()).isEmpty()) {
            List<String> strings =
                    Arrays.asList(Files.readAllLines(mainFile.toPath()).get(0).split(","));

            List<Number> numbers = Arrays.stream(
                    Files.readAllLines(mainFile.toPath()).get(1).split(",")).
                    map((Double::valueOf)).
                    collect(Collectors.toList());

            if(numbers.size() == strings.size()){
                for(int counter = 0; counter < numbers.size(); counter++){
                    mainList.add(new XYChart.Data<>(strings.get(counter), numbers.get(counter)));
                }
            }
        }

        return mainList;
    }
}
