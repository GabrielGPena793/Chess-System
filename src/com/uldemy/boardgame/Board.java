package com.uldemy.boardgame;

public class Board {

    private int rows;
    private int colmns;
    private Piece[][] pieces;

    public Board(int rows, int colmns) {
        if (rows < 1 || colmns < 1){
            throw new BoardExeption("Error creating board: there must be at least 1 row and 1 column.");
        }
        this.rows = rows;
        this.colmns = colmns;
        pieces = new Piece[rows][colmns];
    }

    public int getRows() {
        return rows;
    }

    public int getColmns() {
        return colmns;
    }

    public Piece piece(int row, int colmn){
        if (!positionExists(row, colmn)){
            throw  new BoardExeption("Position not on the board.");
        }
        return pieces[row][colmn];
    }

    public Piece piece(Position position){
        if (!positionExists(position)){
            throw  new BoardExeption("Position not on the board.");
        }
        return pieces[position.getRow()][position.getColumn()];
    }

    public void placePiece(Piece piece, Position position){
        if (thereIsAPiece(position)){
            throw new BoardExeption("There is already a piece on position " + position);
        }
        pieces[position.getRow()][position.getColumn()] = piece;
        piece.position = position;
    }

    private boolean positionExists(int row, int colmn){
        return (row >= 0 && row < rows) && (colmn >= 0 && colmn < colmns);
    }

    public boolean positionExists(Position position){
        return positionExists(position.getRow(), position.getColumn());
    }

    public boolean thereIsAPiece(Position position){
        if (!positionExists(position)){
            throw  new BoardExeption("Position not on the board.");
        }
        return  piece(position) != null;
    }
}
