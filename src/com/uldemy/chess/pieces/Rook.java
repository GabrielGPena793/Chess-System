package com.uldemy.chess.pieces;

import com.uldemy.boardgame.Board;
import com.uldemy.chess.ChessPiece;
import com.uldemy.chess.Color;

public class Rook extends ChessPiece {

    public Rook(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "R";
    }
}
