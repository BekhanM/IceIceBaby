package model;

import util.TextUI;

import java.util.ArrayList;

public class TrainingProgram {
    TextUI ui = new TextUI();
    ArrayList<Exercises> exercises;
    Exercises BenchPress = new Exercises("Bench Press",20,12);
    ArrayList<String> schedule;
    private int rest;

    public void displayExercises() {
        ui.displayMessage("Exercises: " + exercises);
    }

    public void trainingSchedule() {
        ui.displayMessage("Training Schedule: " + schedule);
    }

    public int intensity() { // Dummy tal lidt for hardcoded
        int intensity = 0;
        int caloriesBurned = 0;
        if(intensity == 1) {
            return caloriesBurned = 100;
        }
        else if(intensity == 2) {
            return caloriesBurned = 125;
        }
        else if(intensity == 3) {
            return caloriesBurned = 150;
        }
        else if(intensity == 3) {
            return caloriesBurned = 175;
        }
        else if(intensity == 4) {
            return caloriesBurned = 200;
        }
        else if(intensity == 5) {
            return caloriesBurned = 225;
        }
        else if(intensity == 6) {
            return caloriesBurned = 250;
        }
        else if(intensity == 7) {
            return caloriesBurned = 275;
        }
        else if(intensity == 8) {
            return caloriesBurned = 300;
        }
        else if(intensity == 9) {
            return caloriesBurned = 325;
        }
        else if(intensity == 10) {
            return caloriesBurned = 350;
        }
        else {
            ui.displayMessage("Invalid number");
        }
        return 0;
    }

    public void trainingTemplate() {

    }
    public void addExercise(ArrayList<Exercises> exercise) {

    }
    public void modifySchedule(String newSchedule) {

    }
    public void addExerciseToDatabase() {

    }
}
