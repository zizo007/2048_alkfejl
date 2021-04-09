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
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class Game2048 extends Application {

    public final static int SCENE_WIDTH = Board.BOARD_SIZE * 125;
    public final static int SCENE_HEIGHT = Board.BOARD_SIZE * 125 + 100;
    private Stage mainWindow;
    private Scene menuScene, gameScene;
    private boolean gameIsOpen = false;

    @Override
    public void start(Stage stage) {
        mainWindow = stage;
        constructMenuScene();

        var gameManager = new GameManager();
        //constructMenuScene();

        var mainScene = gameManager.initialize();
        gameScene = new Scene(mainScene, SCENE_WIDTH, SCENE_HEIGHT);

        gameScene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

        if (!gameIsOpen){
            mainWindow.setScene(menuScene);
        }
        else{
            mainWindow.setScene(gameScene);
        }

        mainWindow.setTitle("Fejlesztett Alkamazás");
        //restart event
        EventHandler<MouseEvent> eventHandler = mouseEvent -> {
            gameIsOpen = true;
            mainWindow.requestFocus();
            start(mainWindow);

        };
        gameManager.getRestartButton().addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
        //mainWindow.setScene(menuScene);
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
            if (gameManager.getBoard().checkWinner()){
                ButtonType keepPlaying = new ButtonType("Keep Playing", ButtonBar.ButtonData.OK_DONE);
                ButtonType newGame = new ButtonType("New Game", ButtonBar.ButtonData.OK_DONE);
                var answer = gameManager.gameWon(keepPlaying, newGame);

                if (answer.isPresent() && answer.get() == newGame){
                    gameIsOpen = true;
                    start(mainWindow);
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
                gameIsOpen = true;
                start(mainWindow);

           }
        });

        mainWindow.show();

        mainScene.requestFocus();
        //main.onKeyPressedProperty().bind(scene.onKeyPressedProperty());

    }


    private void constructMenuScene() {
        var title = new Text("2048");
        title.getStyleClass().add("game-title");

        var menuBox = new VBox();
        menuBox.setAlignment(Pos.CENTER);
        menuBox.setSpacing(5);
        TextField nameInput = new TextField();
        nameInput.setPromptText("Enter your nickname");
        nameInput.setPrefHeight(50);
        nameInput.setFocusTraversable(false);
        nameInput.getStyleClass().add("text-field");

        ObservableList<String> options =
                FXCollections.observableArrayList("4x4", "5x5", "6x6", "8x8");
        ComboBox<String> gridSizeSelector = new ComboBox<>(options);
        gridSizeSelector.setValue("4x4");

        EventHandler<ActionEvent> event =
                e -> Board.setBoardSize (gridSizeSelector.getValue().charAt(0));

        gridSizeSelector.setOnAction(event);




        //majd kesobb
        Button pictureConfig = new Button("Pictures");
        pictureConfig.getStyleClass().add("menu-button");
        Button topList = new Button("Toplist");
        topList.getStyleClass().add("menu-button");
        topList.setPrefWidth(170);
        Button play = new Button("Play");
        play.setPrefWidth(170);
        play.getStyleClass().add("menu-button");
        play.setOnAction(actionEvent -> {
            mainWindow.setScene(gameScene);
        });


        menuBox.getChildren().addAll(title,nameInput,gridSizeSelector,pictureConfig,topList,play);

        menuScene = new Scene(menuBox, 200, 400);
        menuScene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
    }



    public static void main(String[] args) {
        launch();
    }

}