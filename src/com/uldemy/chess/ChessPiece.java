package com.uldemy.chess;

import com.uldemy.boardgame.Board;
import com.uldemy.boardgame.Piece;
import com.uldemy.boardgame.Position;

public abstract class ChessPiece extends Piece {

    private Color color;
    private int moveCount;


    public ChessPiece(Board board, Color color) {
        super(board);
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public int getMoveCount() {
        return moveCount;
    }

    //acrescenta um movimento a peça
    public void increaseMoveCount(){
        moveCount++;
    }

    //decrementa um movimento a peça
    public void decreaseMoveCount(){
        moveCount--;
    }

    public ChessPosition getChessPosition(){
        return ChessPosition.fromPosition(position);
    }

    //verifica se existe uma peça inimiga ou aliada na posição alvo;
    protected boolean isThereOpponentPiece(Position position){
        ChessPiece p = (ChessPiece) getBoard().piece(position);
        return  p != null && p.getColor() != color;
    }
}
