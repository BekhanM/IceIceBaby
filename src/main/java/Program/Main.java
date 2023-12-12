package Program;
import model.Nutrition;

import model.User;

import model.BMI;
import util.DatabaseIO;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
      /*  BroScience bro = new BroScience();
        bro.startMenu();
        Nutrition nutrition = new Nutrition();

        User user = new User("Otto","taber",1.94,94.20,22,"mand");
        System.out.println(user);
        System.out.println("Vil du bulke?");
        nutrition.recommendedNutritionIntakeBulk();
     BMI bmi = new BMI();
     bmi.updateBMI();*/

        DatabaseIO dbIO = new DatabaseIO();
        //dbIO.displayFood();
        //dbIO.searchExercise();
        dbIO.searchFood();

    }
}