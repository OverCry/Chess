package Interfaces.Piece;

import Enums.Side;

public interface IPiece {

    public Side getSide();

    public int getRow();

    public void setRow(int row);

    public int getColumn();

    public void setColumn(int column);

    public String getType();
}
