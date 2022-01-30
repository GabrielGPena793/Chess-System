package com.uldemy.chess;

import com.uldemy.boardgame.Board;
import com.uldemy.boardgame.Piece;
import com.uldemy.boardgame.Position;
import com.uldemy.chess.pieces.King;
import com.uldemy.chess.pieces.Rook;

public class ChessMatch {

    private int turn;
    private Color currentPlayer;
    private Board board;

    public ChessMatch() {
        board = new Board(8,8);
        turn = 1;
        currentPlayer = Color.WHITE;
        initialSetup();
    }

    public int getTurn() {
        return turn;
    }

    public Color getCurrentPlayer(){
        return currentPlayer;
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

    //marca os posiveis movimentos da peça que irá se mover;
    public boolean[][] possibleMoves(ChessPosition sourcePosition){
        Position position = sourcePosition.toPosition();
        validateSourcePosition(position);
        return board.piece(position).possibleMoves();
    }

    //realiza o movimento da peça;
    public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition){
        Position source = sourcePosition.toPosition();
        Position target = targetPosition.toPosition();
        validateSourcePosition(source);
        validadeTargetPosition(source, target);
        Piece capturedPiece = makeMove(source, target);
        nextTurn();
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

    //validações do jogo;
    private void validateSourcePosition(Position position){
        //verifica se tem uma peça na posição selecionada;
        if (!board.thereIsAPiece(position)){
            throw new ChessExeption("There is no piece on source position");
        }
        //verifica se a peça é a do jogador da vez;
        if (currentPlayer != ((ChessPiece) board.piece(position)).getColor()){
            throw new ChessExeption("The chosen piece is not yours");
        }
        //verifica se a peça pode se mover para algum lugar
        if (!board.piece(position).isThereAnyPossibleMove()){
            throw new ChessExeption("There is no possible moves for the chosen piece");
        }
    }

    //verifica se a peça pode se mover para a posição alvo!
    private void validadeTargetPosition(Position source, Position target){
        if (!board.piece(source).possibleMove(target)){
            throw new ChessExeption("The chosen piece can't move to target position");
        }
    }

    private void nextTurn(){
        turn++;
        this.currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK :  Color.BLACK;
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
