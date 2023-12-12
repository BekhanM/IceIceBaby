package model;

import util.TextUI;

import javax.swing.*;
import java.text.DecimalFormat;

public class BMI {

    double targetWeight;
    double height;
    double weight;
    int age;
    double bmi;
    boolean gender;


    TextUI ui = new TextUI();
    DecimalFormat dec = new DecimalFormat("#,##");


   public double bmiCalculator(/*double weight,double height,int age*/){
       weight =  Double.parseDouble(ui.getInput("Indtast din vægt i metrisk"));
       height =  Double.parseDouble(ui.getInput("Indtast din højde i metrisk"));
       age = Integer.parseInt(ui.getInput("Indtast din alder"));

       bmi = weight/(height*height);

       ui.displayMessage("Din BMI er :"+ bmi);

       checkBMI();

       return bmi;
    }

   public void updateBMI(){
       if (JOptionPane.showConfirmDialog(null, "Vil du opdatere dit BMI? ", "BMI UPDATE",
               JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
           bmiCalculator();
        }
       }

   public void checkBMI() {
       if (bmi < 18.5) {
           ui.displayMessage("Du er sgu en lille stang");
       } else if (bmi >= 18.5 && bmi <= 24.9) {
           ui.displayMessage("Perfekt");
       } else if (bmi >= 25 && bmi <= 29.9) {
           ui.displayMessage("Du er overvægtigt brormand");
       } else {
           ui.displayMessage("Du skal tabe dig med det samme");
       }
   }
}
