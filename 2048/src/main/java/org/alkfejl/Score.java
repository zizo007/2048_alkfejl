package org.alkfejl;

import javafx.scene.control.Label;

public class Score extends Label {

    private final int LEFT_OFFSET = 20 * Board.BOARD_SIZE;
    private int score;


    public Score(int score) {
        this.score = score;
        setText(Integer.toString(score));
    }

    public int getScore() {
        return score;
    }


    public void setScore(int score) {
        this.score = score;
    }


    @Override
    public String toString() {
        return "Score{" +
                "score=" + score +
                '}';
    }
}
