package org.alkfejl;

import java.util.Arrays;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class Game2048 extends Application {

    public final static int SCENE_WIDTH = Board.BOARD_SIZE * 125;
    public final static int SCENE_HEIGHT = Board.BOARD_SIZE * 125 + 100;

    @Override
    public void start(Stage stage) {

        stage.setTitle("Fejlesztett Alkamazás");
        var gameManager = new GameManager();

        var mainScene = gameManager.initialize();
        var scene = new Scene(mainScene, SCENE_WIDTH, SCENE_HEIGHT);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

        stage.setScene(scene);

        EventHandler<MouseEvent> eventHandler = mouseEvent -> start(stage);


        gameManager.getRestartButton().addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);

        scene.setOnKeyPressed(
                (KeyEvent e) -> {
                    int[][] boardPreMove = gameManager.getBoard().getTileValues();
                    if (e.getCode() == KeyCode.LEFT) {
                        gameManager.getBoard().moveLeft(-1);
                    }
                    if (e.getCode() == KeyCode.RIGHT) {
                        gameManager.getBoard().moveRight(1);
                    }
                    if (e.getCode() == KeyCode.UP) {
                        gameManager.getBoard().moveUp(-1);
                    }
                    if (e.getCode() == KeyCode.DOWN) {
                        gameManager.getBoard().moveDown(1);
                    }
                    int[][] boardAfterMove = gameManager.getBoard().getTileValues();

                    gameManager.getBoard().checkWinner();


                    if (!Arrays.deepEquals(boardPreMove, boardAfterMove)) {
                        gameManager.getBoard().addRandomTile();
                        gameManager.getScore().setText("Score\n" + gameManager.getBoard().getScore());
                    }
                    //if direction of movements was not valid, meaning no tile has moved, dont generate new tile
                    if (gameManager.getBoard().getEmptyLocations().size() == 0 && !gameManager.getBoard().tileMatchesAvailable()){
                        System.out.println("we done now");
                    }

                });

        //ez itt még lehet buggos majd popup windowwal megnezzeuk
       scene.setOnKeyReleased(keyEvent -> {
            if (gameManager.getBoard().getEmptyLocations().size() == 0 && !gameManager.getBoard().tileMatchesAvailable()){
                System.out.println("we done now");
            }
        });


        stage.show();

        //anyad
        mainScene.requestFocus();
        //main.onKeyPressedProperty().bind(scene.onKeyPressedProperty());

    }


    public static void main(String[] args) {
        launch();
    }

}