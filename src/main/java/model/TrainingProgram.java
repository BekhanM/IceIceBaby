package model;

import util.TextUI;

import java.sql.*;

public class TrainingProgram {
    //---------------------------------------------------------------------
    static final String DB_URL = "jdbc:mysql://localhost/broscience";
    //  Database credentials
    static final String USER = "root";
    static final String PASS = "Heisenberg2001!";
    //-------------------------------------------------------------------------
    TextUI ui = new TextUI();

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
            String sql = "SELECT exerciseID, name, focusGroup FROM broscience.exercises";
            stmt = conn.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            //STEP 4: Extract data from result set
            while (rs.next()) {
                //Retrieve by column name

                int exerciseID = rs.getInt("exerciseID");
                String name = rs.getString("Name");
                String focusGroup = rs.getString("focusGroup");

                String formatString = "workoutID: %-12.10sName: %-45sfocusGroup: %-12.10s";
                String formattedOutput = String.format(formatString,exerciseID, name, focusGroup);
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

    public void addExerciseToDatabase() {
        Connection conn;
        PreparedStatement stmt;


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
                ui.displayMessage(name + " er tilføjet");
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
