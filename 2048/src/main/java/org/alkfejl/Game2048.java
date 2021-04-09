package org.alkfejl;

import java.util.Arrays;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class Game2048 extends Application {

    public static int SCENE_WIDTH;
    public static int SCENE_HEIGHT;
    private Stage mainWindow;
    private Scene menuScene, gameScene;
    private static boolean gameIsOpen = false;
    private BorderPane mainScene;

    public static int getSceneWidth() {
        return SCENE_WIDTH;
    }

    public Stage getMainWindow() {
        return mainWindow;
    }

    public static boolean isGameIsOpen() {
        return gameIsOpen;
    }

    public void setMainWindow(Stage mainWindow) {
        this.mainWindow = mainWindow;
    }

    public static void setGameIsOpen(boolean gameIsOpen) {
        Game2048.gameIsOpen = gameIsOpen;
    }

    @Override
    public void start(Stage stage) {
        mainWindow = stage;
        //constructMenuScene();

        var gameManager = new GameManager();
        //constructMenuScene();

       /* mainScene = gameManager.initialize();
        gameScene = new Scene(mainScene, SCENE_WIDTH, SCENE_HEIGHT);

        gameScene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        */
        if (!gameIsOpen) {
            menuScene = gameManager.constructMenuScene();
            mainWindow.setScene(menuScene);

            EventHandler<MouseEvent> eventHandler = mouseEvent -> {
                int levels = Integer.parseInt(gameManager.getLevelInput().getText());
                Board.setScoreToWin((int) Math.pow(2, levels));
                gameIsOpen = true;
                mainWindow.requestFocus();
                start(mainWindow);

            };

            gameManager.getPlayButton().addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);

        } else {
            SCENE_WIDTH = Board.BOARD_SIZE * 125;
            SCENE_HEIGHT = Board.BOARD_SIZE * 125 + 100;
            mainScene = gameManager.constructGameScene();
            gameScene = new Scene(mainScene, SCENE_WIDTH, SCENE_HEIGHT);
            gameScene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
            EventHandler<MouseEvent> eventHandler = mouseEvent -> {
                gameIsOpen = true;
                mainWindow.requestFocus();
                start(mainWindow);

            };
            gameManager.getRestartButton().addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
            mainWindow.setScene(gameScene);
            mainWindow.requestFocus();
        }

        mainWindow.setTitle("Fejlesztett Alkamazás");
        //restart event

        //mainWindow.setScene(menuScene);
        if (gameIsOpen) {
            gameScene.setOnKeyPressed(
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

            gameScene.setOnKeyReleased(keyEvent -> {
                if (gameManager.getBoard().checkWinner()) {
                    ButtonType keepPlaying = new ButtonType("Keep Playing", ButtonBar.ButtonData.OK_DONE);
                    ButtonType newGame = new ButtonType("New Game", ButtonBar.ButtonData.OK_DONE);
                    var answer = gameManager.gameWon(keepPlaying, newGame);

                    if (answer.isPresent() && answer.get() == newGame) {
                        gameIsOpen = true;
                        start(mainWindow);
                    }
                    if (answer.isPresent() && answer.get() == keepPlaying) {
                        //this allows the player to play forever
                        // because at no point in the game will a tile contain value of 50
                        Board.setScoreToWin(50);
                    }
                } else if (gameManager.getBoard().getEmptyLocations().size() == 0 && !gameManager.getBoard().tileMatchesAvailable()) {
                    //notify player if game ends
                    gameManager.gameOver();
                    gameIsOpen = true;
                    start(mainWindow);

                }
            });
            mainScene.requestFocus();
        }

        mainWindow.show();

        /*if (gameIsOpen) {
            mainScene.requestFocus();
        */
    }

    public static void main(String[] args) {
        launch();
    }

}