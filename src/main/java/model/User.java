package model;

public class User {

    private String username;
    private String password;
    private double height;
    private double weight;
    private int age;
    private String gender;

    public User(String username, String password, double height, double weight, int age, String gender) {
        this.username = username;
        this.password = password;
        this.height = height;
        this.weight = weight;
        this.age = age;
        this.gender = gender;
    }
}
