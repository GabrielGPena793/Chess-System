package com.uldemy.application;


import com.uldemy.chess.ChessMatch;

public class Program {
    public static void main(String[] args) {
        ChessMatch chessMatch = new ChessMatch();

        UI.printBoard(chessMatch.getPieces());

    }
}
