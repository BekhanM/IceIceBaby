package model;

public class Exercises {
    private String name;
    private double duration;
    private int reps;
    private int sets;

    public Exercises(String name, double duration, int reps, int sets) {
        this.name = name;
        this.duration = duration;
        this.reps = reps;
        this.sets = sets;
    }

    public String getName() {
        return name;
    }
    public double getDuration() {
        return duration;
    }
    public int getReps() {
        return reps;
    }
    public int getSets() {
        return sets;
    }

    @Override
    public String toString() {
        return "Name: " + name + "\nDuration: " + duration + "\nReps: " + reps + "\nSets: " + sets;
    }
}
