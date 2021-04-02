package org.alkfejl;

import java.util.Arrays;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class Game2048 extends Application {

    private final int SCENE_WIDTH = Board.BOARD_SIZE * 125;
    private final int SCENE_HEIGHT = Board.BOARD_SIZE * 125;

    @Override
    public void start(Stage stage) {
        stage.setTitle("Fejlesztett Alkamazás");
        Board board = new Board();

        var label = new StackPane();
        label.getChildren().add(board.getGridGroup());
        // System.out.println(Arrays.deepToString(board.getBoardPositions()));
        var scene = new Scene(label, SCENE_WIDTH, SCENE_HEIGHT);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

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