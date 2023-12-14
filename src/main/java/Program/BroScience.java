package Program;

import model.BMI;
import model.Nutrition;
import model.TrainingProgram;
import model.User;
import util.DatabaseIO;
import util.TextUI;

public class BroScience {
    private final TextUI ui = new TextUI();
    DatabaseIO dbIO = new DatabaseIO();
    DatabaseIO db = new DatabaseIO();
    BMI bmi = new BMI();
    TrainingProgram tp = new TrainingProgram();
    Nutrition nutrition = new Nutrition();
    private String userInputUsername;
    private String userInputPassword;

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
        String i = ui.getInput("Du har nu følgene valgmulighedheder:" +
                "\n1) Opdater din BMI" +
                "\n2) Opdater dit træningsprogram" +
                "\n3) Tilføj mad til databasen" +
                "\n4) Se dit træningsporgram" +
                "\n5) Tilføj mad du har spist" +
                "\n6) Logout");


        switch (i) {
            case "1":
                bmi.updateBMI(db.getAuthenticatedUser(userInputUsername, userInputPassword));
                bmi.checkBMI(db.getAuthenticatedUser(userInputUsername, userInputPassword));
                break;
            case "2":
                tp.modifySchedule();
                break;
            case "3":
                db.addFood();
                break;
            case "4":
                tp.trainingTemplate();
            case "5":
                db.searchFood();
                db.addFoodIntake(db.getAuthenticatedUser(userInputUsername,userInputPassword));
                break;
            case "6":
                //logout();
                break;
            default:
                ui.displayMessage("Sværger du en idiot skriv et af tallene din mongol");
                mainMenu();
                break;
        }
    }

    public void login() {
        userInputUsername = ui.getInput("Brugernavn: ");
        userInputPassword = ui.getInput("Kodeord: ");

        User authenticatedUser = db.getAuthenticatedUser(userInputUsername, userInputPassword);

        if (authenticatedUser != null && authenticatedUser.getUsername().equals(userInputUsername) && authenticatedUser.getPassword().equals(userInputPassword)) {
            ui.displayMessage("Nice dude, username/password passer!");
            ui.displayMessage("Velkommen, " + userInputUsername + " the GOAT!");
            ui.displayMessage("Ser stærk ud i dag, " + userInputUsername + "!");
            mainMenu();
        } else {
            login();
        }
    }

}
