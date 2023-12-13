package Program;

import model.User;
import util.DatabaseIO;
import util.TextUI;

import javax.xml.crypto.Data;

public class BroScience {
    private final TextUI ui = new TextUI();
    private String userInputUsername;
    private String userInputPassword;
    DatabaseIO db = new DatabaseIO();

    public void startMenu() {
        ui.displayMessage("Velkommen til Bro Science, landets bedste app for bros der elsker gains!");
        String i = ui.getInput("Hvad gør du her bro?\n1) Login\n2) Opret ny bruger");
        if (i.equals("1")) {
            login();
        } else if (i.equals("2")) {
            db.addUser();
        } else {
            ui.displayMessage("Ik alt muligt andet, vælg mellem 1 eller 2 bro");
            startMenu();
        }
    }

    public void mainMenu() {
        ui.displayMessage("du er logget ind som " + userInputUsername);
    }

    public void login() {
        userInputUsername = ui.getInput("Brugernavn: ");
        userInputPassword = ui.getInput("Kodeord: ");

                                        // Bruges bare indtil databasen er oprettet
        User authenticatedUser = db.getAuthenticatedUser(userInputUsername, userInputPassword);


        if (authenticatedUser != null && authenticatedUser.getUsername().equals(userInputUsername) && authenticatedUser.getPassword().equals(userInputPassword)) {
            mainMenu();
        } else {
            login();
        }
    }

}
