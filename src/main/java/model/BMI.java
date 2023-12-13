package model;

import util.DatabaseIO;
import util.TextUI;

import javax.swing.*;
import java.text.DecimalFormat;

public class BMI {
    DatabaseIO db = new DatabaseIO();
    String username;
    String password;
    double targetWeight;
    double height;
    double weight;
    int age;
    double bmi;
    BMI bmi1;
    String gender;
    User user = new User(username, password, height, weight, age, gender, bmi1);


    TextUI ui = new TextUI();
    DecimalFormat dec = new DecimalFormat("#,##");


    public double bmiCalculator(double height, double weight, int age) {
        double bmi = weight / (height * height);
        ui.displayMessage("Din BMI er: " + bmi);


        return bmi;
    }


    public void updateBMI(User user) {
        if (JOptionPane.showConfirmDialog(null, "Vil du opdatere dit BMI? ", "BMI UPDATE",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            double newWeight = Double.parseDouble(ui.getInput("Indtast din nye vægt"));
            double height = user.getHeight();
            int age = user.getAge();
            double newBMI = bmiCalculator(height, newWeight, age); // Calculate the new BMI
            db.saveToDatabase(newBMI, newWeight, user.getUsername()); // Assuming you have a method to get the user's ID

            //ui.displayMessage("Din BMI er: " + newBMI);

        }
    }


    public void checkBMI(User user) {
        double weight = user.getWeight();
        double height = user.getHeight();
        int age = user.getAge();

        double userBMI = weight / (height * height);

        if (userBMI < 18.5) {
            ui.displayMessage("Du er sgu en lille stang");
        } else if (userBMI >= 18.5 && userBMI <= 24.9) {
            ui.displayMessage("Perfekt");
        } else if (userBMI >= 25 && userBMI <= 29.9) {
            ui.displayMessage("Du er overvægtigt brormand");
        } else {
            ui.displayMessage("Du skal tabe dig med det samme");
        }
    }
}
