package application;
import java.util.Scanner;  
import java.util.List;

import GUI.GUI;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	static List<String> input;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //String filename = getFilename();
        String path = "C:\\Users\\Fabian\\Documents\\TPicSim1.LST";
        ReadLstFile readLstFile = new ReadLstFile();
        input = readLstFile.readFile(path);
        GUI gui = new GUI(input);
        gui.start(primaryStage);
        System.out.println("Test");
    }

    public List<String> getInput() {
		return input;
	}

	public String getFilename() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Geben Sie den Namen der Datei an:");
        String eingabe = scanner.next();
        return eingabe;
    }
    
}
