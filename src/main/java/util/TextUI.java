package util;

import javax.swing.*;

public class TextUI {

    public String getInput(String msg) {
        return JOptionPane.showInputDialog(msg);
    }

    public void displayMessage(String msg) {
        JOptionPane.showMessageDialog(null, msg);
    }
}
