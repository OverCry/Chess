package Interfaces;

import Enums.PieceType;
import Enums.Side;
import Piece.Support.Coordinate;

import java.util.List;
import java.util.Map;

public interface IRulebook {

    public boolean legal(IPiece movingPiece, ICoordinate endPosition, ICoordinate _lastOriginalPosition, ICoordinate _lastFinalPosition, PieceType[][] _representation, Map<Side, List<IPiece>>  _pieceLocation);

    public boolean isEnPassant();

    public Coordinate isCastle();

    public void resetCastle();
}
