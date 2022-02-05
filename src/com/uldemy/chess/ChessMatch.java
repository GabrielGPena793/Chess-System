package com.uldemy.chess;

import com.uldemy.boardgame.Board;
import com.uldemy.boardgame.Piece;
import com.uldemy.boardgame.Position;
import com.uldemy.chess.pieces.King;
import com.uldemy.chess.pieces.Pawn;
import com.uldemy.chess.pieces.Rook;

import java.util.ArrayList;
import java.util.List;

public class ChessMatch {

    private int turn;
    private Color currentPlayer;
    private Board board;
    private boolean check;
    private boolean checkMate;

    private List<Piece> piecesOnTheBoard = new ArrayList<>();
    private List<Piece> capturedPieces = new ArrayList<>();

    public ChessMatch() {
        board = new Board(8,8);
        turn = 1;
        currentPlayer = Color.WHITE;
        initialSetup();
    }

    public boolean isCheck() {
        return check;
    }

    public boolean isCheckMate() {
        return checkMate;
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

        if (testCheck(currentPlayer)){
            undoMove(source,target,capturedPiece);
            throw new ChessExeption("You can't put yourself in check");
        }
        check = testCheck(opponent(currentPlayer));

        if (testCheckMate(opponent(currentPlayer))){
            checkMate = true;
        }
        else {
            nextTurn();
        }
        return (ChessPiece) capturedPiece;
    }

    //remove a peça do local atual, coloca ela no local desejado e retorna a peça inimiga do local alvo, removendo ela
    // da posição;
    private Piece makeMove(Position source, Position target){
        ChessPiece p = (ChessPiece)board.removePiece(source);
        p.increaseMoveCount();
        Piece capturedPiece = board.removePiece(target);
        board.placePiece(p, target);

        if (capturedPiece != null){
            piecesOnTheBoard.remove(capturedPiece);
            capturedPieces.add(capturedPiece);
        }
        return capturedPiece;
    }

    //desfaz um movimento caso tente se movimentar para uma posição que irá ficar em check
    private void undoMove(Position source,Position target, Piece caputredPiece){
        ChessPiece p = (ChessPiece) board.removePiece(target);
        p.decreaseMoveCount();
        board.placePiece(p, source);

        if (caputredPiece != null){
            board.placePiece(caputredPiece, target);
            capturedPieces.remove(caputredPiece);
            piecesOnTheBoard.add(caputredPiece);
        }
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

    //passa para o proximo turno e muda de jogador
    private void nextTurn(){
        turn++;
        currentPlayer = getCurrentPlayer() == Color.WHITE ? Color.BLACK : Color.WHITE;
    }

    //retorna uma determinada cor
    private Color opponent(Color color){
        return color == Color.WHITE ? Color.BLACK : Color.WHITE;
    }

    //localiza o King de uma determinada cor
    private ChessPiece king(Color color){
        List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color).toList();
        for (Piece p: list) {
            if (p instanceof King){
                return (ChessPiece) p;
            }
        }
        throw new IllegalStateException("There is no " + color + " king on the board");
    }

    //verifica se alguma peça inimiga pode se movimentar para casa do rei, (ocorrendo um check)
    private boolean testCheck(Color color){
        Position kingPosition = king(color).getChessPosition().toPosition();
        List<Piece> oppoentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == opponent(color)).toList();
        for (Piece p: oppoentPieces){
            boolean[][] mat = p.possibleMoves();
            if (mat[kingPosition.getRow()][kingPosition.getColumn()]){
                return true;
            }
        }
        return false;
    }

    //verifica se deu check mate
    private boolean testCheckMate(Color color){
        if (!testCheck(color)){
            return false;
        }
        List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color).toList();
        for (Piece p: list){
            boolean[][] mat = p.possibleMoves();

            for (int i=0; i < board.getRows(); i++){
                for (int j=0; j < board.getColmns(); j++){
                    if (mat[i][j]){
                        Position source = ( (ChessPiece) p ).getChessPosition().toPosition();
                        Position target = new Position(i,j);
                        Piece capturedPiece = makeMove(source,target);
                        boolean testCheck = testCheck(color);
                        undoMove(source,target, capturedPiece);

                        if (!testCheck){
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    //coloca uma peça na posição válida desejada, passando a peça e a posição
    private void placeNewPiece(char column, int row, ChessPiece piece){
        board.placePiece(piece, new ChessPosition(column,row).toPosition());
        piecesOnTheBoard.add(piece);
    }

    public void initialSetup(){
        placeNewPiece('a', 1, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new King(board, Color.WHITE));
        placeNewPiece('h', 1, new Rook(board, Color.WHITE));
        placeNewPiece('a', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('b', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('c', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('d', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('e', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('f', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('g', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('h', 2, new Pawn(board, Color.WHITE));

        placeNewPiece('a', 8, new Rook(board, Color.BLACK));
        placeNewPiece('e', 8, new King(board, Color.BLACK));
        placeNewPiece('h', 8, new Rook(board, Color.BLACK));
        placeNewPiece('a', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('b', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('c', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('d', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('e', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('f', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('g', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('h', 7, new Pawn(board, Color.BLACK));
    }
}
