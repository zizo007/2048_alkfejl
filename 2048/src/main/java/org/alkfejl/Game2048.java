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
        board.fillTileArray();
        for(int i = 0; i<16; i ++){
            board.addRandomTile();
        }

        for(int i = 0; i<4; i ++){
            System.out.print("[");
            for(int j = 0; j<4; j ++){
                System.out.print(board.getBoardPositions()[j][i].getValue());
            }
            System.out.println("]");
        }

        board.getBoardPositions()[0][0].merge(board.getBoardPositions()[0][1]);

        for(int i = 0; i<4; i ++){
            System.out.print("[");
            for(int j = 0; j<4; j ++){
                System.out.print(board.getBoardPositions()[j][i].getValue());
            }
            System.out.println("]");
        }

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