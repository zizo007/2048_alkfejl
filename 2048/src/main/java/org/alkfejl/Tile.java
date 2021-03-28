package org.alkfejl;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.util.Random;

public class Tile extends Label {

        private int value;
        private int x;
        private int y;


        Tile(Integer value) {
                final int squareSize = 100;
                setMinSize(squareSize, squareSize);
                setMaxSize(squareSize, squareSize);
                setPrefSize(squareSize, squareSize);
                setAlignment(Pos.CENTER);

                this.value = value;
                setText(value.toString());
                getStyleClass().add("tile-text");

        }

        public static Tile newRandomTile() {
                int value = new Random().nextDouble() < 0.9 ? 2 : 4;
                return new Tile(value);
        }

        public int getValue() {
                return value;
        }

        public int getX() {
                return x;
        }

        public int getY() {
                return y;
        }

        public void merge(Tile another) {
                this.value += another.value;
                setText(Integer.toString(value));
        }



        @Override
        public String toString() {
                return "Tile{" +
                        "value=" + value +
                        ", x=" + x +
                        ", y=" + y +
                        '}';
        }
}
