package GUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class SpecialfunctionRegister {
    private Text wReg = new Text("W-Reg");
    private Text fSR = new Text("FSR");
    private Text pCL = new Text("PCL");
    private Text pCLATH = new Text("PCLATH");
    private Text status = new Text("Status");
    private Text pC = new Text("PC");
    private Text stackpointer = new Text("Stackpointer");
    private TextField[] textFields;
    private GridPane gridPane;

    public SpecialfunctionRegister() {
        textFields = new TextField[6];
        gridPane = new GridPane();
        gridPane.setHgap(10);  // Set horizontal gap between columns
        gridPane.setVgap(10);  // Set vertical gap between rows
        gridPane.setPadding(new Insets(10));  // Set padding

        for (int i = 0; i < textFields.length; i++) {
            textFields[i] = new TextField("00");
            textFields[i].setEditable(false);
            textFields[i].setPrefWidth(30);  // Set preferred width
            textFields[i].setPrefHeight(30);  // Set preferred height
            gridPane.add(textFields[i], 1, i);  // Add TextField to the gridPane
        }

        gridPane.add(wReg, 0, 0);
        gridPane.add(fSR, 0, 1);
        gridPane.add(pCL, 0, 2);
        gridPane.add(pCLATH, 0, 3);
        gridPane.add(status, 0, 4);
        gridPane.add(pC, 0, 5);
        gridPane.add(stackpointer, 0, 6);

        // Align labels and text fields
        GridPane.setHalignment(wReg, javafx.geometry.HPos.RIGHT);
        GridPane.setHalignment(fSR, javafx.geometry.HPos.RIGHT);
        GridPane.setHalignment(pCL, javafx.geometry.HPos.RIGHT);
        GridPane.setHalignment(pCLATH, javafx.geometry.HPos.RIGHT);
        GridPane.setHalignment(status, javafx.geometry.HPos.RIGHT);
        GridPane.setHalignment(pC, javafx.geometry.HPos.RIGHT);
        GridPane.setHalignment(stackpointer, javafx.geometry.HPos.RIGHT);

        gridPane.setAlignment(Pos.CENTER);
    }

    // Getter method for the grid pane
    public GridPane getGridPane() {
        return gridPane;
    }
    
    // Setter methods to change the values in the text fields
    public void setwRegValue(String value) {
        textFields[0].setText(value);
    }
    
    public void setfSRValue(String value) {
        textFields[1].setText(value);
    }
    
    public void setpCLValue(String value) {
        textFields[2].setText(value);
    }
    
    public void setpCLATHValue(String value) {
        textFields[3].setText(value);
    }
    
    public void setStatusValue(String value) {
        textFields[4].setText(value);
    }
    
    public void setpCStackPointerValue(String value) {
        textFields[5].setText(value);
    }
}
