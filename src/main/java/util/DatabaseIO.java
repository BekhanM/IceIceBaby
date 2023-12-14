package util;

import model.BMI;
import model.User;

import java.sql.*;

public class DatabaseIO {

    static final String DB_URL = "jdbc:mysql://localhost/broscience";
    //  Database credentials
    static final String USER = "root";
    static final String PASS = "Heisenberg2001!";
    String username;
    String puffPass;
    double height;
    double weight;
    int age;
    String gender;


    User user;
    TextUI ui = new TextUI();
    Datavalidator dv = new Datavalidator();
    int userChoice = 0;
    String mediaContentName;


    public void addUser() {
        BMI bmi = new BMI();
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            String username = ui.getInput("indtast din brugernavn smukke");

            // Check if the username already exists in the database
            if (!checkUsername(username)) {
                String password = ui.getInput("indtast din kodeord");
                if (dv.validatePassword(password) == true) {
                    double height = Double.parseDouble(ui.getInput("Indtast din højde"));
                    double weight = Double.parseDouble(ui.getInput("Indtast din vægt"));
                    int age = Integer.parseInt(ui.getInput("Indtast din alder flinke"));
                    String gender = ui.getInput("Er du mand,kone eller noget andet?");
                    double userBMI = bmi.bmiCalculator(height, weight, age);

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
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
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
        BMI bmi = new BMI();
        Connection conn = null;
        PreparedStatement stmt = null;

        this.username = username;
        this.puffPass = puffPass;

        user = null;
        try {
            //STEP 1: Register JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            //STEP 2: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            String sql = "SELECT username, password, height, weight FROM USER WHERE username = ? AND password = ? ";
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, username);
            stmt.setString(2, puffPass);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                double retrievedHeight = rs.getDouble("height");
                double retrievedWeight = rs.getDouble("weight");

                user = new User(username, puffPass, retrievedHeight, retrievedWeight, age, gender, bmi);

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
                double proteinPr100 = rs.getDouble("proteinPr100");

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
                Integer foodID = rs.getInt("foodID");
                String name = rs.getString("Name");
                double caloriesPr100 = rs.getDouble("CaloriesPr100");
                double proteinPr100 = rs.getDouble("proteinpr100");

                String searchForFood = "\nFoodID: " + foodID + "\nName: " + name + "\nCalories (100 g.): " + caloriesPr100 + " Cal.\nProtein (100 g.): " + proteinPr100 + " g.";

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


            String name = ui.getInput("Skriv maden sum du vil tilføje ");
            double calories = Double.parseDouble(ui.getInput("Skriv calories per 100g, tjak"));
            double protein = Double.parseDouble(ui.getInput("Skriv protein per 100g"));
            stmt.setString(1, name);
            stmt.setDouble(2, calories);
            stmt.setDouble(3, protein);

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

    public void updateBmiDatabase(double newBMI, double newWeight, String username) {
        Connection conn = null;
        PreparedStatement stmt = null;

        this.username = username;


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // Update query with parameters
            String sql = "UPDATE `broscience`.`user` SET `bmi` = ?, `weight` = ?  WHERE `username` = ?";
            stmt = conn.prepareStatement(sql);

            // Set the parameters
            stmt.setDouble(1, newBMI);
            stmt.setDouble(2, newWeight);
            stmt.setString(3, username);

            // Execute the update
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                ui.displayMessage("BMI updated successfully!");
            } else {
                ui.displayMessage("Failed to update BMI");
            }

            stmt.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addFoodIntake(User user) {
        Connection conn = null;
        PreparedStatement insertStmt = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // Allow user to search for and select food
            int selectedFoodID = searchAndSelectFood(conn);

            // Insert the selected food into nutritionintake table
            String insertSql = "INSERT INTO nutritionintake (userID, foodID) VALUES (?, ?)";
            insertStmt = conn.prepareStatement(insertSql);

            insertStmt.setInt(1, getUserIDFromDatabase(username,puffPass));
            insertStmt.setInt(2, selectedFoodID);

            int rowsInserted = insertStmt.executeUpdate();

            if (rowsInserted > 0) {
                ui.displayMessage("Food intake added successfully.");

                // Update user's total calories and protein intake
                updateTotalIntake(conn, user);
            } else {
                ui.displayMessage("Failed to add food intake.");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (insertStmt != null) {
                    insertStmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public int getUserIDFromDatabase(String username, String password) {
        int userID = -1;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

            String sql = "SELECT userID FROM user WHERE username = ? AND password = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, username);
                stmt.setString(2, password);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        userID = rs.getInt("userID");
                    }
                }
            }

            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return userID;
    }

    public void updateTotalIntake(Connection conn, User user) throws SQLException {
        String updateSql = "UPDATE user SET totalCalories = (SELECT SUM(caloriesPr100) FROM nutritionintake "
                + "JOIN nutrition ON nutritionintake.foodID = nutrition.foodID "
                + "WHERE nutritionintake.userID = ?), "
                + "totalProtein = (SELECT SUM(proteinPr100) FROM nutritionintake "
                + "JOIN nutrition ON nutritionintake.foodID = nutrition.foodID "
                + "WHERE nutritionintake.userID = ?) "
                + "WHERE userID = ?";

        try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
            updateStmt.setInt(1, getUserIDFromDatabase(username,puffPass));
            updateStmt.setInt(2, getUserIDFromDatabase(username,puffPass));
            updateStmt.setInt(3, getUserIDFromDatabase(username,puffPass));

            int rowsUpdated = updateStmt.executeUpdate();

            if (rowsUpdated > 0) {
                ui.displayMessage("User's total intake updated successfully.");
            } else {
                ui.displayMessage("Failed to update user's total intake.");
            }
        }
    }


    private int searchAndSelectFood(Connection conn) throws SQLException {
        // Display available foods
        searchFood();

        // Allow user to select a food
        int selectedFoodID = -1;
        boolean validSelection = false;
        do {
            String selectedFoodIDString = ui.getInput("Enter the ID of the food you want to add to your intake:");
            try {
                selectedFoodID = Integer.parseInt(selectedFoodIDString);
                validSelection = true;
            } catch (NumberFormatException e) {
                ui.displayMessage("Invalid input. Please enter a valid food ID.");
            }
        } while (!validSelection);

        // Validate that the selected food ID exists in the database
        if (!validateFoodID(conn, selectedFoodID)) {
            ui.displayMessage("Invalid food ID. Please select a valid food.");
            // Recursively call the method to allow the user to select again
            selectedFoodID = searchAndSelectFood(conn);
        }

        return selectedFoodID;
    }

    private boolean validateFoodID(Connection conn, int foodID) throws SQLException {
        String query = "SELECT COUNT(*) FROM nutrition WHERE foodID = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, foodID);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        }
        return false;
    }


}
