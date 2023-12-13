package util;

import model.BMI;
import model.User;

import java.sql.*;

public class DatabaseIO {

    static final String DB_URL = "jdbc:mysql://localhost/broscience";
    //  Database credentials
    static final String USER = "root";
    static final String PASS = "Heisenberg2001!";
    String username1;
    String password1;
    User user = new User("pølsefar","pølsefar1234",182.0,81.0,22,"female");
    TextUI ui = new TextUI();
    Datavalidator dv = new Datavalidator();
    int userChoice = 0;
    String mediaContentName;
    BMI bmi = new BMI();

    public void addUser() {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            String username = ui.getInput("indtast din brugernavn smukke");

            // Check if the username already exists in the database
            if (!checkUsername( username)) {
                String password = ui.getInput("indtast din kodeord");
                if(dv.validatePassword(password)==true){
                    double height = Double.parseDouble(ui.getInput("Indtast din højde"));
                    double weight = Double.parseDouble(ui.getInput("Indtast din vægt"));
                    int age = Integer.parseInt(ui.getInput("Indtast din alder flinke"));
                    String gender = ui.getInput("Er du mand,kone eller noget andet?");
                    double userBMI = bmi.bmiCalculator(height,weight,age);

                    String sql = "INSERT INTO USER (username, password,height,weight,age,gender,bmi) VALUES (?, ? , ? , ? , ? , ? , ?)";
                    stmt = conn.prepareStatement(sql);
                    stmt.setString(1, username);
                    stmt.setString(2, password);
                    stmt.setDouble(3, height);
                    stmt.setDouble(4, weight);
                    stmt.setInt(5, age);
                    stmt.setString(6, gender);
                    stmt.setDouble(7, userBMI);
                }

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    ui.displayMessage("Bruger er gemt");
                } else {
                    ui.displayMessage("Mislykkedes at gemme brugeren");
                }
            } else {
                ui.displayMessage("Brugernavn eksisterer din dumme svin");
                addUser();
            }

            if (stmt != null) {
                stmt.close();
            }
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private boolean checkUsername(String username) throws SQLException {
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)){
            String query = "SELECT COUNT(*) FROM USER WHERE Username = ?";
            try (PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setString(1, username);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        int count = resultSet.getInt(1);
                        return count > 0;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }



    public User getAuthenticatedUser(String username, String puffPass) {
        Connection conn = null;
        PreparedStatement stmt = null;

        user = null;
        try {
            //STEP 1: Register JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            //STEP 2: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            String sql = "SELECT username, password FROM USER WHERE username = ? AND password = ? ";
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, username);
            stmt.setString(2, puffPass);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user = new User(username, puffPass,182.0,81.0,22,"female");
                ui.displayMessage("Nice dude, username/password passer!");
                ui.displayMessage("Velkommen, " + username + " the GOAT!");
                ui.displayMessage("Ser stærk ud i dag, " + username + "!");
            } else {
                ui.displayMessage("Brugernavn findes ikke, ellers kan du ikke stave, dumbass.");
            }
            stmt.close();
            conn.close();


        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return user;
    }

    public void displayFood() {
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
            String sql = "SELECT name, caloriesPr100, proteinPr100 FROM broscience.nutrition";
            stmt = conn.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            //STEP 4: Extract data from result set
            while (rs.next()) {
                //Retrieve by column name


                String name = rs.getString("Name");
                double caloriesPr100 = rs.getDouble("caloriesPr100");
                double proteinPr100= rs.getDouble("proteinPr100");

                String formatString = "Name: %-45sCalories: %-12.2fProtein: %-5.2f";
                String formattedOutput = String.format(formatString, name, caloriesPr100, proteinPr100);
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
            }//end finally try
        }//end try
    }


    public void searchExercise() {
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
            String s = ui.getInput("Søg efter den øvelse du gerne vil se, bro.");
            String sql = "SELECT exerciseID, name, focusGroup FROM broscience.exercises WHERE INSTR(name, '" + s + "') > 0 ";
            stmt = conn.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery(sql);

            //STEP 4: Extract data from result set
            while (rs.next()) {
                //Retrieve by column name
                String name = rs.getString("Name");
                String focusGroup = rs.getString("focusGroup");

                String exercise = "\nName: " + name + "\nFocus Group: " + focusGroup;

                System.out.println(exercise);
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
            }//end finally try
        }
    }
    public void searchFood() {
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
            String s = ui.getInput("Søg efter alt det lækre mad med gains, bro.");
            String sql = "SELECT foodID, name, caloriesPr100, proteinPr100 FROM broscience.nutrition WHERE INSTR(name, '" + s + "') > 0 ";
            stmt = conn.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery(sql);

            //STEP 4: Extract data from result set
            while (rs.next()) {
                //Retrieve by column name
                String name = rs.getString("Name");
                double caloriesPr100 = rs.getDouble("CaloriesPr100");
                double proteinPr100 = rs.getDouble("proteinpr100");

                String searchForFood = "\nName: " + name + "\nCalories: " + caloriesPr100 + "\nProtein: " + proteinPr100;

                System.out.println(searchForFood);
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
            }//end finally try
        }
    }


    public void addFood() {
        Connection conn = null;
        PreparedStatement stmt = null;



        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            String sql = "INSERT INTO NUTRITION (NAME, CALORIESPR100,PROTEINPR100) VALUES (?, ?, ?);";
            stmt = conn.prepareStatement(sql);


            String name = ui.getInput("Skriv maden sum du vil tilføje " );
            double calories = Double.parseDouble(ui.getInput("Skriv calories per 100g, tjak"));
            double protein = Double.parseDouble(ui.getInput("Skriv protein per 100g"));
            stmt.setString(1, name);
            stmt.setDouble(2, calories);
            stmt.setDouble(3,protein);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                ui.displayMessage("maden er tilføjet.");
            } else {
                ui.displayMessage("Mislykkes at gemme maden");
            }

            stmt.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void saveUserData(String newName, String newPassword) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            String sql = "INSERT INTO USER (name, password) VALUES ( ?, ?)";
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, newName);
            stmt.setString(2, newPassword);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                ui.displayMessage("Brugeren er gemt");
            } else {
                ui.displayMessage("Mislykkedes at gemme brugeren");
            }

            stmt.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeUserData(String newName, String newPassword) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            String sql = "DELETE FROM USER WHERE name = ? AND password = ?";
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, newName);
            stmt.setString(2, newPassword);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                ui.displayMessage("Brugeren er fjernet");
            } else {
                ui.displayMessage("Mislykkedes at fjerne brugeren");
            }

            stmt.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void displayWatchedList() {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            //STEP 1: Register JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            //STEP 2: Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //STEP 3: Execute a query
            String sql = "SELECT watched_content.movieID, movie.name FROM watched_content JOIN movie ON watched_content.movieID = movie.movieID WHERE watched_content.userID = ?";
            stmt = conn.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            //STEP 4: Extract data from result set
            while (rs.next()) {


                String genre = rs.getString("Genre");
                String name = rs.getString("Name");
                int year = rs.getInt("Year");
                double rating = rs.getDouble("Rating");

                String formatString = "Name: %-45sGenre: %-35sRelease Date: %-12sRating:%-5.2f";

                String formattedOutput = String.format(formatString, name, genre, year, rating);
                ui.displayMessage(formattedOutput);

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
            }//end finally try
        }//end try
    }

    public void displayMyList() {

    }
}
