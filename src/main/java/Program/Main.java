package Program;
import model.Nutrition;

import model.TrainingProgram;
import model.User;

import model.BMI;
import util.DatabaseIO;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        BMI bmi  = new BMI();
        User user = new User("dfdfd","asfasfafs",1.90,100,23,"other",bmi);
        BroScience bro = new BroScience();
        TrainingProgram proBro = new TrainingProgram();
        DatabaseIO db = new DatabaseIO();



       // bmi.updateBMI(user);
        bro.startMenu();
        //proBro.displayExercises();

    }
}