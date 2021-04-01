package org.alkfejl;

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
                            //System.out.println(board.getGridGroup().getChildren());
                            board.moveLeft(-1);
                            //board.addRandomTile();
                           /* for(int i = 0; i < 4; i++){
                                for(int j = 0; j < 4; j++){
                                    System.out.print(board.getBoardPositions()[j][i].getValue());
                                }
                                System.out.println();
                            }*/

                        }
                        if (e.getCode() == KeyCode.RIGHT){
                            board.moveRight(1);
                            //board.addRandomTile();
                        }

                        if (e.getCode() == KeyCode.UP){
                            board.moveUp(-1);
                        }

                        if (e.getCode() == KeyCode.DOWN){
                            board.moveDown(1);
                        }

                        board.addRandomTile();
                    }
                });


        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}