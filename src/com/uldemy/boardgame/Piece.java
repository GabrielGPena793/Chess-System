package com.uldemy.boardgame;

public abstract class Piece {
    protected Position position;
    private Board board;

    public Piece(Board board) {
        this.board = board;
        position =  null;
    }

    protected Board getBoard() {
        return board;
    }

    public abstract boolean[][] possibleMoves();

    public boolean possibleMove(Position position){
        return possibleMoves()[position.getRow()][position.getColumn()];
    }

    //verifica se existe pelo menos um movimento possivel para a peça;
    public boolean isThereAnyPossibleMove(){
        boolean[][] mat = possibleMoves();
        for (int i = 0; i < mat.length; i++){
            for (int j=0; j < mat.length; j++){
                if (mat[i][j]) {
                    return  true;
                }
            }
        }
        return  false;
    }
}
