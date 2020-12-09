package Piece;

import Enums.PieceType;
import Enums.Side;
import Interfaces.ICoordinate;

public class Queen extends Piece {
    private PieceType _type = PieceType.QUEEN;

    public Queen(Side side, int row, int column) {
        super(side, row, column);
    }

    @Override
    public PieceType getType() {
        return _type;
    }
}
