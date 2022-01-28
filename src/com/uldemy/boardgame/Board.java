package com.uldemy.boardgame;

public class Board {

    private int rows;
    private int colmns;
    private Piece[][] pieces;

    public Board(int rows, int colmns) {
        this.rows = rows;
        this.colmns = colmns;
        pieces = new Piece[rows][colmns];
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColmns() {
        return colmns;
    }

    public void setColmns(int colmns) {
        this.colmns = colmns;
    }

    public Piece piece(int row, int colmn){
        return pieces[row][colmn];
    }

    public Piece piece(Position position){
        return pieces[position.getRow()][position.getColumn()];
    }
}
