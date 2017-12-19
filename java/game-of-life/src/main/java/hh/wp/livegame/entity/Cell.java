package hh.wp.livegame.entity;

import hh.wp.livegame.myenum.State;

/**
 * Created by root on 17-6-3.
 */
public class Cell {
    private Integer row;
    private Integer col;
    private State state;

    public Cell(Integer row, Integer col, State state) {
        this.row = row;
        this.col = col;
        this.state = state;
    }

    public Cell() {
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getCol() {
        return col;
    }

    public void setCol(Integer col) {
        this.col = col;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
