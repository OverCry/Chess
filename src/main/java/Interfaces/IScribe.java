package Interfaces;

import Enums.PieceType;
import Enums.Side;

import java.util.List;
import java.util.Map;

public interface IScribe {

    public void write(IPiece piece, PieceType[][] pieceLocation, Map<Side, List<IPiece>> location, boolean taken, String promo, int startColumnInt, String endString );

    public void show();
}
