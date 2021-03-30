package org.alkfejl;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
        stage.setTitle("Fejlesztett Alkamazás");
        Board board = new Board();

        var label = new StackPane();
        label.getChildren().add(board.getGridGroup());
        // System.out.println(Arrays.deepToString(board.getBoardPositions()));
        var scene = new Scene(label, 500, 500);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

        stage.setScene(scene);
        final long startNanoTime = System.nanoTime();

        scene.setOnKeyPressed(
                new EventHandler<KeyEvent>()
                {
                    public void handle(KeyEvent e)
                    {
                        if (e.getCode() == KeyCode.LEFT){
                            board.nextValidPosition();
                            board.addRandomTile();
                            for(int i = 0; i < 4; i++){
                                for(int j = 0; j < 4; j++){
                                    System.out.print(board.getBoardPositions()[j][i].getValue());
                                }
                                System.out.println();
                            }

                        }
                    }
                });


        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}