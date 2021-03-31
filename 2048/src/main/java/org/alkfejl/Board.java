package org.alkfejl;

import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public class Board extends Pane {

    public static final int BOARD_SIZE = 4;
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
        Rectangle cell = new Rectangle(100,100);
        cell.setX(i * 100 - TILE_OFFSET);
        cell.setY(j * 100 - TILE_OFFSET);
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


    public void horizontalMovement(){
        for(int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                Tile oldTile = boardPositions[j][i];
                if (oldTile.getValue() != 0 && Tile.inHorizontalBounds(j)){
                    //Tile tile = new Tile(oldTile.getValue());
                    /*//oldTile.setValue(0);
                   //System.out.println("before:");
                    for(int a = 0; a < 4; a++){
                        for(int b = 0; b < 4; b++){
                            System.out.print(boardPositions[b][a].getValue() +"-");
                        }
                        System.out.println();
                    }
                    //System.out.println("after")
                   // System.out.print(i + " ");
                    //System.out.println(j);
*/

                    int temp = j - 1;
                    Tile neighbourTile = boardPositions[temp][i];
                    while(neighbourTile.getValue() == 0 && temp > 0){
                        temp -= 1;
                        neighbourTile = boardPositions[temp][i];
                    }
                    //Tile tile = new Tile(oldTile.getValue() + neighbourTile.getValue());

                    if(neighbourTile.getValue() != 0){
                        //System.out.println(neighbourTile.getValue());
                        //Tile tile = new Tile(oldTile.getValue() + neighbourTile.getValue());
                        Tile tile;
                        if(oldTile.getValue() == neighbourTile.getValue()){
                            //gridGroup.getChildren().remove(neighbourTile);
                            tile = new Tile(oldTile.getValue() + neighbourTile.getValue());
                            //magic 100as
                            gridGroup.getChildren().remove(neighbourTile);
                            //gridGroup.getChildren().remove(oldTile);
                            //magic 100as
                            tile.setLayoutX((temp) * 100);
                            tile.setLayoutY(oldTile.getLayoutY());
                            boardPositions[temp][i] = tile;
                            //gridGroup.getChildren().add(tile);
                            //gridGroup.getChildren().add(tile);


                        }
                        else {
                            tile = new Tile(oldTile.getValue());
                            //tile.setLayoutY(oldTile.getLayoutY());
                            //magic 100as
                            tile.setLayoutX((temp + 1) * 100);
                            tile.setLayoutY(oldTile.getLayoutY());
                            boardPositions[temp + 1][i] = tile;
                            //gridGroup.getChildren().remove(oldTile);
                        }
                        //gridGroup.getChildren().remove(neighbourTile);
                        gridGroup.getChildren().add(tile);
                    }
                    else{
                        Tile tile = new Tile(oldTile.getValue());
                        //magic 100as
                        tile.setLayoutX(temp * 100);
                        tile.setLayoutY(oldTile.getLayoutY());
                        boardPositions[temp][i] = tile;
                        gridGroup.getChildren().add(tile);
                       // gridGroup.getChildren().remove(oldTile);
                    }
                    //gridGroup.getChildren().add(tile);
                    //tile.setLayoutY(oldTile.getLayoutY());
                    //boardPositions[temp + 1][i] = tile;
                    gridGroup.getChildren().remove(oldTile);
                    oldTile.setValue(0);
                    //gridGroup.getChildren().add(tile);
                    //gridGroup.getChildren().add(tile);

                }
            }
        }
    }

}
