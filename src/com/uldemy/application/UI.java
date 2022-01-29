package com.uldemy.application;

import com.uldemy.chess.ChessPiece;

public class UI {

    //printa na tela o jogo de xadrez.
    public  static  void printBoard(ChessPiece[][] pieces){
        for (int i = 0; i < pieces.length;i++){
            System.out.print((8 - i) + " ");
            for (int j = 0; j < pieces.length;j++){
                printPiece(pieces[i][j]);
            }
            System.out.println();
        }
        System.out.println("  a b c d e f g h");
    }

    //verifica se tem peças e printa caso tenha a peça se não retonar um " - " ;
    private static void printPiece(ChessPiece piece){
        if (piece == null){
            System.out.print("-");
        }else {
            System.out.print(piece);
        }
        System.out.print(" ");
    }
}
