package Simulator.components.student;

import Simulator.components.classroom.Classroom;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Student extends Label {
    private int studentID;
    private String colorState;
    private Boolean needHelp;
    private String ability;
    private int currentTask = 1;
    private int attempts = 0;
    private Label queLabel;
    private Label waitTime;
    private int seconds = -1;
    private int minutes = 0;
    private int hours = 0;
    private Timer timer;
    private boolean isHelping = false;

    public Student(int studentID, String ability) {
        waitTime = new Label("Working");
        setStudentID(studentID);
        Label queLabel = new Label(Integer.toString(studentID));
        queLabel.setStyle("-fx-padding: 10px; -fx-border-color: black; -fx-border-width: 2px; -fx-background-color: white;");
        queLabel.setFont(new Font(24));
        setQueLabel(queLabel);
        setAbility(ability);
        setColorState("grey");
        setNeedHelp(false);

        this.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        this.setPadding(new Insets(5));
        this.setAlignment(Pos.CENTER);
        this.setFont(new Font(24));
        GridPane.setHgrow(this, Priority.ALWAYS);
        GridPane.setVgrow(this, Priority.ALWAYS);

        updateLabelText();
    }

    public void updateLabelText() {
        this.setText("StudentID: " + studentID + "\nAbility: " + ability + "\nTask: " + currentTask + "\nAttempt: " + attempts);
    }

    public static void startWork(Student student) {
        int attemptTime = 1000;

        switch (student.getAbility()) {
            case "low":
                attemptTime = 15000;
                break;
            case "medium":
                attemptTime = 14000;
                break;
            case "high":
                attemptTime = 13000;
                break;
        }
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if(Classroom.isEnableFramework() && !student.isHelping()) {
                        giveTask(student);
                    } else if(!student.needHelp) {
                        giveTask(student);
                    }
                });
            }
        }, 0, attemptTime);
    }

    private static void giveTask(Student student) {
        int solution = student.getSolution();
        student.incrementAttemps();
        boolean foundSolution;
        student.updateLabelText();
        if(solution == 1) {
            student.setColorState("green");
            student.setNeedHelp(false);
            foundSolution = true;
        } else if (solution == 2 && !student.getAbility().equals("low")) {
            student.setColorState("green");
            student.setNeedHelp(false);
            foundSolution = true;
        } else if (solution == 3 && student.getAbility().equals("high")) {
            student.setColorState("green");
            student.setNeedHelp(false);
            foundSolution = true;
        } else {
            if(student.getAttempts() > 3) {
                student.setColorState("red");
                student.setNeedHelp(true);
                Student.putInQue(student);
            } else {
                student.setNeedHelp(false);
                student.setColorState("lightblue");
            }
            foundSolution = false;
        }

        if(foundSolution) {
            Student.removeFromQue(student);
            student.setAttempts(0);
            student.incrementCurrentTask();
        }
    }

    public static void putInQue(Student student) {
        if(!Classroom.getHelpQue().contains(student.getQueLabel())) {
            Classroom.getHelpQue().offer(student.getQueLabel());
        }
        Classroom.getTheClassroom().updateQueView();
    }

    public static void removeFromQue(Student student) {
        Classroom.getHelpQue().remove(student.getQueLabel());
        Classroom.getTheClassroom().updateQueView();
    }

    public void incrementAttemps() {
            this.attempts++;
    }

    public void incrementCurrentTask() {
        this.currentTask++;
    }

    public int getSolution() {
        return new Random().nextInt(9) + 1;
    }

    public String getColorState() {
        return colorState;
    }

    public void setColorState(String color) {
        switch (color) {
            case "green":
                this.colorState = color;
                this.setStyle("-fx-background-color: #66ff66;");
                break;
            case "red":
                this.colorState = color;
                this.setStyle("-fx-background-color: #ff9999;");
                break;
            case "lightblue":
                this.colorState = color;
                this.setStyle("-fx-background-color: #e6ffff;");
                break;
            case "grey":
                this.colorState = color;
                this.setStyle("-fx-background-color: #A9A9A9;");
                break;
            case "yellow":
                this.colorState = color;
                this.setStyle("-fx-background-color: #FFFF99;");
                break;
        }
    }

    public Boolean getNeedHelp() {
        return needHelp;
    }

    public void setNeedHelp(Boolean needHelp) {
        this.needHelp = needHelp;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public String getAbility() {
        return ability;
    }

    public void setAbility(String ability) {
        this.ability = ability;
    }

    public int getCurrentTask() {
        return currentTask;
    }

    public void setCurrentTask(int currentTask) {
        this.currentTask = currentTask;
    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public Label getQueLabel() {
        return queLabel;
    }

    public void setQueLabel(Label queLabel) {
        this.queLabel = queLabel;
    }

    public void setWaitTime(Label waitTime) {
        this.waitTime = waitTime;
    }

    public Label getWaitTime() {
        return this.waitTime;
    }

    public boolean isHelping() {
        return isHelping;
    }

    public void setHelping(boolean helping) {
        isHelping = helping;
    }
}
