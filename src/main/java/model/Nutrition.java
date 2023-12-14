package model;

import util.DatabaseIO;

public class Nutrition {
    int userID;
    String username;
    String password;
    double height;
    double weight;
    int age;
    String gender;
    BMI bmi = new BMI();
    BMI yourBMI = new BMI();
    User user = new User(userID,username,password,height,weight,age,gender,yourBMI);
    DatabaseIO db = new DatabaseIO();

    public void addNutrition() {
        //db.searchForFood();
        //addFood(); MÅSKE?
    }

    public void displayNutrition() {
        //db.displayFood();
    }

    public void recommendedNutritionIntakeMaintenance() {
        String gender = user.getGender();
        int recommendedCal = 0;
        int recommendedProt = 0;
//**********************************************************************************//
        //100kg
        if (yourBMI.bmiCalculator(height,weight,age) >= 30) {
            recommendedCal += 2778;
            recommendedProt += 208;

            if (gender.equalsIgnoreCase("female")) {
                recommendedCal -= 229;
                recommendedProt -= 17;
            }
//**********************************************************************************//
            //90kg
        } else if (yourBMI.bmiCalculator(height,weight,age) >= 25 && yourBMI.bmiCalculator(height,weight,age) <= 29.99) {
            recommendedCal += 2640;
            recommendedProt += 198;

            if (gender.equalsIgnoreCase("female")) {
                recommendedCal -= 228;
                recommendedProt -= 17;
            }
//**********************************************************************************//
            //70kg
        } else if (yourBMI.bmiCalculator(height,weight,age) >= 18.5 && yourBMI.bmiCalculator(height,weight,age) <= 24.99) {
            recommendedCal += 2365;
            recommendedProt += 177;

            if (gender.equalsIgnoreCase("female")) {
                recommendedCal -= 228;
                recommendedProt -= 28;
            }
//**********************************************************************************//
            //50
        } else if (yourBMI.bmiCalculator(height,weight,age) < 18.5) {
            recommendedCal += 2090;
            recommendedProt += 157;

            if (gender.equalsIgnoreCase("female")) {
                recommendedCal -= 228;
                recommendedProt -= 17;
            }
        }
//**********************************************************************************//
        System.out.println("Recommended Calories: " + recommendedCal);
        System.out.println("Recommended Protein: " + recommendedProt);
    }


    public void recommendedNutritionIntakeCut() {
        String gender = user.getGender();
        int recommendedCal = 0;
        int recommendedProt = 0;
//**********************************************************************************//
        //100kg
        if (yourBMI.bmiCalculator(height,weight,age) >= 30) {
            recommendedCal += 2278;
            recommendedProt += 171;

            if (gender.equalsIgnoreCase("female")) {
                recommendedCal -= 229;
                recommendedProt -= 17;
            }
//**********************************************************************************//
            //90kg
        } else if (yourBMI.bmiCalculator(height,weight,age) >= 25 && yourBMI.bmiCalculator(height,weight,age) <= 29.99) {
            recommendedCal += 2140;
            recommendedProt += 163;

            if (gender.equalsIgnoreCase("female")) {
                recommendedCal -= 228;
                recommendedProt -= 20;
            }
//**********************************************************************************//
            //70kg
        } else if (yourBMI.bmiCalculator(height,weight,age) >= 18.5 && yourBMI.bmiCalculator(height,weight,age) <= 24.99) {
            recommendedCal += 1865;
            recommendedProt += 140;

            if (gender.equalsIgnoreCase("female")) {
                recommendedCal -= 228;
                recommendedProt -= 17;
            }
//**********************************************************************************//
            //50
        } else if (yourBMI.bmiCalculator(height,weight,age) < 18.5) {
            recommendedCal += 1590;
            recommendedProt += 119;

            if (gender.equalsIgnoreCase("female")) {
                recommendedCal -= 228;
                recommendedProt -= 17;
            }
        }
//**********************************************************************************//
        System.out.println("Recommended Calories: " + recommendedCal);
        System.out.println("Recommended Protein: " + recommendedProt);
    }


    public void recommendedNutritionIntakeBulk() {
        String gender = user.getGender();
        int recommendedCal = 0;
        int recommendedProt = 0;
//**********************************************************************************//
        //100kg
        if (yourBMI.bmiCalculator(height,weight,age) >= 30) {
            recommendedCal += 3278;
            recommendedProt += 246;

            if (gender.equalsIgnoreCase("female")) {
                recommendedCal -= 229;
                recommendedProt -= 17;
            }
//**********************************************************************************//
            //90kg
        } else if (yourBMI.bmiCalculator(height,weight,age) >= 25 && yourBMI.bmiCalculator(height,weight,age) <= 29.99) {
            recommendedCal += 3140;
            recommendedProt += 236;

            if (gender.equalsIgnoreCase("female")) {
                recommendedCal -= 228;
                recommendedProt -= 18;
            }
//**********************************************************************************//
            //70kg
        } else if (yourBMI.bmiCalculator(height,weight,age) >= 18.5 && yourBMI.bmiCalculator(height,weight,age) <= 24.99) {
            recommendedCal += 2865;
            recommendedProt += 215;

            if (gender.equalsIgnoreCase("female")) {
                recommendedCal -= 228;
                recommendedProt -= 17;
            }
//**********************************************************************************//
            //50
        } else if (yourBMI.bmiCalculator(height,weight,age) < 18.5) {
            recommendedCal += 2590;
            recommendedProt += 194;

            if (gender.equalsIgnoreCase("female")) {
                recommendedCal -= 228;
                recommendedProt -= 17;
            }
        }
//**********************************************************************************//
        System.out.println("Recommended Calories: " + recommendedCal);
        System.out.println("Recommended Protein: " + recommendedProt);
    }


    public void addNewFood() {

    }
}
