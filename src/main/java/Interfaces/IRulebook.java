package Interfaces;

import Enums.PieceType;
import Enums.Side;
import Interfaces.Piece.IPiece;
import Piece.Piece;

import java.util.List;
import java.util.Map;

public interface IRulebook {

    public boolean legal(IPiece movingPiece, ICoordinate endPosition, ICoordinate _lastOriginalPosition, ICoordinate _lastFinalPosition, String[][] _representation, Map<PieceType, List<Piece>>  _pieceLocation);
}
