package org.alkfejl;

import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public class Board extends Pane {

    private final int BOARD_SIZE = 4;
    private final Group gridGroup = new Group();
   private Integer[][] boardPositions = new Integer[BOARD_SIZE][BOARD_SIZE];

    private Rectangle createCell(int i, int j){
        //remove magic numbers
        Rectangle cell = new Rectangle(100,100);
        cell.setFill(Color.WHITE);
        cell.setStroke(Color.GREY);
        cell.setX(i * 100);
        cell.setY(j * 100);
        return cell;
    }

    public void createGrid(){
        for(int i = 0; i < BOARD_SIZE; i ++ ){
            for(int j = 0; j < BOARD_SIZE; j ++){
                gridGroup.getChildren().add(createCell(i,j));
            }
        }
    }

    public Group getGridGroup() {
        return gridGroup;
    }

    public Integer[][] getBoardPositions() {
        return boardPositions;
    }

    public void addRandomTile(){
        Tile tile = Tile.newRandomTile();
        int[] pos = getValidRandomLocation();
        tile.setLayoutX(pos[0]);
        tile.setLayoutY(pos[1]);
        gridGroup.getChildren().add(tile);
    }

    public int[] getValidRandomLocation(){
        int[] ans = new int[2];
        int rand_x = new Random().nextInt(BOARD_SIZE)* 100;
        int rand_y = new Random().nextInt(BOARD_SIZE)* 100;
        if(boardPositions[rand_x/100][rand_y/100] != null){
            getValidRandomLocation();
        }
        else {
            ans[0] = rand_x;
            ans[1] = rand_y;

        }
        return ans;
    }
}
