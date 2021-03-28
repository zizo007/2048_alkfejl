package org.alkfejl;

import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public class Board extends Pane {

    private final int BOARD_SIZE = 4;
    private final Group gridGroup = new Group();
    private  Tile[][] boardPositions = new Tile[BOARD_SIZE][BOARD_SIZE];

    public void fillTileArray(){
        for(int i = 0; i < BOARD_SIZE; i ++ ){
            for(int j = 0; j < BOARD_SIZE; j ++){
                boardPositions[i][j] = new Tile(0);
            }
        }
    }


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

    public Tile[][] getBoardPositions() {
        return boardPositions;
    }

    public void addRandomTile(){
        Tile tile = Tile.newRandomTile();
        int[] pos = getValidRandomLocation();
        tile.setLayoutX(pos[0]);
        tile.setLayoutY(pos[1]);
        boardPositions[pos[0]/100][pos[1]/100] = tile;
        gridGroup.getChildren().add(tile);
    }

    public int[] getValidRandomLocation(){
        int[] ans = new int[2];
        int rand_x = new Random().nextInt(BOARD_SIZE)* 100;
        int rand_y = new Random().nextInt(BOARD_SIZE)* 100;
        Tile actualTile = boardPositions[rand_x/100][rand_y/100];
        //toDo stop generating when no more space is left
        if(actualTile.getValue() != 0){
             return getValidRandomLocation();
        }
        else {
            ans[0] = rand_x;
            ans[1] = rand_y;

        }
        return ans;
    }
}
