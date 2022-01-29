package com.uldemy.chess;

import com.uldemy.boardgame.Board;
import com.uldemy.boardgame.Position;
import com.uldemy.chess.pieces.King;
import com.uldemy.chess.pieces.Rook;

public class ChessMatch {

    private Board board;

    public ChessMatch() {
        board = new Board(8,8);
        initialSetup();
    }

    public ChessPiece[][] getPieces(){
        ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColmns()];
        for (int i = 0; i < board.getRows();i++){
            for (int j = 0; j < board.getColmns();j++){
                mat[i][j] = (ChessPiece) board.piece(i,j);
            }
        }
        return mat;
    }

    public void initialSetup(){
        board.placePiece(new Rook(board, Color.WHITHE), new Position(2,1));
        board.placePiece(new King(board, Color.BLACK), new Position(0,4));
        board.placePiece(new King(board, Color.WHITHE), new Position(7,4));
    }
}
