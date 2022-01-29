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

    //verifica se existe a posição e me retorna a posição da matriz caso exista;
    public Piece piece(int row, int colmn){
        if (!positionExists(row, colmn)){
            throw  new BoardExeption("Position not on the board.");
        }
        return pieces[row][colmn];
    }

    //verifica se existe a posição e me retorna a posição da matriz caso exista; @sobrecarga
    public Piece piece(Position position){
        if (!positionExists(position)){
            throw new BoardExeption("Position not on the board.");
        }
        return pieces[position.getRow()][position.getColumn()];
    }

    //faz a verificação se existe algo na posição da matriz, se não exisir, coloca uma peça na posição;
    public void placePiece(Piece piece, Position position){
        if (thereIsAPiece(position)){
            throw new BoardExeption("There is already a piece on position " + position);
        }
        pieces[position.getRow()][position.getColumn()] = piece;
        piece.position = position;
    }

    //remove a peça da posição e retorna a peça, colocando a posição que ela tava como null;
    public Piece removePiece(Position position){
        if (!positionExists(position)){
            throw new BoardExeption("Position not on the board.");
        }
        //verifica se a peça é nula
        if (piece(position) == null){
            return null;
        }
        Piece aux = piece(position);
        aux.position = null;
        pieces[position.getRow()][position.getColumn()] =  null;
        return aux;
    }

    //faz a verificação das posições da matriz;
    private boolean positionExists(int row, int colmn){
        return (row >= 0 && row < rows) && (colmn >= 0 && colmn < colmns);
    }

    //faz a verificação das posições da matriz pela posição; @sobrecarga;
    public boolean positionExists(Position position){
        return positionExists(position.getRow(), position.getColumn());
    }

    //primeiro faz a verificação se existe a posição na matriz e depois verifica se existe alguma peça na posição.
    public boolean thereIsAPiece(Position position){
        if (!positionExists(position)){
            throw  new BoardExeption("Position not on the board.");
        }
        return  piece(position) != null;
    }
}
