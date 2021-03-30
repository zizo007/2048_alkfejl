package org.alkfejl;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.util.Random;

public class Tile extends Label {

        private int value;


        Tile(Integer value) {
                final int squareSize = 100-10;
                setMinSize(squareSize, squareSize);
                setMaxSize(squareSize, squareSize);
                setPrefSize(squareSize, squareSize);
                setAlignment(Pos.CENTER);

                this.value = value;
                setText(value.toString());
                getStyleClass().addAll("game-label", "game-tile-" + value);

        }

        public static Tile newRandomTile() {
                int value = new Random().nextDouble() < 0.9 ? 2 : 4;
                return new Tile(value);
        }

        public int getValue() {
                return value;
        }


        public void merge(Tile another) {
                this.value += another.value;
                setText(Integer.toString(value));
        }

        public void setValue(int value) {
                this.value = value;
        }

        @Override
        public String toString() {
                return "Tile{" +
                        "value=" + value +
                        '}';
        }
}
