package com.uldemy.chess;

import com.uldemy.boardgame.Board;
import com.uldemy.boardgame.Piece;
import com.uldemy.boardgame.Position;

public abstract class ChessPiece extends Piece {

    private Color color;

    public ChessPiece(Board board, Color color) {
        super(board);
        this.color = color;
    }

    public Color getColor() {
        return color;
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
