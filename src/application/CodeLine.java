package application;

import javafx.scene.control.CheckBox;

public class CodeLine {
    
    private String content;
    private CheckBox breakpointCheckBox;
    
    public CodeLine(String content) {
        this.content = content;
        this.breakpointCheckBox = createBreakpointCheckBox();
    }

    // Getters and setters for content and breakpointCheckBox

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isBreakpoint() {
        return breakpointCheckBox.isSelected();
    }

    public void setBreakpoint(boolean breakpoint) {
        breakpointCheckBox.setSelected(breakpoint);
    }

    public CheckBox getBreakpointCheckBox() {
        return breakpointCheckBox;
    }

    private CheckBox createBreakpointCheckBox() {
        CheckBox checkBox = new CheckBox();
        return checkBox;
    }
}
