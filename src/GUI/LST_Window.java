package GUI;

import application.CodeLine;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

import java.util.List;

public class LST_Window {
    private ListView<CodeLine> listView = new ListView<>();
    private List<String> input;

    public LST_Window(List<String> input) {
        this.input = input;
        updateListView();
    }

    public void updateListView() {
    	listView.setCellFactory(param -> new ListCell<CodeLine>() {
    	    // Cell factory implementation

    	    @Override
    	    protected void updateItem(CodeLine codeLine, boolean empty) {
    	        super.updateItem(codeLine, empty);

    	        if (empty || codeLine == null) {
    	            setGraphic(null);  // Clear the graphic if empty
    	        } else {
    	            // Update the cell contents based on the codeLine object
    	            // ...
    	        }
    	    }
    	});

    	// Create and add CodeLine objects to the ListView
    	listView.getItems().add(new CodeLine("Content 1"));
    	listView.getItems().add(new CodeLine("Content 2"));
    	listView.getItems().add(new CodeLine("Content 3"));
    	// Add more CodeLine objects as needed

    }
    public ListView<CodeLine> getListView() {
        return listView;
    }

    public void setDimensions() {
        listView.setEditable(false);
        listView.setLayoutY(400);
        listView.setPrefHeight(400);
        listView.setPrefWidth(600);
    }
}
