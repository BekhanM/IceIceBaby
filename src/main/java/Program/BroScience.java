package Program;

import model.*;
import util.DatabaseIO;
import util.TextUI;

public class BroScience {
    private final TextUI ui = new TextUI();
    DatabaseIO db = new DatabaseIO();
    BMI bmi = new BMI();
    Nutrition nutrition = new Nutrition();
    PremadeTrainingProgram ptr = new PremadeTrainingProgram();
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
            ui.displayMessage("Ik alt muligt andet, vælg mellem 1 eller 2, bro");
            startMenu();
        }
    }

    public void mainMenu() {
        String i = ui.getInput("""
                Du har nu følgene valgmuligheder:
                1) Opdater din BMI
                2) Opdater din diæt
                3) Tilføj mad til databasen
                4) Se DIT træningsprogram
                5) Vis anbefalede træningsprogram
                6) Tilføj dag
                7) Tilføj øvelse til dag
                8) Tilføj indtaget mad
                9) Vis indtaget mad
                10) Log ud
                11) Luk programmet
                """
                + displayRecCal() + "\n" + displayEatenCal());

        switch (i) {
            case "1":
                bmi.updateBMI(db.getAuthenticatedUser(userInputUsername, userInputPassword));
                bmi.checkBMI(db.getAuthenticatedUser(userInputUsername, userInputPassword));
                mainMenu();
                break;
            case "2":
                db.updateWeightWish(userInputUsername);
                mainMenu();
                break;
            case "3":
                db.addFood();
                mainMenu();
                break;
            case "4":
                db.displayProgram();
                mainMenu();
                break;
            case "5":
                ptr.displayTrainingMaintenance(db.getWeighWishFromDatabase(userInputUsername, userInputPassword));
                mainMenu();
                break;
            case "6":
                db.addDay(db.getAuthenticatedUser(userInputUsername, userInputPassword));
                mainMenu();
                break;
            case "7":
                db.addDayToProgram();
                mainMenu();
                break;
            case "8":
                db.searchFood();
                db.addFoodIntake(db.getAuthenticatedUser(userInputUsername, userInputPassword));
                mainMenu();
                break;
            case "9":
                db.soutFoodIntake(db.getAuthenticatedUser(userInputUsername,userInputPassword));
                mainMenu();
                break;
            case "10":
                logout();
                break;
            case "11":
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
            ui.displayMessage("Nice! Username/password passer!");
            ui.displayMessage("Velkommen, " + userInputUsername + " the GOAT!");
            ui.displayMessage("Ser stærk ud i dag, " + userInputUsername + "!");
            mainMenu();
        } else {
            login();
        }
    }

    public String displayRecCal() {
        return nutrition.nutritionWish(db.getBMIFromDatabase(userInputUsername, userInputPassword),
                db.getGenderFromDatabase(userInputUsername, userInputPassword),
                db.getWeighWishFromDatabase(userInputUsername, userInputPassword));
    }

    public String displayEatenCal() {
        return db.displayFoodIntake(db.getAuthenticatedUser(userInputUsername, userInputPassword));
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