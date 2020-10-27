import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.Chart;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;


public class StageManager {

    private Stage pStage;
    private FileChooser fc;
    private File mainFile;
    private GraphMaker graphMaker;
    private Chart mainChart;

    public StageManager(Stage pStage){
        this.pStage = pStage;
        this.fc = new FileChooser();
        this.graphMaker = new GraphMaker();
        this.mainChart = null;

        pStage.setScene(init());
    }

    // creer les éléments necessaires
    private Scene init(){
        //Menu bar
        MenuBar mb = new MenuBar();

        Menu importMenu = new Menu("Import");

        Menu exportMenu = new Menu("Export");

        MenuItem exportMenuItem = new MenuItem("Save");

        exportMenuItem.setOnAction(event -> save());

        exportMenu.getItems().addAll(exportMenuItem);

        mb.getMenus().addAll(importMenu, exportMenu);


        // Main Pain
        BorderPane bp = new BorderPane();
        bp.setTop(mb);
        bp.setCenter(createMenu(importMenu));
        bp.setMinSize(Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight());

        return new Scene(bp);
    }

    // creeer tous les elements du menu Import, et les donner la capacité de creer une graphique
    private VBox createMenu(Menu menu){
        VBox graphBox = new VBox();

        MenuItem lineMenu = new MenuItem("Line");
        lineMenu.setOnAction(event -> {
            importPopUp();
            try {
                this.mainChart = this.graphMaker.createChart(mainFile, "line");
            } catch (IOException e) {
                e.printStackTrace();
            }
            graphBox.getChildren().add(this.mainChart);
        });

        MenuItem areaMenu = new MenuItem("Area");
        areaMenu.setOnAction(event -> {
            importPopUp();
            try {
                this.mainChart = this.graphMaker.createChart(mainFile, "area");
            } catch (IOException e) {
                e.printStackTrace();
            }
            graphBox.getChildren().add(this.mainChart);
        });

        MenuItem barMenu = new MenuItem("Bar");
        barMenu.setOnAction(event -> {
            importPopUp();
            try {
                this.mainChart = this.graphMaker.createChart(mainFile, "bar");
            } catch (IOException e) {
                e.printStackTrace();
            }
            graphBox.getChildren().add(this.mainChart);
        });


        menu.getItems().addAll(lineMenu, areaMenu, barMenu);


        return graphBox;
    }

    // pop up pour selectioner le file a ouvrir
    private void importPopUp(){
        this.fc.setTitle("Selectioner un file à ouvrir.");
        this.fc.getExtensionFilters().removeAll();
        this.fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Data", "*.dat"));
        this.mainFile = this.fc.showOpenDialog(this.pStage);
    }

    // pop up pour selectioner le dossier pour sauvre-garder
    private File exportPopUp(){
        this.fc.setTitle("Selectioner un dossier pour sauve-garder.");
        this.fc.getExtensionFilters().removeAll();
        this.fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image", "*.png"));
        return this.fc.showSaveDialog(this.pStage);
    }

    private void save(){
            WritableImage image = this.mainChart.snapshot(new SnapshotParameters(), null);
            File file = exportPopUp();
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}
