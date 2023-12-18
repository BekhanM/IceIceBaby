package model;

import util.TextUI;

import javax.swing.*;

public class PremadeTrainingProgram {
    private TextUI ui = new TextUI();

    public void displayTrainingMaintenance(String weightWishFromDatabase) {
        JTextArea textArea = new JTextArea(20, 50);
        textArea.setEditable(false);

        switch (weightWishFromDatabase) {
            case "leve" -> textArea.setText("""
                    Welcome to the 7-day Maintenance Program!

                    Day 1: Full Body:
                    Exercises:
                        - Squats: 3 sets x 8-10 reps
                        - Bench Press: 3 sets x 8-10 reps
                        - Bent Over Rows: 3 sets x 8-10 reps
                        - Overhead Press: 3 sets x 8-10 reps
                        - Planks: 3 sets, hold for 45 seconds

                    Day 2: Cardio:
                    Cardio: 30 minutes of moderate-intensity cardio

                    Day 3: Upper Body:
                    Exercises:
                        - Pull-Ups: 3 sets x 8-10 reps
                        - Dumbbell Rows: 3 sets x 8-10 reps
                        - Push-Ups: 3 sets x 8-10 reps
                        - Tricep Dips: 3 sets x 8-10 reps
                        - Bicep Curls: 3 sets x 8-10 reps

                    Day 4: Rest Day

                    Day 5: Legs and Cardio:
                    Exercises:
                        - Lunges: 3 sets x 10-12 reps per leg
                        - Leg Press: 3 sets x 10-12 reps
                        - Leg Curls: 3 sets x 10-12 reps
                        - Calf Raises: 3 sets x 12-15 reps
                    Cardio: 30 minutes of moderate-intensity cardio

                    Day 6: Cardio and Core:
                    Cardio: 40 minutes of steady-state cardio
                    Core:
                        - Bicycle Crunches: 3 sets x 15-20 reps
                        - Plank with Leg Lifts: 3 sets x 12-15 reps (each leg)

                    Day 7: Full Body:
                    Exercises:
                        - Deadlifts: 3 sets x 8-10 reps
                        - Incline Dumbbell Press: 3 sets x 8-10 reps
                        - Lat Pulldowns: 3 sets x 8-10 reps
                        - Lateral Raises: 3 sets x 8-10 reps
                        - Planks: 3 sets, hold for 45 seconds
                    """);
            case "cutte" -> textArea.setText("""
                    Welcome to the 7-day Cutting Program!

                    Day 1: Full Body:
                    Exercises:
                        - Squats: 4 sets x 8-10 reps
                        - Bench Press: 4 sets x 8-10 reps
                        - Bent Over Rows: 3 sets x 10-12 reps
                        - Overhead Press: 3 sets x 10-12 reps
                        - Planks: 3 sets, hold for 60 seconds

                    Day 2: Cardio and Abs:
                    Cardio: 30 minutes of moderate-intensity cardio
                    Abs:
                        - Russian Twists: 3 sets x 15-20 reps (7-10 each side)
                        - Leg Raises: 3 sets x 12-15 reps

                    Day 3: Upper Body:
                    Exercises:
                        - Pull-Ups: 4 sets x 8-10 reps
                        - Dumbbell Rows: 4 sets x 8-10 reps
                        - Push-Ups: 3 sets x 10-12 reps
                        - Tricep Dips: 3 sets x 10-12 reps
                        - Bicep Curls: 3 sets x 12-15 reps

                    Day 4: Rest Day

                    Day 5: Legs and Cardio:
                    Exercises:
                        - Lunges: 4 sets x 10-12 reps per leg
                        - Leg Press: 4 sets x 10-12 reps
                        - Leg Curls: 3 sets x 12-15 reps
                        - Calf Raises: 3 sets x 15-20 reps
                    Cardio: 30 minutes of high-intensity interval training (HIIT)

                    Day 6: Cardio and Core:
                    Cardio: 40 minutes of steady-state cardio
                    Core:
                        - Bicycle Crunches: 3 sets x 15-20 reps
                        - Plank with Leg Lifts: 3 sets x 12-15 reps (each leg)

                    Day 7: Full Body:
                    Exercises:
                        - Deadlifts: 4 sets x 8-10 reps
                        - Incline Dumbbell Press: 4 sets x 8-10 reps
                        - Lat Pulldowns: 3 sets x 10-12 reps
                        - Lateral Raises: 3 sets x 10-12 reps
                        - Burpees: 3 sets x 15 reps
                    """);
            case "bulke" -> textArea.setText("""
                    Welcome to the 7-day Bulking Program!

                    Day 1: Chest and Triceps:
                    Exercises:
                        - Bench Press: 4 sets x 6-8 reps
                        - Incline Dumbbell Press: 4 sets x 8-10 reps
                        - Chest Flyes: 3 sets x 10-12 reps
                        - Tricep Dips: 3 sets x 10-12 reps
                        - Tricep Pushdowns: 3 sets x 12-15 reps

                    Day 2: Back and Biceps:
                    Exercises:
                        - Deadlifts: 4 sets x 6-8 reps
                        - Bent Over Rows: 4 sets x 8-10 reps
                        - Lat Pulldowns: 3 sets x 10-12 reps
                        - Barbell Curls: 3 sets x 10-12 reps
                        - Hammer Curls: 3 sets x 12-15 reps

                    Day 3: Legs:
                    Exercises:
                        - Squats: 4 sets x 6-8 reps
                        - Leg Press: 4 sets x 8-10 reps
                        - Lunges: 3 sets x 10-12 reps
                        - Leg Extensions: 3 sets x 12-15 reps
                        - Hamstring Curls: 3 sets x 12-15 reps
                        - Calf Raises: 3 sets x 15-20 reps

                    Day 4: Rest Day

                    Day 5: Shoulders and Abs:
                    Exercises:
                        - Overhead Press: 4 sets x 8-10 reps
                        - Lateral Raises: 3 sets x 10-12 reps
                        - Front Raises: 3 sets x 12-15 reps
                        - Shrugs: 4 sets x 10-12 reps
                        - Planks: 3 sets, hold for 60 seconds
                        - Russian Twists: 3 sets x 20 reps (10 each side)

                    Day 6: Chest and Triceps:
                    Exercises:
                        - Dumbbell Bench Press: 4 sets x 8-10 reps
                        - Decline Bench Press: 4 sets x 8-10 reps
                        - Cable Crossovers: 3 sets x 10-12 reps
                        - Close Grip Bench Press: 3 sets x 10-12 reps
                        - Skull Crushers: 3 sets x 12-15 reps

                    Day 7: Back and Biceps:
                    Exercises:
                        - Pull-Ups: 4 sets x 8-10 reps
                        - Seated Cable Rows: 4 sets x 8-10 reps
                        - Face Pulls: 3 sets x 10-12 reps
                        - Preacher Curls: 3 sets x 10-12 reps
                        - Concentration Curls: 3 sets x 12-15 reps
                    """);
        }

        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.setCaretPosition(0);

        JOptionPane.showMessageDialog(null, scrollPane, "Training Program", JOptionPane.PLAIN_MESSAGE);
    }
}
