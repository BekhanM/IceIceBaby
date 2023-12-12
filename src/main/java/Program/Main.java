package Program;
import model.Nutrition;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Nutrition nutrition = new Nutrition();

        System.out.println("Vil du bulke?");
        nutrition.recommendedNutritionIntakeBulk();
    }
}