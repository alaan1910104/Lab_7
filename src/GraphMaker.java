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

    public class fileException extends Exception{};

    // creer un chart d'un type especifié
    public Chart createChart(File mainFile, String type) throws IOException {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();

        xAxis.setLabel("Months");
        yAxis.setLabel("Temperature");

        XYChart<String, Number> chart;

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


        try{
         series.getData().addAll(read(mainFile));
        }
        catch (fileException ex){
            System.out.print("Il y a un problème avec le File, assurez-vous de suivre le format: " +
                    "\n mois1,mois2,mois3,..." +
                    "\nnumb1,numb2,numb3,...");
        }

        chart.getData().add(series);

        return chart;
    }


    // lire les 2 premieres lignes du file, et retuner une liste avec string et Number, pour les series du chart
    private List<XYChart.Data<String, Number>> read(File mainFile) throws IOException, fileException {

        List<XYChart.Data<String, Number>> mainList = new ArrayList<>();

        // checker si le file n'est pas vide et s'il
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
            else{ throw new fileException();}
        }
        else{throw new fileException();}

        return mainList;
    }
}
