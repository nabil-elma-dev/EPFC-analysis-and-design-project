package counter;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Random;

public class CounterApp extends Application {
    public static final int ORIGIN = -3;
    public static final int BOUND = 4;

    // Containers
    private final GridPane myCounter = new GridPane();
    private final HBox secondRow = new HBox();

    // Tools
    private final TextField txtMyCounter = new TextField("My Counter");
    private final Button plus = new Button("+");
    private final Button minus = new Button("-");
    private final Label value = new Label("3");
    private final Label errorMessage = new Label("Trimmed length must be >= 3");

    // Start and Launch
    @Override
    public void start(Stage stage) {
        myCounterBuilder();
        eventsManager();

        Scene scene = new Scene(myCounter);
        stage.setTitle("Counter");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    // Containers' builders
    private void myCounterBuilder() {
        myCounter.setPrefSize(400, 200);
        setTxtMyCounter();
        myCounter.add(txtMyCounter, 0, 0);
        setErrorMessage();
        myCounter.add(errorMessage, 0, 1);
        secondRowBuilder();
        myCounter.add(secondRow, 0, 2);
        myCounter.setAlignment(Pos.CENTER);
    }

    private void secondRowBuilder() {
        buttonsSetUp(plus, minus);
        setValue();
        secondRow.getChildren().addAll(minus, value, plus);
        secondRow.setSpacing(20);
        secondRow.setAlignment(Pos.CENTER);
        secondRow.setPadding(new Insets(20,0,0,0));
    }

    // Tools' customization
    private void setTxtMyCounter() {
        txtMyCounter.setPrefWidth(250);
    }
    private void buttonsSetUp(Button...buttons) {
        for (Button b : buttons) {
            b.setPrefWidth(50);
        }
    }

    private void setValue() {
        Random randomValue = new Random();
        value.setText(String.valueOf(randomValue.nextInt(ORIGIN, BOUND)));
        value.setTextFill(Color.BLUE);
    }

    private void setErrorMessage() {
        errorMessage.setTextFill(Color.RED);
    }

    private void refresh() {
        minus.setDisable(Integer.parseInt(value.getText()) == -3);
        plus.setDisable(Integer.parseInt(value.getText()) == 3);
        errorMessage.setVisible(txtMyCounter.getText().length() < 3);
    }


    // Handling events
    private void eventsManager() {
        refresh();
        // Click on "+"
        plus.setOnAction((ActionEvent increment) -> {
            Integer newVal = Integer.parseInt(value.getText()) + 1;
            value.setText(String.valueOf(newVal));

            minus.setDisable(false);
            plus.setDisable(newVal == 3);
        } );

        // Click on "-"
        minus.setOnAction((ActionEvent decrement) -> {
            Integer newVal = Integer.parseInt(value.getText()) - 1;
            value.setText(String.valueOf(newVal));
            plus.setDisable(false);
            minus.setDisable(newVal == -3);
        });

        // Type on txtField
        txtMyCounter.setOnKeyTyped((KeyEvent type) -> {
            errorMessage.setVisible(txtMyCounter.getText().trim().length() < 3);
        });
    }
}