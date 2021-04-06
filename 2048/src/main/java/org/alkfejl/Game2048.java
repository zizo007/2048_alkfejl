package org.alkfejl;

import java.util.Arrays;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class Game2048 extends Application {

    private final int SCENE_WIDTH = Board.BOARD_SIZE * 125;
    private final int SCENE_HEIGHT = Board.BOARD_SIZE * 125 + 100;

    @Override
    public void start(Stage stage) {

        stage.setTitle("Fejlesztett Alkamazás");
        Board board = new Board();
        var title = new Text("2048");
        title.getStyleClass().add("game-title");

        var score = new Text("Score\n" + board.getScore());
        score.getStyleClass().add("score");

        var titleBox = new HBox();
        titleBox.getChildren().addAll(title);


        var scoreRestartBox = new VBox();
        scoreRestartBox.setSpacing(10);
        scoreRestartBox.setAlignment(Pos.CENTER);
        var restartButton = new Button("New Game");
        restartButton.getStyleClass().add("restart-button");



        scoreRestartBox.getStyleClass().add("score-box");
        scoreRestartBox.getChildren().addAll(score, restartButton);



        var topElements = new BorderPane();
        topElements.setLeft(titleBox);
        BorderPane.setAlignment(titleBox, Pos.CENTER_LEFT);
        BorderPane.setMargin(titleBox, new Insets(20,0, 0, (int)(SCENE_WIDTH / 12)));
        topElements.setRight(scoreRestartBox);
        BorderPane.setAlignment(scoreRestartBox, Pos.CENTER_RIGHT);
        BorderPane.setMargin(scoreRestartBox, new Insets(20, ((int)(SCENE_WIDTH / 12)), (((int)(SCENE_HEIGHT/ 25))), 0));




        var main = new BorderPane();
        main.setTop(topElements);

        main.setCenter(board.getGridGroup());
        BorderPane.setAlignment(board.getGridGroup(), Pos.BOTTOM_CENTER);
        BorderPane.setMargin(board.getGridGroup(), new Insets(0,0, 50,0 ));

        var scene = new Scene(main, SCENE_WIDTH, SCENE_HEIGHT);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

        //this doesnt wanna load from css
        main.setStyle("-fx-background-color: #FAF8F0; -fx-padding: 0 0 0px 0 ");
        stage.setScene(scene);


        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                System.out.println("asd");
                main.requestFocus();
            }
        };

        restartButton.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);

        scene.setOnKeyPressed(
                (KeyEvent e) -> {
                    int[][] boardPreMove = board.getTileValues();
                    if (e.getCode() == KeyCode.LEFT) {
                        board.moveLeft(-1);
                    }
                    if (e.getCode() == KeyCode.RIGHT) {
                        board.moveRight(1);
                    }
                    if (e.getCode() == KeyCode.UP) {
                        board.moveUp(-1);
                    }
                    if (e.getCode() == KeyCode.DOWN) {
                        board.moveDown(1);
                    }
                    int[][] boardAfterMove = board.getTileValues();

                    //if direction of movements was not valid, meaning no tile has moved, dont generate new tile
                    if (!Arrays.deepEquals(boardPreMove, boardAfterMove)) {
                        board.addRandomTile();
                        score.setText("Score\n" + board.getScore());
                    }
                });

        stage.show();
        //anyad
        main.requestFocus();
        //main.onKeyPressedProperty().bind(scene.onKeyPressedProperty());

    }


    public static void main(String[] args) {
        launch();
    }

}