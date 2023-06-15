package GUI;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class GUI extends Application{
	List<String> input;
	public GUI(List<String>input) {
		this.input = input;
	}
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
    	AnchorPane leftPane = new AnchorPane();
    	AnchorPane middlePane = new AnchorPane();
        AnchorPane rightPane = new AnchorPane();
        AnchorPane rootPane = new AnchorPane();
        Scene scene = new Scene(rootPane, 1200, 800);
        
        SpecialfunctionRegister sfr = new SpecialfunctionRegister();
        LST_Window lstWindow = new LST_Window(input);
        lstWindow.setDimensions();
        middlePane.getChildren().add(lstWindow.getListView());
        //rightPane.getChildren().add(sfr.getGridPane());
        leftPane.setLayoutX(0);
        
        middlePane.setLayoutX(2.5 / 8.0 * scene.getWidth());
        rightPane.setLayoutX(0.8125 * scene.getWidth());

        rootPane.getChildren().add(middlePane);
        rootPane.getChildren().add(leftPane);
        rootPane.getChildren().add(rightPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
