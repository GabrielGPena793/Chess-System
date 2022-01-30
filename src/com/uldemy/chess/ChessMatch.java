package com.uldemy.chess;

import com.uldemy.boardgame.Board;
import com.uldemy.boardgame.Piece;
import com.uldemy.boardgame.Position;
import com.uldemy.chess.pieces.King;
import com.uldemy.chess.pieces.Rook;

public class ChessMatch {

    private Board board;

    public ChessMatch() {
        board = new Board(8,8);
        initialSetup();
    }

    //cria uma matriz com o tamanho do tabuleiro, colocando uma peça em cada posição do tabuleiro caso haja uma peça
    // na matriz de peças na mesma posição do tabuleiro;
    public ChessPiece[][] getPieces(){
        ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColmns()];
        for (int i = 0; i < board.getRows();i++){
            for (int j = 0; j < board.getColmns();j++){
                mat[i][j] = (ChessPiece) board.piece(i,j);
            }
        }
        return mat;
    }

    //realiza o movimento da peça;
    public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition){
        Position source = sourcePosition.toPosition();
        Position target = targetPosition.toPosition();
        validateSourcePosition(source);
        validadeTargetPosition(source, target);
        Piece capturedPiece = makeMove(source, target);
        return (ChessPiece) capturedPiece;
    }

    //remove a peça do local atual, coloca ela no local desejado e retorna a peça inimiga do local alvo, removendo ela
    // da posição;
    private Piece makeMove(Position source, Position target){
        Piece p = board.removePiece(source);
        Piece capturedPiece = board.removePiece(target);
        board.placePiece(p, target);
        return capturedPiece;
    }

    //verifica se existe uma peça no local desejado e se a peça pode ir para aquela posição;
    private void validateSourcePosition(Position position){
        if (!board.thereIsAPiece(position)){
            throw new ChessExeption("There is no piece on source position");
        }
        if (!board.piece(position).isThereAnyPossibleMove()){
            throw new ChessExeption("There is no possible moves for the chosen piece");
        }
    }

    private void validadeTargetPosition(Position source, Position target){
        if (!board.piece(source).possibleMove(target)){
            throw new ChessExeption("The chosen piece can't move to target position");
        }
    }

    //coloca uma peça na posição válida desejada, passando a peça e a posição
    private void placeNewPiece(char column, int row, ChessPiece piece){
        board.placePiece(piece, new ChessPosition(column,row).toPosition());
    }

    public void initialSetup(){
        placeNewPiece('c', 1, new Rook(board, Color.WHITE));
        placeNewPiece('c', 2, new Rook(board, Color.WHITE));
        placeNewPiece('d', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new Rook(board, Color.WHITE));
        placeNewPiece('d', 1, new King(board, Color.WHITE));

        placeNewPiece('c', 7, new Rook(board, Color.BLACK));
        placeNewPiece('c', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 8, new King(board, Color.BLACK));
    }
}
