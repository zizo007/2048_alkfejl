package org.alkfejl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.Optional;

import static org.alkfejl.Game2048.*;

public class GameManager {

    private Board board;
    private Button restartButton, playButton;
    private Text score;
    private TextField levelInput, nameInput;


    public BorderPane constructGameScene(){
        this.board = new Board();
        var title = new Text("2048");
        title.getStyleClass().add("game-title");

        this.score = new Text("Score\n" + board.getScore());
        score.getStyleClass().add("score");

        var titleBox = new HBox();
        titleBox.getChildren().add(title);

        var scoreRestartBox = new VBox();
        scoreRestartBox.setSpacing(10);
        scoreRestartBox.setAlignment(Pos.CENTER);

        this.restartButton = new Button("New Game");
        restartButton.getStyleClass().add("restart-button");


        scoreRestartBox.getStyleClass().add("score-box");
        scoreRestartBox.getChildren().addAll(score, restartButton);

        var topElements = new BorderPane();
        topElements.setLeft(titleBox);
        BorderPane.setAlignment(titleBox, Pos.CENTER_LEFT);
        BorderPane.setMargin(titleBox, new Insets(20,0, 0, (int)(SCENE_WIDTH / 12)));
        topElements.setRight(scoreRestartBox);
        BorderPane.setAlignment(scoreRestartBox, Pos.CENTER_RIGHT);
        BorderPane.setMargin(scoreRestartBox, new Insets(20, ((int)(SCENE_WIDTH / 12)), (((int)(SCENE_HEIGHT/ 25))), 0));

        var main = new BorderPane();
        main.setTop(topElements);

        main.setCenter(board.getGridGroup());
        BorderPane.setAlignment(board.getGridGroup(), Pos.BOTTOM_CENTER);
        BorderPane.setMargin(board.getGridGroup(), new Insets(0,0, 50,0 ));
        main.setStyle("-fx-background-color: #FAF8F0; -fx-padding: 0 0 0px 0 ");

        return main;
    }

    public Scene constructMenuScene() {
        Scene menuScene;
        var title = new Text("2048");
        title.getStyleClass().add("game-title");

        var menuBox = new VBox();
        menuBox.setAlignment(Pos.CENTER);
        menuBox.setSpacing(5);
        nameInput = new TextField();
        nameInput.setPromptText("Enter your nickname");
        nameInput.setPrefHeight(50);
        nameInput.setFocusTraversable(false);
        nameInput.getStyleClass().add("text-field");
        levelInput = new TextField();
        levelInput.setPromptText("Levels");
        levelInput.setPrefHeight(50);
        levelInput.setFocusTraversable(false);
        levelInput.getStyleClass().add("text-field");

        ObservableList<String> options =
                FXCollections.observableArrayList("4x4", "5x5", "6x6", "8x8");
        ComboBox<String> gridSizeSelector = new ComboBox<>(options);
        gridSizeSelector.setValue("4x4");

        EventHandler<ActionEvent> event =
                e -> Board.setBoardSize (Integer.parseInt(String.valueOf(gridSizeSelector.getValue().charAt(0))));

        gridSizeSelector.setOnAction(event);
        gridSizeSelector.setPrefWidth(245);


        //majd kesobb
        Button pictureConfig = new Button("Pictures");
        pictureConfig.getStyleClass().add("menu-button");
        pictureConfig.setPrefWidth(245);
        Button topList = new Button("Toplist");
        topList.getStyleClass().add("menu-button");
        topList.setPrefWidth(245);
        playButton = new Button("Play");
        playButton.setPrefWidth(245);
        playButton.getStyleClass().add("menu-button");


        menuBox.getChildren().addAll(title,nameInput,levelInput,gridSizeSelector,pictureConfig,topList, playButton);

        menuScene = new Scene(menuBox, 250, 500);
        menuScene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

        return menuScene;
    }


    public void gameOver(){
        ButtonType restart = new ButtonType("Try again", ButtonBar.ButtonData.OK_DONE);
        Alert gameEnded = new Alert(Alert.AlertType.INFORMATION, null, restart);
        gameEnded.setTitle("Game Over");
        gameEnded.setHeaderText("You Lost");
        DialogPane dialogPane = gameEnded.getDialogPane();
        dialogPane.getStylesheets().add(
                getClass().getResource("/css/style.css").toExternalForm());
        dialogPane.getStyleClass().add("Dialog");
        gameEnded.showAndWait();
    }


    public Optional<ButtonType> gameWon(ButtonType keepplaying, ButtonType newgame){
        Alert winner = new Alert(Alert.AlertType.INFORMATION, null, keepplaying, newgame);
        winner.setTitle("You Win");
        winner.setHeaderText("What would you like to do?");
        DialogPane dialogPane = winner.getDialogPane();
        dialogPane.getStylesheets().add(
                getClass().getResource("/css/style.css").toExternalForm());
        dialogPane.getStyleClass().add("Dialog");
        return winner.showAndWait();
    }

    public void invalidInput(){
        Alert invalidInput = new Alert(Alert.AlertType.INFORMATION, null);
        invalidInput.setHeaderText("Invalid input");
        invalidInput.setHeaderText("Nickname and levels must be filled out,\n levels must be an integert");
        DialogPane dialogPane = invalidInput.getDialogPane();
        dialogPane.getStylesheets().add(
                getClass().getResource("/css/style.css").toExternalForm());
        dialogPane.getStyleClass().add("Dialog");
        invalidInput.showAndWait();
    }


    public Board getBoard() {
        return board;
    }

    public Button getRestartButton() {
        return restartButton;
    }

    public Text getScore() {
        return score;
    }

    public void setScore(Text score) {
        this.score = score;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setRestartButton(Button restartButton) {
        this.restartButton = restartButton;
    }

    public Button getPlayButton() {
        return playButton;
    }

    public TextField getLevelInput() {
        return levelInput;
    }

    public TextField getNameInput() {
        return nameInput;
    }
}
