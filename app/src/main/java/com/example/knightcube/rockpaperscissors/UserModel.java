package com.example.knightcube.rockpaperscissors;

/**
 * Created by Rajat Kumar Gupta on 10/07/2018.
 */
public class UserModel {
    private String name;
    private long score;

    public UserModel(String name, long score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public UserModel(){

    }
}
