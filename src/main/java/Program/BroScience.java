package Program;

import model.User;
import util.TextUI;

public class BroScience {
    private final TextUI ui = new TextUI();
    private String userInputUsername;
    private String userInputPassword;

    public void startMenu() {
        ui.displayMessage("Velkommen til Bro Science, landets bedste app for bros der elsker gains");
        String i = ui.getInput("Hvad gør du her bro?\n1) Login\n2) Opret ny bruger");
        if (i.equals("1")) {
            //login();
        } else if (i.equals("2")) {
            //addUser();
        } else {
            ui.displayMessage("Ik alt muligt andet, vælg mellem 1 eller 2 bro");
            startMenu();
        }
    }

    public void mainMenu() {

    }

    public void login() {
        userInputUsername = ui.getInput("Brugernavn: ");
        userInputPassword = ui.getInput("Kodeord: ");

                                        // Bruges bare indtil databasen er oprettet
        User authenticatedUser = new User("Pivert","Hvaså",2.10,120.20,22,"mand"); //db.getAuthenticatedUser(userInputUsername, userInputPassword);

        if (authenticatedUser != null && authenticatedUser.getUsername().equals(userInputUsername) && authenticatedUser.getPassword().equals(userInputPassword)) {
            mainMenu();
        } else {
            login();
        }
    }

}
