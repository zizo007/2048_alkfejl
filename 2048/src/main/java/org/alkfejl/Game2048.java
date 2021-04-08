package org.alkfejl;

import java.util.Arrays;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
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

        //restart event
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

                    //if direction of movements was not valid, meaning no tile has moved, dont generate new tile
                    if (!Arrays.deepEquals(boardPreMove, boardAfterMove)) {
                        gameManager.getBoard().addRandomTile();
                        gameManager.getScore().setText("Score\n" + gameManager.getBoard().getScore());
                    }


                });

        scene.setOnKeyReleased(keyEvent -> {
            if (gameManager.getBoard().checkWinner()){
                ButtonType keepPlaying = new ButtonType("Keep Playing", ButtonBar.ButtonData.OK_DONE);
                ButtonType newGame = new ButtonType("New Game", ButtonBar.ButtonData.OK_DONE);
               var answer = gameManager.gameWon(keepPlaying, newGame);

                if (answer.isPresent() && answer.get() == newGame){
                    start(stage);
                }
                if (answer.isPresent() && answer.get() == keepPlaying){
                    //this allows the player to play forever
                    // because at no point in the game will a tile contain value of 50
                    Board.setScoreToWin(50);
                }
            }

           else if (gameManager.getBoard().getEmptyLocations().size() == 0 && !gameManager.getBoard().tileMatchesAvailable()){
                //notify player if game ends
                gameManager.gameOver();
                start(stage);

           }
        });

        stage.show();

        mainScene.requestFocus();
        //main.onKeyPressedProperty().bind(scene.onKeyPressedProperty());

    }


    public static void main(String[] args) {
        launch();
    }

}