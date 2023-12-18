package model;

import util.DatabaseIO;
import util.TextUI;

import javax.swing.*;

public class BMI {
    DatabaseIO db = new DatabaseIO();
    TextUI ui = new TextUI();


    public double bmiCalculator(double height, double weight) {
        double bmi = weight / (height * height);
        ui.displayMessage("Din BMI er: " + bmi);
        return bmi;
    }


    public void updateBMI(User user) {
        if (JOptionPane.showConfirmDialog(null, "Vil du opdatere dit BMI? ", "BMI UPDATE",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            double newWeight = Double.parseDouble(ui.getInput("Indtast din nye vægt"));
            double height = user.getHeight();
            double newBMI = bmiCalculator(height, newWeight);
            db.updateBmiDatabase(newBMI, newWeight, user.getUsername());

            //ui.displayMessage("Din BMI er: " + newBMI);
        }
    }

    public void checkBMI(User user) {
        double weight = user.getWeight();
        double height = user.getHeight();
        double userBMI = bmiCalculator(height, weight);

        if (userBMI < 18.5) {
            ui.displayMessage("Du er sgu en lille stang");
        } else if (userBMI >= 18.5 && userBMI <= 24.9) {
            ui.displayMessage("Perfekt");
        } else if (userBMI >= 25 && userBMI <= 29.9) {
            ui.displayMessage("Du er overvægtigt brormand");
        } else if (userBMI >= 30) {
            ui.displayMessage("Du skal tabe dig med det samme");
        }
    }
}
