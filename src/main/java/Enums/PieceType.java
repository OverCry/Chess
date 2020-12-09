package Enums;

public enum PieceType {
    KING("K"),
    QUEEN("Q"),
    BISHOP("B"),
    KNIGHT("N"),
    ROOK("R"),
    PAWN("P");

    private String _representation;

    PieceType(String representation) { _representation = representation;}

    public String getRepresentation(){return _representation;}
}
