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
        String i = ui.getInput("Hvad gør du her bro?" +
                "\n1) Login" +
                "\n2) Opret ny bruger");
        if (i.equals("1")) {
            login();
        } else if (i.equals("2")) {
            db.addUser();
            startMenu();
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
                "\n6) Vis mad indtaget"+
                "\n7) Tilføj dag"+
                "\n8) Vis dine træningsdage" +
                "\n9) Logout" +
                "\n10) Luk programmet" +
                "\n11) test");

        switch (i) {
            case "1":
                bmi.updateBMI(db.getAuthenticatedUser(userInputUsername, userInputPassword));
                bmi.checkBMI(db.getAuthenticatedUser(userInputUsername, userInputPassword));
                mainMenu();
                break;
            case "2":
                tp.modifySchedule();
                mainMenu();
                break;
            case "3":
                db.addFood();
                mainMenu();
                break;
            case "4":
                tp.trainingTemplate();
                mainMenu();
            case "5":
                db.searchFood();
                db.addFoodIntake(db.getAuthenticatedUser(userInputUsername,userInputPassword));
                //db.selectFoodAndCalculateIntake();
                mainMenu();
                break;
            case "6":
                db.displayFoodIntake(db.getAuthenticatedUser(userInputUsername,userInputPassword));
                mainMenu();
                break;
            case "7":
                db.addDay(db.getAuthenticatedUser(userInputUsername,userInputPassword));
                mainMenu();
                break;
            case "8":
                db.displayDays();
                mainMenu();
                break;
            case "9":
                logout();
            case "10":
                break;
            case "11":
                tp.addExerciseToDatabase();
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
    public void logout() {
        String i = ui.getInput("Er du sikker du vil logge ud, bro?" +
                "\nTast 1 hvis du gerne vil logge ud:" +
                "\nTast 2 hvis du ikke vil logge ud:");
        if (i.equals("1")) {
            startMenu();
        } else if (i.equals("2")) {
            mainMenu();
        } else {
            ui.displayMessage("Forkert valg. Vælg funktion 1 eller 2.");
            logout();
        }
    }

}
