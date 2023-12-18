package model;

import util.DatabaseIO;
import util.TextUI;

public class Nutrition {
    int userID;
    String username;
    String password;
    double height;
    double weight;
    int age;
    //String gender;
    BMI yourBMI = new BMI();
    //User user = new User(userID, username, password, height, weight, age, gender, yourBMI);
    DatabaseIO db = new DatabaseIO();
    TextUI ui = new TextUI();

    public void addNutrition() {
        //db.searchForFood();
        //addFood(); MÅSKE?
    }

    public void displayNutrition() {
        //db.displayFood();
    }

    public String recommendedNutritionIntakeMaintenance(double bmiFromDatabase, String gender) {

        int recommendedCal = 0;
        int recommendedProt = 0;
//**********************************************************************************//
        //100kg
        if (bmiFromDatabase >= 30) {
            recommendedCal += 2778;
            recommendedProt += 208;

            if (gender.equalsIgnoreCase("kvinde")) {
                recommendedCal -= 229;
                recommendedProt -= 17;
            }
//**********************************************************************************//
            //90kg
        } else if (bmiFromDatabase >= 25 && bmiFromDatabase <= 29.99) {
            recommendedCal += 2640;
            recommendedProt += 198;

            if (gender.equalsIgnoreCase("kvinde")) {
                recommendedCal -= 228;
                recommendedProt -= 17;
            }
//**********************************************************************************//
            //70kg
        } else if (bmiFromDatabase >= 18.5 && bmiFromDatabase <= 24.99) {
            recommendedCal += 2365;
            recommendedProt += 177;

            if (gender.equalsIgnoreCase("kvinde")) {
                recommendedCal -= 228;
                recommendedProt -= 28;
            }
//**********************************************************************************//
            //50
        } else if (bmiFromDatabase < 18.5) {
            recommendedCal += 2090;
            recommendedProt += 157;

            if (gender.equalsIgnoreCase("kvinde")) {
                recommendedCal -= 228;
                recommendedProt -= 17;
            }
        }
//**********************************************************************************//
        return "\nAnbefalet Daglig Kalorierindtag " + recommendedCal + "\nAnbefalet Daglig Proteinindtag " + recommendedProt;
    }


    public String recommendedNutritionIntakeCut(double bmiFromDatabase, String gender) {
        int recommendedCal = 0;
        int recommendedProt = 0;
//**********************************************************************************//
        //100kg
        if (bmiFromDatabase >= 30) {
            recommendedCal += 2278;
            recommendedProt += 171;

            if (gender.equalsIgnoreCase("kvinde")) {
                recommendedCal -= 229;
                recommendedProt -= 17;
            }
//**********************************************************************************//
            //90kg
        } else if (bmiFromDatabase >= 25 && bmiFromDatabase <= 29.99) {
            recommendedCal += 2140;
            recommendedProt += 163;

            if (gender.equalsIgnoreCase("kvinde")) {
                recommendedCal -= 228;
                recommendedProt -= 20;
            }
//**********************************************************************************//
            //70kg
        } else if (bmiFromDatabase >= 18.5 && bmiFromDatabase <= 24.99) {
            recommendedCal += 1865;
            recommendedProt += 140;

            if (gender.equalsIgnoreCase("kvinde")) {
                recommendedCal -= 228;
                recommendedProt -= 17;
            }
//**********************************************************************************//
            //50
        } else if (bmiFromDatabase < 18.5) {
            recommendedCal += 1590;
            recommendedProt += 119;

            if (gender.equalsIgnoreCase("kvinde")) {
                recommendedCal -= 228;
                recommendedProt -= 17;
            }
        }
//**********************************************************************************//
        return "\nAnbefalet Daglig Kalorierindtag " + recommendedCal + "\nAnbefalet Daglig Proteinindtag " + recommendedProt;
    }


    public String recommendedNutritionIntakeBulk(double bmiFromDatabase, String gender) {
        int recommendedCal = 0;
        int recommendedProt = 0;
//**********************************************************************************//
        //100kg
        if (bmiFromDatabase >= 30) {
            recommendedCal += 3278;
            recommendedProt += 246;

            if (gender.equalsIgnoreCase("kvinde")) {
                recommendedCal -= 229;
                recommendedProt -= 17;
            }
//**********************************************************************************//
            //90kg
        } else if (bmiFromDatabase >= 25 && bmiFromDatabase <= 29.99) {
            recommendedCal += 3140;
            recommendedProt += 236;

            if (gender.equalsIgnoreCase("kvinde")) {
                recommendedCal -= 228;
                recommendedProt -= 18;
            }
//**********************************************************************************//
            //70kg
        } else if (bmiFromDatabase >= 18.5 && bmiFromDatabase <= 24.99) {
            recommendedCal += 2865;
            recommendedProt += 215;

            if (gender.equalsIgnoreCase("kvinde")) {
                recommendedCal -= 228;
                recommendedProt -= 17;
            }
//**********************************************************************************//
            //50
        } else if (bmiFromDatabase < 18.5) {
            recommendedCal += 2590;
            recommendedProt += 194;

            if (gender.equalsIgnoreCase("kvinde")) {
                recommendedCal -= 228;
                recommendedProt -= 17;
            }
        }
//**********************************************************************************//
        return "\nAnbefalet Daglig Kalorierindtag: " + recommendedCal + "Anbefalet Daglig Proteinindtag " + recommendedProt;
    }

    public String nutritionWish(double bmiFromDatabase, String gender, String weightWishFromDatabase) {
        String result = "";

        if (weightWishFromDatabase.equals("bulke")) {
            result = recommendedNutritionIntakeBulk(bmiFromDatabase, gender);
        } else if (weightWishFromDatabase.equals("cutte")) {
            result = recommendedNutritionIntakeCut(bmiFromDatabase, gender);
        } else if (weightWishFromDatabase.equals("leve")) {
            result = recommendedNutritionIntakeMaintenance(bmiFromDatabase, gender);
        }

        return result;
    }

    public void addNewFood() {

    }
}
