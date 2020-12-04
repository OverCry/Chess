package Piece;

import Enums.Side;
import Interfaces.ICoordinate;

public class Rook extends Piece  {
    private String _type = "R";
    private boolean _moved = false;


    public Rook(Side side, int row, int column) {
        super(side, row, column);
    }

    public boolean legal(Side[][] locations, ICoordinate endPosition, ICoordinate lastMoveOrigin, ICoordinate lastMoveFinal, String lastType){
        //check if anything is blocking between it
        int smallRow=getRow();
        int smallColumn=getColumn();
        int bigRow=endPosition.getRow();
        int bigColumn= endPosition.getColumn();

        if (endPosition.getRow()==getRow()){
            //same row
            //check which column is larger
            if (endPosition.getColumn()<getColumn()){
                //swap around
                int dummy = bigColumn;
                bigColumn = smallColumn;
                smallColumn = dummy;
            }

            for (int column = smallColumn +1 ; column<bigColumn;column++){
                if (locations[getRow()][column]!=null){
                    return false;
                }
            }


        } else if (endPosition.getColumn()==getColumn()){
            if (endPosition.getRow()<getRow()){
                //swap around
                int dummy = bigRow;
                bigRow = smallRow;
                smallRow = dummy;
            }

            for (int row = smallRow +1 ; row<bigRow;row++){
                if (locations[row][getColumn()]!=null){
                    return false;
                }
            }
        }

        System.out.println("ROOK");
        _moved=true;


        return true;
    }

    @Override
    public String getType(){
        return _type;
    }
}
