package application;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main extends Application {
    static CMD tempCMD;
    static HashMap<Integer, CMD> line_to_CMD = new HashMap<>();
    static TextField[] specialFunctionRegister = new TextField[10];
    static String namesSFR[] = {"W-Reg", "FSR", "PCL", "PCLATH", "Status", "PC", "Stackpointer", "WDT aktiv", "WDT"};
    static HashMap<Integer, Integer> line_to_Full_CMD = new HashMap<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        AnchorPane middlePane = new AnchorPane();
        AnchorPane leftPane = new AnchorPane();
        AnchorPane rightPane = new AnchorPane();

        GridPane trisBank1 = new GridPane();
        trisBank1.setLayoutY(400);
        trisBank1.setLayoutX(20);
        GridPane trisBank2 = new GridPane();
        trisBank2.setLayoutY(500);
        trisBank2.setLayoutX(20);
        trisBank1 = addColumnsAndRowsToGridPane("RA", trisBank1);
        trisBank2 = addColumnsAndRowsToGridPane("RB", trisBank2);

        leftPane.getChildren().add(trisBank1);
        leftPane.getChildren().add(trisBank2);
        addSpecialFunctionRegister(leftPane);
        addtextFieldsSFR(leftPane);
        leftPane.getChildren().add(specialFunctionRegister[0]);
        addButtonsToAnchorPane(rightPane);
        String fabiansPath = "C:\\Users\\fabia\\PIC_GUI\\PIC_UI\\src\\main\\TestProg_PicSim_20210420\\TPicSim1.LST";
        String NidisPath = "/Users/nidhi/Desktop/Coding/UniWorkspace/Java/Gitrepo/PIcSImulator/PicSimulator/src/main/TestProg_PicSim_20210420/TPicSim5.LST";
        String activePath = fabiansPath;

        List<String> input = readLinesFromFile(activePath);
        addListView(input, middlePane);

        AnchorPane rootPane = new AnchorPane();
        Scene scene = new Scene(rootPane, 1200, 800);

        leftPane.setLayoutX(0);
        middlePane.setLayoutX(2.5 / 8.0 * scene.getWidth());
        rightPane.setLayoutX(0.8125 * scene.getWidth());

        rootPane.getChildren().add(middlePane);
        rootPane.getChildren().add(leftPane);
        rootPane.getChildren().add(rightPane);
        primaryStage.setScene(scene);
        primaryStage.show();
        getCommands(input);
    }

    private static List<String> readLinesFromFile(String filePath) throws IOException {
        List<String> lines = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));

        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }

        reader.close();
        return lines;
    }

    private static void getCommands(List<String> input) {
        for (String k : input) {
            if ((k.charAt(0) + "").equals(" ")) {
            } else {
                int temp_dec_CMD = Integer.parseInt(k.split(" ")[1], 16);
                System.out.println("line: " + k.split(" ")[0]);
                tempCMD = CMDUtility.recognizeCMD(temp_dec_CMD);
                int tempCMDLine = Integer.parseInt(k.split(" ")[0], 16);
                line_to_Full_CMD.put(tempCMDLine, temp_dec_CMD);
                line_to_CMD.put(tempCMDLine, tempCMD);
            }
        }
    }

    private static void addListView(List<String> input, AnchorPane middlePane) {
    	ListView<String> listView = new ListView<>();
        listView.setEditable(false);
        listView.setLayoutY(400);
        listView.setPrefHeight(400);
        listView.setPrefWidth(600);      
        
        for (String item : input) {
            listView.getItems().add(item);
        }
        
        middlePane.getChildren().add(listView);
    }

    private static GridPane addColumnsAndRowsToGridPane(String name, GridPane gridPane) {
        // Add column constraints
        for (int i = 0; i < 8; i++) {
            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setPrefWidth(30); // Set preferred width for each column
            gridPane.getColumnConstraints().add(colConstraints);
        }

        // Add row constraints
        for (int i = 0; i < 3; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPrefHeight(30); // Set preferred height for each row
            gridPane.getRowConstraints().add(rowConstraints);
        }
        Label trisBank = new Label(name);
        Label tris = new Label("Tris");
        Label pin = new Label("Pin");
        gridPane.add(trisBank, 0, 0);
        gridPane.add(tris, 0, 1);
        gridPane.add(pin, 0, 2);
        gridPane.setGridLinesVisible(true);
        // Add Labels to the first two rows
        for (int i = 0; i < 2; i++) {
            for (int j = 1; j < 8; j++) {
                Label label;
                if (i == 0) {
                    label = new Label("" + (8 - j));
                    GridPane.setHalignment(label, HPos.CENTER); // Center horizontally within the cell
                    GridPane.setValignment(label, VPos.CENTER); // Center vertically within the cell
                } else {
                    label = new Label("o");
                    GridPane.setHalignment(label, HPos.CENTER); // Center horizontally within the cell
                    GridPane.setValignment(label, VPos.CENTER); // Center vertically within the cell
                }

                gridPane.add(label, j, i);
            }
        }
        // Add TextFields to the last row with starting value of "00"
        for (int j = 1; j < 8; j++) {
            TextField textField = new TextField("00");
            textField.setPrefWidth(Double.MAX_VALUE);
            textField.setPrefHeight(Double.MAX_VALUE);
            textField.setEditable(false); // Disable direct editing by the user
            GridPane.setHalignment(textField, HPos.CENTER); // Center horizontally within the cell
            GridPane.setValignment(textField, VPos.CENTER); // Center vertically within the cell
            gridPane.add(textField, j, 2);
        }

        return gridPane;
    }

    private static void addSpecialFunctionRegister(AnchorPane leftPane) {
        for (int i = 0; i < namesSFR.length; i++) {
            Label label = new Label(namesSFR[i]);
            label.setLayoutX(20);
            label.setLayoutY(20 * (i + 1));
            leftPane.getChildren().add(label);
        }
    }

    private static void addtextFieldsSFR(AnchorPane leftPane) {
        for (int i = 0; i < namesSFR.length; i++) {
            TextField textField = new TextField("00");
            textField.setLayoutX(100);
            textField.setLayoutY(20 * (i + 1));
            textField.setPrefWidth(30);
            specialFunctionRegister[i] = textField;
        }
    }

    private static void addButtonsToAnchorPane(AnchorPane rightPane) {
        Button button1 = new Button("Reset");
        button1.setLayoutX(40);
        button1.setLayoutY(0);
        button1.setPrefSize(80, 50);
        button1.setOnAction(e -> resetButton());

        Button button2 = new Button("Step in");
        button2.setLayoutX(40);
        button2.setLayoutY(60);
        button2.setPrefSize(80, 50);
        button2.setOnAction(e -> stepInButton());

        Button button3 = new Button("GO");
        button3.setLayoutX(40);
        button3.setLayoutY(120);
        button3.setPrefSize(80, 50);
        button3.setOnAction(e -> goButton());
        rightPane.getChildren().addAll(button1, button2, button3);
    }

    private static void resetButton() {
    }

    private static void stepInButton() {
        System.out.println("Button 2 clicked");
        // Perform action for Button 2
    }

    private static void goButton() {
        while (!CMDUtility.isProgramEnded()) {
            int i = CMDUtility.getProgrammCounterLine();
            CMD execCMD = line_to_CMD.get(i);

            // Weitere Verarbeitung des Befehls hier...

            CMDUtility.do_CMD(execCMD, line_to_Full_CMD.get(CMDUtility.getProgrammCounterLine()));

        }
        System.out.println("Test");
        specialFunctionRegister[0].setText("" + CMDUtility.getWRegister());
    }

    private static class HighlightedCell extends javafx.scene.control.ListCell<String> {
        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setText(null);
                setStyle("");
            } else {
                setText(item);
                if (item.startsWith("0000")) {
                    setStyle("-fx-control-inner-background: blue;");
                } else {
                    setStyle("-fx-control-inner-background: red;");
                }
            }
        }
    }
}
