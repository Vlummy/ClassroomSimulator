package Simulator.components.classroom;

import Simulator.components.CustomControls.SwitchButton;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import Simulator.components.student.Student;
import Simulator.components.teacher.Teacher;
import java.util.*;

public class Classroom extends BorderPane {
    private Button startButton;
    private Button stopButton;
    private SwitchButton enableFrameworkSwitch;
    private VBox rightDataPanel;
    private VBox leftDataPanel;
    private HBox topPanel;
    private HBox bottomPanel;
    private GridPane classroom;
    private ObservableList<Student> students;
    private Teacher teacher;
    private Student previousHelpedStudent;
    private Label queLabel = new Label();

    // Static properties for classroom framework
    private static boolean ENABLE_FRAMEWORK = false;
    private static Queue<Label> helpQue; // This will be instantiated when a classroom object is created.
    private static Classroom theClassroom;

    public Classroom() {
        // create classroom
        setClassroom(new GridPane());
        setStudents(FXCollections.observableArrayList());
        setTeacher(new Teacher());
        addStudents();

        // init helpQue
        setHelpQue(new ArrayDeque());

        // Build control and data panes

        setStartButton(new Button("Start Simulation"));
        setStopButton(new Button("Stop Simulation"));
        setEnableFrameworkSwitch(new SwitchButton());

        setLeftDataPanel(new VBox());
        setRightDataPanel(new VBox());
        setTopPanel(new HBox());
        setBottomPanel(new HBox());

        getStartButton().setOnAction(event -> startSimulation());
        getStopButton().setOnAction(event -> stopSimulation());

        addNodeToPanel("T", getStartButton());
        addNodeToPanel("T", getStopButton());
        addNodeToPanel("L", new Label("Enable Framework"));
        addNodeToPanel("L", getEnableFrameworkSwitch());
        getBottomPanel().getChildren().addAll(Classroom.getHelpQue());

        this.setTop(getTopPanel());
        this.setLeft(getLeftDataPanel());
        this.setBottom(getBottomPanel());
        this.setRight(getRightDataPanel());

        distributeStudents();

        Classroom.setTheClassroom(this);
    }

    public void updateQueView() {
        getBottomPanel().getChildren().clear();
        getHelpQue().forEach(student -> {
            getBottomPanel().getChildren().add(student);
        });
    }

    public static Queue<Label> getHelpQue() {
        return helpQue;
    }

    public static void setHelpQue(Queue helpQue) {
        Classroom.helpQue = helpQue;
    }

    public static boolean isEnableFramework() {
        return ENABLE_FRAMEWORK;
    }

    public static void setEnableFramework(boolean enableFramework) {
        ENABLE_FRAMEWORK = enableFramework;
    }

    public static Classroom getTheClassroom() {
        return theClassroom;
    }

    public static void setTheClassroom(Classroom theClassroom) {
        Classroom.theClassroom = theClassroom;
    }

    public void distributeStudents() {
        int rowIndex = 0;
        int colIndex = 0;
        for (Student student : getStudents()) {

            getClassroom().add(student, colIndex, rowIndex);

            if (colIndex == 4) {
                colIndex = -1;
                rowIndex++;
            }

            colIndex++;
        }
    }

    public void startSimulation() {
        System.out.println("Simulations Started.");

        for(Student student : students) {
            Student.startWork(student);
        }

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    Classroom.getTheClassroom().updateQueView();
                    if(previousHelpedStudent != null) {
                        previousHelpedStudent.setHelping(false);
                        previousHelpedStudent.setNeedHelp(false);
                        previousHelpedStudent.setColorState("lightblue");
                        previousHelpedStudent.setAttempts(0);
                        previousHelpedStudent.incrementCurrentTask();
                        previousHelpedStudent.updateLabelText();
                        queLabel.setStyle("-fx-padding: 10px; -fx-border-color: black; -fx-border-width: 2px; -fx-background-color: white");
                        Classroom.getHelpQue().remove(queLabel);
                    }
                    if(Classroom.isEnableFramework()) {
                        queLabel = Classroom.getHelpQue().poll();
                    } else {
                        for(Student student1 : students) {
                            if(student1.getNeedHelp()) {
                                queLabel = student1.getQueLabel();
                                break;
                            }
                        }
                    }
                    if(queLabel != null && !queLabel.getText().equals("")) {
                        int studentID = Integer.parseInt(queLabel.getText());
                        for (Student student : students) {
                            if (student.getStudentID() == studentID) {
                                student.setHelping(true);
                                student.setColorState("yellow");
                                queLabel.setStyle("-fx-padding: 10px; -fx-border-color: black; -fx-border-width: 2px; -fx-background-color: #FFFF99;");
                                previousHelpedStudent = student;
                            }
                        }
                    }
                });
            }
        }, 0, 10000);
    }

    public void stopSimulation() {
        System.out.println("Simulation Stopped.");
        BorderPane borderPane = (BorderPane) this.getParent();
        borderPane.setCenter(new Classroom());
    }

    public void addNodeToPanel(String panelPosition, Node node) {
        switch (panelPosition) {
            case "L":
                this.leftDataPanel.getChildren().add(node);
                break;
            case "R":
                this.rightDataPanel.getChildren().add(node);
                break;
            case "B":
                this.bottomPanel.getChildren().add(node);
                break;
            case "T":
                this.topPanel.getChildren().add(node);
                break;
        }
    }

    public void addStudents() {
        String[] abilityDistributor = {"low", "medium", "high"};
        Random random = new Random();
        for(int i = 1; i <= 25; i++) {
            Student student = new Student(i, abilityDistributor[random.nextInt(3)]);
            this.students.add(student);
        }
    }

    public Button getStartButton() {
        return startButton;
    }

    public void setStartButton(Button startButton) {
        this.startButton = startButton;
    }

    public Button getStopButton() {
        return stopButton;
    }

    public void setStopButton(Button stopButton) {
        this.stopButton = stopButton;
    }

    public VBox getRightDataPanel() {
        return rightDataPanel;
    }

    public void setRightDataPanel(VBox rightDataPanel) {
        this.rightDataPanel = rightDataPanel;
    }

    public VBox getLeftDataPanel() {
        return leftDataPanel;
    }

    public void setLeftDataPanel(VBox leftDataPanel) {
        this.leftDataPanel = leftDataPanel;
    }

    public HBox getTopPanel() {
        return topPanel;
    }

    public void setTopPanel(HBox topPanel) {
        this.topPanel = topPanel;
    }

    public HBox getBottomPanel() {
        return bottomPanel;
    }

    public void setBottomPanel(HBox bottomPanel) {
        bottomPanel.setSpacing(5);
        bottomPanel.setPadding(new Insets(5));
        this.bottomPanel = bottomPanel;
    }

    public GridPane getClassroom() {
        return classroom;
    }

    public void setClassroom(GridPane classroom) {
        classroom.setGridLinesVisible(true);
        classroom.setHgap(5);
        classroom.setVgap(5);
        classroom.setPadding(new Insets(5));

        this.classroom = classroom;
        this.setCenter(classroom);
    }

    public ObservableList<Student> getStudents() {
        return students;
    }

    public void setStudents(ObservableList<Student> students) {
        this.students = students;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public SwitchButton getEnableFrameworkSwitch() {
        return enableFrameworkSwitch;
    }

    public void setEnableFrameworkSwitch(SwitchButton enableFrameworkSwitch) {
        this.enableFrameworkSwitch = enableFrameworkSwitch;
    }
}
