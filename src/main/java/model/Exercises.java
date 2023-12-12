package model;

public class Exercises {
    private String name;
    private int reps;
    private int sets;

    public Exercises(String name, int reps, int sets) {
        this.name = name;
        this.reps = reps;
        this.sets = sets;
    }

    public String getName() {
        return name;
    }
    public int getReps() {
        return reps;
    }
    public int getSets() {
        return sets;
    }

    @Override
    public String toString() {
        return "Name: " + name + "\nReps: " + reps + "\nSets: " + sets;
    }
}
