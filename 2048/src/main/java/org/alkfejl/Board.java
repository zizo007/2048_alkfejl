package org.alkfejl;

import javafx.scene.Group;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Random;

public class Board extends GridPane {

    private int score;
    public static int BOARD_SIZE = 4;
    public static int SCORE_TO_WIN;
    public static final int SQUARE_SIZE = 100;
    private final Group gridGroup = new Group();
    private final Tile[][] boardPositions = new Tile[BOARD_SIZE][BOARD_SIZE];


    public Board(){
        //Score score = new Score(0);
        SCORE_TO_WIN = 2048;
        initializeBoard();
        gridGroup.getStyleClass().add("game-grid");
    }

    public static void setScoreToWin(int scoreToWin) {
        SCORE_TO_WIN = scoreToWin;
    }

    public int getScore() {
        return score;
    }

    public Group getGridGroup() {
        return gridGroup;
    }


    public Tile[][] getBoardPositions() {
        return boardPositions;
    }


    public void initializeBoard(){
        //fill the array with empty tiles, necessary for moving them
        //and add them to the grid
        for(int i = 0; i < BOARD_SIZE; i ++ ){
            for(int j = 0; j < BOARD_SIZE; j ++){
                boardPositions[i][j] = new Tile(0);
                gridGroup.getChildren().add(createCell(i,j));
            }
        }
        //ez még nem biztos h itt marad
        addRandomTile();
        addRandomTile();
    }


    private Rectangle createCell(int i, int j){
        Rectangle cell = new Rectangle(SQUARE_SIZE,SQUARE_SIZE);
        //offset each tile so they are perfectly in the grid cells
        int TILE_OFFSET = 7;
        cell.setX(i * SQUARE_SIZE - TILE_OFFSET);
        cell.setY(j * SQUARE_SIZE - TILE_OFFSET);
        cell.getStyleClass().add("game-grid-cell");
        return cell;
    }


    public void addRandomTile(){
        Tile tile = Tile.newRandomTile();
        int[] pos = getValidRandomLocation();
        tile.setLayoutX(pos[0]);
        tile.setLayoutY(pos[1]);
        boardPositions[pos[0]/SQUARE_SIZE][pos[1]/SQUARE_SIZE] = tile;
        gridGroup.getChildren().add(tile);
    }


    public static void setBoardSize(int boardSize) {
        BOARD_SIZE = boardSize;
    }

    public int[] getValidRandomLocation(){
        ArrayList<int []> emptyPositions = getEmptyLocations();
        int randomIndex = new Random().nextInt(emptyPositions.size());
        int x = emptyPositions.get(randomIndex)[0] * SQUARE_SIZE;
        int y = emptyPositions.get(randomIndex)[1] * SQUARE_SIZE;

        int[] ans = new int[2];
        ans[0] = x;
        ans[1] = y;

        return ans;
    }

    public ArrayList<int[]> getEmptyLocations(){
        //returns the i,j positions of empty positions
        ArrayList<int []> emptyPositions = new ArrayList<>();
        int[][] values = getTileValues();
        for(int i = 0; i < BOARD_SIZE; i ++ ){
            for(int j = 0; j < BOARD_SIZE; j ++){
                if(values[j][i] == 0){
                    int[] position = new int[2];
                    position[0] = i;
                    position[1] = j;
                    emptyPositions.add(position);
                }
            }
        }
        return emptyPositions;
    }


    /**
     All movement is implemented the same way:
        Traverse the board
        if the Value of the tile is not 0 and it can move we look for the farthest valid place
        pass this position the movement function
    */

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
        //if neighbourtile is not 0 we check if same value
        // if yes we merge and delete the tile that got merged
        if(neighbourTile.getValue() != 0){

            if(oldTile.getValue() == neighbourTile.getValue()){
                tile = new Tile(oldTile.getValue() + neighbourTile.getValue());
                gridGroup.getChildren().remove(neighbourTile);
                tile.setLayoutX((temp) * SQUARE_SIZE);
                boardPositions[temp][i] = tile;
                score += tile.getValue();
            }
            else {
                tile = new Tile(oldTile.getValue());
                tile.setLayoutX((temp - direction) * SQUARE_SIZE);
                boardPositions[temp - direction][i] = tile;
            }
        }
        // this branch runs when we didnt find any not 0 value tiles in the direction we are moving
        // we just place the tile on the edge of the board(which edge depends on the direction of movement)
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
        //same as moveHorizontally
        Tile tile;
        if(neighbourTile.getValue() != 0){

            if(oldTile.getValue() == neighbourTile.getValue()){
                tile = new Tile(oldTile.getValue() + neighbourTile.getValue());
                gridGroup.getChildren().remove(neighbourTile);
                tile.setLayoutY((temp) * SQUARE_SIZE);
                boardPositions[j][temp] = tile;
                score += tile.getValue();

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

    public int[][] getTileValues(){
        int[][] values = new int[BOARD_SIZE][BOARD_SIZE];
        for(int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                values[i][j] = boardPositions[j][i].getValue();
            }
        }
        return values;
    }


    public boolean checkWinner(){
        int[][] values = getTileValues();
        for(int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++){
                if (values[i][j] == SCORE_TO_WIN){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean tileMatchesAvailable(){
        //check every 3 tile group horizontally and vertically, if 2 values match that means a merge is available and
        //the game is not over

        int[][] values = getTileValues();
        for(int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 1; j < BOARD_SIZE - 1; j++){
                int leftNeighbour = values[i][j - 1];
                int tile = values[i][j];
                int rightNeighbour = values[i][j + 1];

                if (leftNeighbour == tile || tile == rightNeighbour){
                    return true;
                }
            }
        }

        for(int i = 1; i < BOARD_SIZE - 1; i++) {
            for (int j = 0; j < BOARD_SIZE; j++){
                int leftNeighbour = values[i - 1][j];
                int tile = values[i][j];
                int rightNeighbour = values[i + 1][j];

                if (leftNeighbour == tile || tile == rightNeighbour){
                    return true;
                }
            }
        }

        return false;
    }



}
