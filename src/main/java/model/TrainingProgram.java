package model;

import util.TextUI;

import java.sql.*;
import java.util.ArrayList;

public class TrainingProgram {
    //---------------------------------------------------------------------
    static final String DB_URL = "jdbc:mysql://localhost/broscience";
    //  Database credentials
    static final String USER = "root";
    static final String PASS = "NieMeyerRull2";
    //-------------------------------------------------------------------------


    TextUI ui = new TextUI();
    ArrayList<Exercises> exercises;
    Exercises BenchPress = new Exercises("Bench Press",20,12);
    ArrayList<String> schedule;
    private int rest;

    public void displayExercises() {

        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            //STEP 1: Register JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            //STEP 2: Open a connection
            //ui.displayMessage("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //STEP 3: Execute a query
            // ui.displayMessage("Creating statement...");
            String sql = "SELECT name, focusGroup FROM broscience.exercises";
            stmt = conn.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            //STEP 4: Extract data from result set
            while (rs.next()) {
                //Retrieve by column name


                String name = rs.getString("Name");
                String focusGroup = rs.getString("focusGroup");

                String formatString = "Name: %-45sfocusGroup: %-12.2s";
                String formattedOutput = String.format(formatString, name,focusGroup);
                System.out.println(formattedOutput);

            }
            //STEP 5: Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {

            e.printStackTrace();
        } finally {

            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se2) {
            }
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
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
    public void modifySchedule() {

    }
    public void addExerciseToDatabase() {
        Connection conn = null;
        PreparedStatement stmt = null;



        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            String sql = "INSERT INTO EXERCISES (NAME, FOCUSGROUP) VALUES (?, ?);";
            stmt = conn.prepareStatement(sql);


            String name = ui.getInput("Indtast den øvelse du vil tilføje");
            String muscleGroup = ui.getInput("Hvad for en muskelgruppe tilhører den til?");
            stmt.setString(1, name);
            stmt.setString(2, muscleGroup);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                ui.displayMessage(name+" er tilføjet");
            } else {
                ui.displayMessage("Mislykkes at gemme");
            }

            stmt.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
