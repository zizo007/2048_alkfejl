package org.alkfejl;

import java.util.Arrays;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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
        Score Title = new Score(2048);
        Score score = new Score(0);

        var label = new StackPane();

        var title = new HBox();
        var scoreBox = new VBox();
        scoreBox.getChildren().add(score);
        scoreBox.getChildren().add(new Button("new game"));
        title.getChildren().add(Title);

        title.getChildren().add(scoreBox);

        label.getChildren().add(title);
        label.getChildren().add(board.getGridGroup());
        // System.out.println(Arrays.deepToString(board.getBoardPositions()));
        var scene = new Scene(label, SCENE_WIDTH, SCENE_HEIGHT);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        //this doesnt wanna load from css
        label.setStyle("-fx-background-color: #FAF8F0; -fx-alignment: bottom-center; -fx-padding: 0 0 50px 0 ");
        stage.setScene(scene);
        final long startNanoTime = System.nanoTime();

        scene.setOnKeyPressed(
                new EventHandler<>() {
                    public void handle(KeyEvent e) {
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
                        }
                    }
                });


        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}