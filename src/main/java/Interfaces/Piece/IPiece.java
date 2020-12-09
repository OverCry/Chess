package Interfaces.Piece;

import Enums.Side;
import Interfaces.ICoordinate;

public interface IPiece {

    public Side getSide();

    public int getRow();

    public void setRow(int row);

    public int getColumn();

    public void setColumn(int column);

    public ICoordinate getPosition();

    public void setPosition(ICoordinate coordinate);

    public String getType();

    public boolean getMoved();

    public void setMoved();

}
