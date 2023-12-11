package util;

import java.util.ArrayList;
import java.util.Scanner;

public class Datavalidator {

    static final String DB_URL = "database URL";

    // Database credentials
    static final String USER = "username";
    static final String PASS = "password";

    Scanner scanner = new Scanner(System.in);
    TextUI ui = new TextUI();

    //DataBaseIO db = new DataBaseIO(); Mangler Database

    public boolean checkUpperCase(String str) {
        char c;
        boolean upperCaseFlag = false;
        boolean lowerCaseFlag = false;
        for (int i = 0; i < str.length(); i++) {
            c = str.charAt(i);
            if (Character.isUpperCase(c)) {
                upperCaseFlag = true;
            } else if (Character.isLowerCase(c)) {
                lowerCaseFlag = true;
            }
            if (upperCaseFlag && lowerCaseFlag)
                return true;
        }
        ui.displayMessage("Kodeordet skulle have et stort bogstav, sgu da.");
        return false;
    }

    // tjekker længden er minimum på 8 characters i en String (password)
    public boolean checkLength(String str) {

        if (str.length() < 129 && str.length() > 7) {
            return true;
        } else {
            ui.displayMessage("Kodeordet skulle være mindst 8 karakter langt, for satan.");
            return false;
        }
    }

    // tjekker om en String (password) indeholder et tal
    public boolean checkNumeric(String str) {
        char c;
        boolean numberFlag = false;
        for (int i = 0; i < str.length(); i++) {
            c = str.charAt(i);
            if (Character.isDigit(c)) {
                return true;
            }
        }

        if (!numberFlag) {
            ui.displayMessage("Kodeordet skulle have et fucking tal.");
        }
        return false;
    }

    //tjekker om password opfylder kriterier og returnerer boolean
    public boolean validatePassword(String password) {
        boolean i = checkNumeric(password);
        boolean j = checkLength(password);
        boolean k = checkUpperCase(password);
        if (i && j && k) {
            return true;
        } else {
            return false;
        }
    }

    // Skal tjekke om username/password allerede eksisterer ved at sammenligne indhold i arraylist med ny brugerinput
    public boolean checkRegisterUsernamePassword(ArrayList<String> users, String enteredUsername) {

        for (String user : users) {
            String[] userInfo = user.split(",");
            String username = userInfo[0];
            if (username.contentEquals(enteredUsername)) {
                ui.displayMessage("Brugernavn findes, prøv igen ");
                return false;
            }
        }
        return true;
    }
}
