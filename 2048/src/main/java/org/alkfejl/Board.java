package org.alkfejl;

import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public class Board extends Pane {

    public static final int BOARD_SIZE = 4;
    public static final int SQUARE_SIZE = 100;
    private final Group gridGroup = new Group();
    private  Tile[][] boardPositions = new Tile[BOARD_SIZE][BOARD_SIZE];
    private final int TILE_OFFSET = 7;


    public Board(){
        createGrid();
        fillTileArray();
        gridGroup.getStyleClass().add("game-grid");
    }


    public void fillTileArray(){
        for(int i = 0; i < BOARD_SIZE; i ++ ){
            for(int j = 0; j < BOARD_SIZE; j ++){
                boardPositions[i][j] = new Tile(0);
            }
        }
        addRandomTile();
        addRandomTile();
    }


    private Rectangle createCell(int i, int j){
        //remove magic numbers
        Rectangle cell = new Rectangle(SQUARE_SIZE,SQUARE_SIZE);
        cell.setX(i * SQUARE_SIZE - TILE_OFFSET);
        cell.setY(j * SQUARE_SIZE - TILE_OFFSET);
        cell.getStyleClass().add("game-grid-cell");
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
        boardPositions[pos[0]/SQUARE_SIZE][pos[1]/SQUARE_SIZE] = tile;
        gridGroup.getChildren().add(tile);
    }

    public int[] getValidRandomLocation(){
        int[] ans = new int[2];
        int rand_x = new Random().nextInt(BOARD_SIZE)* SQUARE_SIZE;
        int rand_y = new Random().nextInt(BOARD_SIZE)* SQUARE_SIZE;
        Tile actualTile = boardPositions[rand_x/SQUARE_SIZE][rand_y/SQUARE_SIZE];
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



    public void moveRight(int direction){
        for(int i = BOARD_SIZE - 1; i >= 0; i--) {
            for (int j = BOARD_SIZE - 1; j >= 0; j--) {
                Tile oldTile = boardPositions[j][i];

                if (oldTile.getValue() != 0 && j < BOARD_SIZE - 1){
                    int temp = j + direction;
                    Tile neighbourTile = boardPositions[temp][i];

                    while(neighbourTile.getValue() == 0 && temp < BOARD_SIZE - 1){
                        temp += direction;
                        neighbourTile = boardPositions[temp][i];
                    }

                    moveHorizontally(i, temp, direction, neighbourTile, oldTile);
                }
            }
        }
    }

    public void moveLeft(int direction){
        for(int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                Tile oldTile = boardPositions[j][i];

                if (oldTile.getValue() != 0 && j > 0){
                    int temp = j + direction;
                    Tile neighbourTile = boardPositions[temp][i];

                    while(neighbourTile.getValue() == 0 && temp > 0){
                        temp += direction;
                        neighbourTile = boardPositions[temp][i];
                    }

                    moveHorizontally(i, temp, direction, neighbourTile, oldTile);
                }
            }
        }
    }

    public void moveUp(int direction){
        for(int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                Tile oldTile = boardPositions[j][i];

                if (oldTile.getValue() != 0 && i > 0){
                    int temp = i + direction;
                    Tile neighbourTile = boardPositions[j][temp];

                    while(neighbourTile.getValue() == 0 && temp > 0){
                        temp += direction;
                        neighbourTile = boardPositions[j][temp];
                    }

                    moveVertically(j, temp, direction, neighbourTile, oldTile);
                }
            }
        }
    }

    public void moveDown(int direction){
        for(int i = BOARD_SIZE - 1; i >= 0; i--) {
            for (int j = BOARD_SIZE - 1; j >= 0; j--) {
                Tile oldTile = boardPositions[j][i];

                if (oldTile.getValue() != 0 && i < BOARD_SIZE - 1){
                    int temp = i + direction;
                    Tile neighbourTile = boardPositions[j][temp];

                    while(neighbourTile.getValue() == 0 && temp < BOARD_SIZE - 1){
                        temp += direction;
                        neighbourTile = boardPositions[j][temp];
                    }

                    moveVertically(j, temp, direction, neighbourTile, oldTile);
                }
            }
        }
    }



    public void moveHorizontally(int i, int temp, int direction, Tile neighbourTile, Tile oldTile){
        Tile tile;
        if(neighbourTile.getValue() != 0){

            if(oldTile.getValue() == neighbourTile.getValue()){
                tile = new Tile(oldTile.getValue() + neighbourTile.getValue());
                gridGroup.getChildren().remove(neighbourTile);
                tile.setLayoutX((temp) * SQUARE_SIZE);
                boardPositions[temp][i] = tile;
            }
            else {
                tile = new Tile(oldTile.getValue());
                tile.setLayoutX((temp - direction) * SQUARE_SIZE);
                boardPositions[temp - direction][i] = tile;
            }
        }
        else{
            tile = new Tile(oldTile.getValue());
            tile.setLayoutX(temp * SQUARE_SIZE);
            boardPositions[temp][i] = tile;
        }
        gridGroup.getChildren().add(tile);
        tile.setLayoutY(oldTile.getLayoutY());
        gridGroup.getChildren().remove(oldTile);
        oldTile.setValue(0);
    }


    public void moveVertically(int j, int temp, int direction, Tile neighbourTile, Tile oldTile){
        Tile tile;
        if(neighbourTile.getValue() != 0){

            if(oldTile.getValue() == neighbourTile.getValue()){
                tile = new Tile(oldTile.getValue() + neighbourTile.getValue());
                gridGroup.getChildren().remove(neighbourTile);
                tile.setLayoutY((temp) * SQUARE_SIZE);
                boardPositions[j][temp] = tile;
            }
            else {
                tile = new Tile(oldTile.getValue());
                tile.setLayoutY((temp - direction) * SQUARE_SIZE);
                boardPositions[j][temp - direction] = tile;
            }
        }
        else{
            tile = new Tile(oldTile.getValue());
            tile.setLayoutY(temp * SQUARE_SIZE);
            boardPositions[j][temp] = tile;
        }
        gridGroup.getChildren().add(tile);
        tile.setLayoutX(oldTile.getLayoutX());
        gridGroup.getChildren().remove(oldTile);
        oldTile.setValue(0);
    }

}
