package org.alkfejl;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import java.lang.Math;
import java.util.Arrays;

/**
 * JavaFX App
 */
public class Game2048 extends Application {

    @Override
    public void start(Stage stage) {

        Board board = new Board();
        board.createGrid();
        board.addRandomTile();
        board.addRandomTile();
        board.addRandomTile();
        board.addRandomTile();
        board.addRandomTile();
        board.addRandomTile();
        board.addRandomTile();
        board.addRandomTile();
        board.addRandomTile();
        board.addRandomTile();
        board.addRandomTile();
        board.addRandomTile();
        board.addRandomTile();
        board.addRandomTile();
        board.addRandomTile();
        board.addRandomTile();
        board.addRandomTile();

        var label = new StackPane();
        label.getChildren().add(board.getGridGroup());
        // System.out.println(Arrays.deepToString(board.getBoardPositions()));
        var scene = new Scene(label, 500, 500);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}