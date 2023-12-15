package Program;

import model.BMI;
import model.TrainingProgram;
import model.User;
import util.DatabaseIO;

public class Main {

    public static void main(String[] args) {
        BMI bmi = new BMI();
        User user = new User(2, "", "asfasfafs", 1.90, 100, 23, "other", bmi);
        BroScience bro = new BroScience();
        TrainingProgram proBro = new TrainingProgram();
        DatabaseIO db = new DatabaseIO();

        // bmi.updateBMI(user);

        bro.startMenu();
        //proBro.displayExercises();
        // db.displayFoodIntake();

    }
}