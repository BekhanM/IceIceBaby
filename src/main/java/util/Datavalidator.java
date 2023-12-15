package util;

public class Datavalidator {
    TextUI ui = new TextUI();

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
        return false;
    }

    // tjekker længden er minimum på 8 characters i en String (password)
    public boolean checkLength(String str) {

        return str.length() < 129 && str.length() > 7;
    }

    // tjekker om en String (password) indeholder et tal
    public boolean checkNumeric(String str) {
        char c;
        for (int i = 0; i < str.length(); i++) {
            c = str.charAt(i);
            if (Character.isDigit(c)) {
                return true;
            }
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
            if (!i) {
                ui.displayMessage("Kodeordet skulle have et fucking tal");
            }
            if (!j) {
                ui.displayMessage("Kodeordet skulle være mindst 8 karakter langt, for satan");
            }
            if (!k) {
                ui.displayMessage("Kodeordet skulle have et stort bogstav, sgu da");
            }
            return false;
        }
    }
    }

