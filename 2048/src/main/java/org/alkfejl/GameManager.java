package org.alkfejl;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static org.alkfejl.Game2048.SCENE_HEIGHT;
import static org.alkfejl.Game2048.SCENE_WIDTH;

public class GameManager {

    private Board board;
    private Button restartButton;
    private Text score;



    public BorderPane initialize(){
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


    public void restart(Stage stage){
        this.board = new Board();
        this.score = new Text("Score\n" + board.getScore());
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
}
