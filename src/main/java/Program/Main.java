package Program;

import model.BMI;
import model.PremadeTrainingProgram;
import model.TrainingProgram;
import model.User;
import util.DatabaseIO;

public class Main {

    public static void main(String[] args) {
        BroScience bro = new BroScience();
        TrainingProgram proBro = new TrainingProgram();
        DatabaseIO db = new DatabaseIO();

        // bmi.updateBMI(user);

        bro.startMenu();
    }
}