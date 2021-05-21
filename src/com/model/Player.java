package com.model;

public class Player {

    private String name;
    private int id;
    private int movesCount;
    private boolean isTurn; // Program how turns will work.

    public Player(String name, int id,boolean turn) {
        this.name = name;
        this.id = id;
        this.isTurn = turn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void updateTurn(boolean turn)    {
        isTurn = turn;
    }

    public boolean isTurn() {
        return isTurn;
    }
}
