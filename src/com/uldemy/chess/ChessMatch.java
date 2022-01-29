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

    private void placeNewPiece(char column, int row, ChessPiece piece){
        board.placePiece(piece, new ChessPosition(column,row).toPosition());
    }

    public void initialSetup(){
        placeNewPiece('b', 6, new Rook(board, Color.WHITHE));
        placeNewPiece('e', 8, new King(board, Color.BLACK));
        placeNewPiece('e', 1, new King(board, Color.WHITHE));
    }
}