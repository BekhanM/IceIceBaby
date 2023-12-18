package util;

import model.BMI;
import model.TrainingProgram;
import model.User;

import java.sql.*;

public class DatabaseIO {

    static final String DB_URL = "jdbc:mysql://localhost/broscience";
    //  Database credentials
    static final String USER = "root";
    static final String PASS = "Heisenberg2001!";
    String username;
    String puffPass;
    int age;
    String gender;
    User user;
    TextUI ui = new TextUI();
    Datavalidator dv = new Datavalidator();
    TrainingProgram tp = new TrainingProgram();


    public void addUser() {
        BMI bmi = new BMI();
        Connection conn;
        PreparedStatement stmt;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            String username;
            do {
                username = ui.getInput("indtast din brugernavn smukke");
                if (checkUsername(username)) {
                    ui.displayMessage("Brugernavn eksisterer allerede, prøv igen");
                }
            } while (checkUsername(username));

            String password;
            do {
                password = ui.getInput("Indtast dit kodeord søde;)");
            } while (!dv.validatePassword(password));

            double height = Double.parseDouble(ui.getInput("Indtast din højde i meter. Eks: 1.85"));
            double weight = Double.parseDouble(ui.getInput("Indtast din vægt i kg. Eks: 75.3"));
            int age = Integer.parseInt(ui.getInput("Indtast din alder flinke"));
            String gender = ui.getInput(
                    """
                            Køn:
                            1) Mand.
                            2) Kvinde.""");

            if (gender.equals("1")) {
                gender = "mand".toLowerCase();
            } else {
                gender = "kvinde".toLowerCase();
            }

            double userBMI = bmi.bmiCalculator(height, weight);

            String weightWish = ui.getInput("Vil du bulke, cutte eller bare leve normalt, bro?" + """
                                    
                    1) Bulke.
                    2) Cutte.
                    3) Leve.
                    """);
            if (weightWish.equals("1")) {
                weightWish = "Bulke".toLowerCase();
            } else if (weightWish.equals("2")) {
                weightWish = "Cutte".toLowerCase();
            } else if (weightWish.equals("3")) {
                weightWish = "Leve".toLowerCase();
            }

            String sql = "INSERT INTO USER (username, password,height,weight,age,gender,bmi, weightWish) VALUES (?, ? , ? , ? , ? , ? , ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setDouble(3, height);
            stmt.setDouble(4, weight);
            stmt.setInt(5, age);
            stmt.setString(6, gender);
            stmt.setDouble(7, userBMI);
            stmt.setString(8, weightWish);


            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                ui.displayMessage("Bruger er gemt");
            } else {
                ui.displayMessage("Mislykkedes at gemme brugeren");
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
        Connection conn;
        PreparedStatement stmt;

        this.username = username;
        this.puffPass = puffPass;

        user = null;
        try {
            //STEP 1: Register JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            //STEP 2: Open a connection
            //System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            String sql = "SELECT userID,username, password, height, weight FROM USER WHERE username = ? AND password = ? ";
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, username);
            stmt.setString(2, puffPass);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                double retrievedHeight = rs.getDouble("height");
                double retrievedWeight = rs.getDouble("weight");
                int retrievedUserID = rs.getInt("userID");
                user = new User(retrievedUserID, username, puffPass, retrievedHeight, retrievedWeight, age, gender, bmi);

            } else {
                if (!checkUsername(username)) {
                    ui.displayMessage("Brugernavn eksisterer ikke, prøv igen dumbo");
                } else {
                    ui.displayMessage("Kodeord er forkert, prøv igen spade");
                }
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
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //STEP 3: Execute a query
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
              //  System.out.println(formattedOutput);

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
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //STEP 3: Execute a query
            String s = ui.getInput("Søg efter den øvelse du gerne vil se, bro.");
            String sql = "SELECT exerciseID, name, focusGroup FROM broscience.exercises WHERE INSTR(name, '" + s + "') > 0 ";
            stmt = conn.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery(sql);

            //STEP 4: Extract data from result set
            while (rs.next()) {
                String name = rs.getString("Name");
                String focusGroup = rs.getString("focusGroup");

                String exercise = "\nNavn: " + name + "\nFokusgruppe: " + focusGroup;

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
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //STEP 3: Execute a query
            String s = ui.getInput("Søg efter alt det lækre mad med gains, bro.");
            String sql = "SELECT foodID, name, caloriesPr100, proteinPr100 FROM broscience.nutrition WHERE INSTR(name, '" + s + "') > 0 ";
            stmt = conn.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery(sql);

            //STEP 4: Extract data from result set
            while (rs.next()) {
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
            }
        }
    }


