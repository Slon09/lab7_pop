package main;

import java.awt.event.WindowEvent;

public class RunEditor {

    public static void main(String[] args) throws Exception {

        Editor editor = new Editor();
        editor.startEditor();

        while(editor.exitCondition){
            editor.react(editor.getTerminal().getCommand());
        }

        editor.getTerminal().dispatchEvent(new WindowEvent(editor.getTerminal(), WindowEvent.WINDOW_CLOSING));

    }

}
