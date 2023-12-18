package model;

public class User {

    int userID;
    private final String username;
    private final String password;
    private final double height;
    private final double weight;
    private final int age;
    private final String gender;
    private final BMI bmi;


    public User(int userID, String username, String password, double height, double weight, int age, String gender, BMI bmi) {
        this.username = username;
        this.password = password;
        this.height = height;
        this.weight = weight;
        this.age = age;
        this.gender = gender;
        this.bmi = bmi;
        this.userID = userID;
    }

    public int getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public double getHeight() {
        return height;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "Username: " + username + "\nPassword: " + password + "\nHeight: " + height + "\nWeight: " + weight + "\nAge: " + age + "\nGender: " + gender + "\nBMI: " + bmi;
    }


}
