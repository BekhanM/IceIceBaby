package Program;

import model.BMI;
import model.Nutrition;
import model.TrainingProgram;
import model.User;
import util.DatabaseIO;
import util.TextUI;

import javax.xml.crypto.Data;

public class BroScience {
    DatabaseIO dbIO = new DatabaseIO();
    private final TextUI ui = new TextUI();
    private String userInputUsername;
    private String userInputPassword;
    DatabaseIO db = new DatabaseIO();
    BMI bmi = new BMI();
    TrainingProgram tp = new TrainingProgram();
    Nutrition nutrition = new Nutrition();

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
        String i = ui.getInput("Du har nu følgene valgmulighedheder:\n1) Opdater din BMI\n2) Opdater dit træningsprogram\n3) Tilføj mad du har spist\n4) Se træningsprogrammer\n5) Logout");
        switch(i) {
            case "1":
                bmi.updateBMI();
                break;
            case "2":
                tp.modifySchedule();
                break;
            case "3":
                nutrition.addNutrition();
                break;
            case "4":
                tp.trainingTemplate();
                break;
            case "5":
                //logout();
                break;
            default:
                ui.displayMessage("Sværger du en idiot skriv et af tallene din mongol");
                mainMenu();
                break;
        }
        ui.displayMessage("du er logget ind som " + userInputUsername);
    }

    public void login() {
        userInputUsername = ui.getInput("Brugernavn: ");
        userInputPassword = ui.getInput("Kodeord: ");

        User authenticatedUser = db.getAuthenticatedUser(userInputUsername, userInputPassword);

        if (authenticatedUser != null && authenticatedUser.getUsername().equals(userInputUsername) && authenticatedUser.getPassword().equals(userInputPassword)) {
            mainMenu();
        } else {
            login();
        }
    }

}
