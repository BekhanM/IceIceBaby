package model;

public class User {

    private String username;
    private String password;
    private double height;
    private double weight;
    private int age;
    private String gender;
    private BMI bmi;

    public User(String username, String password, double height, double weight, int age, String gender,BMI bmi) {
        this.username = username;
        this.password = password;
        this.height = height;
        this.weight = weight;
        this.age = age;
        this.gender = gender;
        this.bmi = bmi;
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
    public int getAge() {
        return age;
    }
    public String getGender() {
        return gender;
    }

    public BMI getBMI(){
        return bmi;
    }

    @Override
    public String toString() {
        return "Username: " + username + "\nPassword: " + password + "\nHeight: " + height + "\nWeight: " + weight + "\nAge: " + age + "\nGender: " + gender;
    }




}
