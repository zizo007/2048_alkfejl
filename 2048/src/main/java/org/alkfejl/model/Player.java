package org.alkfejl.model;

import javafx.beans.property.*;

public class Player {
    private IntegerProperty id = new SimpleIntegerProperty(this, "id");
    private StringProperty name = new SimpleStringProperty(this, "name");
    private DoubleProperty time = new SimpleDoubleProperty(this, "time");
    private IntegerProperty level = new SimpleIntegerProperty(this, "level");
    private IntegerProperty score = new SimpleIntegerProperty(this, "score");

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public double getTime() {
        return time.get();
    }

    public DoubleProperty timeProperty() {
        return time;
    }

    public void setTime(double time) {
        this.time.set(time);
    }

    public int getLevel() {
        return level.get();
    }

    public IntegerProperty levelProperty() {
        return level;
    }

    public void setLevel(int level) {
        this.level.set(level);
    }

    public int getScore() {
        return score.get();
    }

    public IntegerProperty scoreProperty() {
        return score;
    }

    public void setScore(int score) {
        this.score.set(score);
    }
}
