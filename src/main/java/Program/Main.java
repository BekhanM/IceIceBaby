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
        BroScience bro = new BroScience();
        TrainingProgram proBro = new TrainingProgram();

        //bro.startMenu();
        proBro.displayExercises();

    }
}