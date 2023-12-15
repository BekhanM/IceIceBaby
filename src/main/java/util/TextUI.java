package util;

import javax.swing.*;
import java.util.Scanner;


public class TextUI {
    private Scanner scan = new Scanner(System.in);

    public String getInput(String msg) {
        return JOptionPane.showInputDialog(msg);
    }

    public void displayMessage(String msg) {
        JOptionPane.showMessageDialog(null, msg);
    }
}