    public void addFood() {
        Connection conn;
        PreparedStatement stmt;


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            String sql = "INSERT INTO NUTRITION (NAME, CALORIESPR100,PROTEINPR100) VALUES (?, ?, ?);";
            stmt = conn.prepareStatement(sql);


            String name = ui.getInput("Skriv maden som du vil tilføje ");
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
        Connection conn;
        PreparedStatement stmt;

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
        Connection conn;
        PreparedStatement stmt;

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
                ui.displayMessage("BMI er opdateret!");
            } else {
                ui.displayMessage("Kunne ikke opdatere BMI");
            }

            stmt.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
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

    public double getBMIFromDatabase(String username, String password) {
        double bmi = -1;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

            String sql = "SELECT bmi FROM user WHERE username = ? AND password = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, username);
                stmt.setString(2, password);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        bmi = rs.getDouble("bmi");
                    }
                }
            }


            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return bmi;
    }

    public String getWeighWishFromDatabase(String username, String password) {
        String weightWish = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

            String sql = "SELECT weightWish FROM user WHERE username = ? AND password = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, username);
                stmt.setString(2, password);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        weightWish = rs.getString("weightWish");
                    }
                }
            }

            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return weightWish;
    }




    public String getGenderFromDatabase(String username, String password) {
        String gender = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

            String sql = "SELECT gender FROM user WHERE username = ? AND password = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, username);
                stmt.setString(2, password);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        gender = rs.getString("gender");
                    }
                }
            }

            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return gender;
    }


    public void addFoodIntake(User user) {
        Connection conn = null;
        PreparedStatement insertStmt = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // Allow user to search for and select food
            int selectedFoodID = searchAndSelectFood(conn);

            // Ask the user for the amount of grams they want to eat
            double selectedGrams = -1;
            boolean validGrams = false;
            do {
                String selectedGramsString = ui.getInput("Indtast mængden af mad du har spist i gram:");
                try {
                    selectedGrams = Double.parseDouble(selectedGramsString);
                    validGrams = true;
                } catch (NumberFormatException e) {
                    ui.displayMessage("Forkert indtastning. Indtast et tal");
                }
            } while (!validGrams);

            // Retrieve the nutritional values from the database based on the selected foodID
            double caloriesPer100g = getCaloriesPer100g(conn, selectedFoodID);
            double proteinPer100g = getProteinPer100g(conn, selectedFoodID);

            // Calculate the nutritional values based on the selected grams
            double caloriesIntake = (selectedGrams / 100.0) * caloriesPer100g;
            double proteinIntake = (selectedGrams / 100.0) * proteinPer100g;

            ui.displayMessage("Næringsværdier for " + selectedGrams + " gram af det valgte produkt:");
            ui.displayMessage("Calories: " + caloriesIntake + " kcal");
            ui.displayMessage("Protein: " + proteinIntake + " grams");

            // Insert the selected food into nutritionintake table
            String insertSql = "INSERT INTO nutritionintake (userID, foodID, calories, protein, gram) VALUES (?, ?, ?, ?, ?)";
            insertStmt = conn.prepareStatement(insertSql);

            insertStmt.setInt(1, getUserIDFromDatabase(username, puffPass));
            insertStmt.setInt(2, selectedFoodID);
            insertStmt.setDouble(3, caloriesIntake);
            insertStmt.setDouble(4, proteinIntake);
            insertStmt.setDouble(5, selectedGrams);

            int rowsInserted = insertStmt.executeUpdate();

            if (rowsInserted > 0) {
                ui.displayMessage("Madindtag tilføjet.");

                // Update user's total calories and protein intake
            } else {
                ui.displayMessage("Kan ikke tilføje madindtag.");
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

    public String displayFoodIntake(User user) {
        Connection conn = null;
        PreparedStatement stmt = null;

        double totalCalories = 0;
        double totalProtein = 0;
        String result = null;
        try {
            //STEP 1: Register JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            //STEP 2: Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // Get the user's ID
            int userID = user.getUserID();

            //STEP 3: Execute a query
            String sql = "SELECT n.name, ni.gram, ni.calories, ni.protein " +
                    "FROM nutritionintake ni " +
                    "JOIN nutrition n ON ni.foodID = n.foodID " +
                    "WHERE ni.userID = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userID);

            ResultSet rs = stmt.executeQuery();

            totalCalories = 0.0;
            totalProtein = 0.0;

            //STEP 4: Extract data from result set
            while (rs.next()) {
                String foodName = rs.getString("name");
                double grams = rs.getDouble("gram");
                double calories = rs.getDouble("calories");
                double protein = rs.getDouble("protein");

                String formatString = "Food: %-35sGrams: %-12.2fCalories: %-12.2fProtein: %-5.2f";
                String formattedOutput = String.format(formatString, foodName, grams, calories, protein);
               // System.out.println("Mad indtaget so far: \n" + formattedOutput);

                totalCalories += calories;
                totalProtein += protein;
            }

            result = "\nKalorier indtaget: " + totalCalories + "\nProtein indtaget: " + totalProtein;


            //STEP 5: Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
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
        return result;

    }


    private int searchAndSelectFood(Connection conn) throws SQLException {
        // Allow user to select a food
        int selectedFoodID = -1;
        boolean validSelection = false;
        do {
            String selectedFoodIDString = ui.getInput("Indtast ID'et på maden du vil tilføje til dit indtag:");
            try {
                selectedFoodID = Integer.parseInt(selectedFoodIDString);
                validSelection = true;
            } catch (NumberFormatException e) {
                ui.displayMessage("Forkert indtastning. Indtast korrekt mad ID");
            }
        } while (!validSelection);

        // Validate that the selected food ID exists in the database
        if (!validateFoodID(conn, selectedFoodID)) {
            ui.displayMessage("Forkert madID. Indtast korrekt madID");
            // Recursively call the method to allow the user to select again
            selectedFoodID = searchAndSelectFood(conn);
        }

        return selectedFoodID;
    }

    // Helper method to retrieve calories per 100g from the database
    public double getCaloriesPer100g(Connection conn, int foodID) throws SQLException {
        double caloriesPer100g = 0.0;

        // Replace the following placeholder SQL query with your actual query
        String sql = "SELECT caloriesPr100 FROM nutrition WHERE foodID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, foodID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    caloriesPer100g = rs.getDouble("caloriesPr100");
                }
            }
        }

        return caloriesPer100g;
    }

    // Helper method to retrieve protein per 100g from the database
    public double getProteinPer100g(Connection conn, int foodID) throws SQLException {
        double proteinPer100g = 0.0;

        // Replace the following placeholder SQL query with your actual query
        String sql = "SELECT proteinPr100 FROM nutrition WHERE foodID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, foodID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    proteinPer100g = rs.getDouble("proteinPr100");
                }
            }
        }

        return proteinPer100g;
    }


    public boolean validateFoodID(Connection conn, int foodID) throws SQLException {
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


    //--------------------------------"Training program" ---------------------------------//
    public void addDay(User user) {
        Connection conn;
        PreparedStatement stmt;
        int user1 = user.getUserID();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            String sql = "INSERT INTO WORKOUT (day,userID) VALUES (?,?);";
            stmt = conn.prepareStatement(sql);

            //hygg í databaseio frá linju 116 - 126
            String day = ui.getInput("Indtast hvad dag du vil tilføje ");

            stmt.setString(1, day);
            stmt.setInt(2, getUserIDFromDatabase(username, puffPass));

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                ui.displayMessage("dagen er tilføjet.");
            } else {
                ui.displayMessage("Mislykkes at gemme dagen");
            }

            stmt.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void displayProgram() {

        String userID = String.valueOf(getUserIDFromDatabase(username, puffPass));
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            String sql = "SELECT  we.SETS, we.REPS, w.day, e.name " +
                    "FROM broscience.workoutexercise we " +
                    "JOIN broscience.workout w ON we.workoutID = w.workoutID " +
                    "JOIN broscience.exercises e ON we.EXERCISEID = e.EXERCISEID " +
                    "WHERE we.userID = ?";


            stmt = conn.prepareStatement(sql);
            stmt.setString(1, userID);


            ResultSet rs = stmt.executeQuery();

            //hví er tad tveir????
            while (rs.next()) {


                while (rs.next()) {


                    String sets1 = rs.getString("SETS");
                    String reps1 = rs.getString("REPS");
                    String day = rs.getString("day");
                    String exerciseName = rs.getString("name");

                    String formatString = "day: %-5s exercise: %-30s sets: %-5s reps: %s";

                    String formattedOutput = String.format(formatString, day, exerciseName, sets1, reps1);

                    System.out.println(formattedOutput);
                }


            }

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


    public void addDayToProgram() {
        Connection conn = null;
        PreparedStatement stmt = null;
        String userID = String.valueOf(getUserIDFromDatabase(username, puffPass));


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            displayDays();
            String workoutID = ui.getInput("Hvilken dag vil du indsætte øvelsen i? " + " tast som nummer");

            tp.displayExercises();
            String exerciseID = ui.getInput("Hvilken øvelse vil du tilføje til den dag? husk at taste sum nummer");

            String sets = ui.getInput("Hvor mange sets?");
            String reps = ui.getInput("Hvor mange reps?");

            String sql = "INSERT INTO WORKOUTEXERCISE (USERID, WORKOUTID, EXERCISEID, SETS, REPS) VALUES (?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, userID);
            stmt.setString(2, workoutID);
            stmt.setInt(3, Integer.parseInt(exerciseID));
            stmt.setInt(4, Integer.parseInt(sets));
            stmt.setInt(5, Integer.parseInt(reps));
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                ui.displayMessage("Det gik... denne gang");
            } else {
                ui.displayMessage("Mislykkedes");
            }

            stmt.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void displayDays() {
        String userID = String.valueOf(getUserIDFromDatabase(username, puffPass));
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            String sql = "SELECT workoutID, day FROM broscience.workout WHERE userID = (?) ";

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, userID);


            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                //Retrieve by column name

                String workoutID1 = rs.getString("workoutID");
                String day1 = rs.getString("day");

                String formatString = "workoutID: %-10sday: %-12.5s";
                String formattedOutput = String.format(formatString, workoutID1, day1);
                System.out.println(formattedOutput);


            }

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

    public void displayPremadeProgram() {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            //STEP 1: Register JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            //STEP 2: Open a connection
            //ui.displayMessage("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);


            String sql = "SELECT tp.trainingprogramID, tp.day, e.name , tp.sets, tp.reps " +
                    "FROM broscience.trainingprogram tp " +
                    "JOIN broscience.exercises e ON tp.exerciseID = e.exerciseID";


            stmt = conn.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            System.out.println("\n" + "----------------------------maintenance--------------------------" + "\n");
            while (rs.next()) {


                int trainingprogramID = rs.getInt("trainingprogramID");
                String day = rs.getString("day");
                String exerciseName = rs.getString("name"); // Corrected to fetch exercise name from 'exercises' table
                int sets = rs.getInt("sets");
                int reps = rs.getInt("reps");

                String formatString = "trainingprogramID: %-10s day: %-15s exerciseName: %-25s sets: %-5s reps: %-5s";
                String formattedOutput = String.format(formatString, trainingprogramID, day, exerciseName, sets, reps);
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

}
