package Piece;

import Enums.Side;
import Interfaces.ICoordinate;

public class Rook extends Piece  {
    private String _type = "R";

    public Rook(Side side, int row, int column) {
        super(side, row, column);
    }

    //TODO incomplete
    public boolean legal(Side[][] locations, ICoordinate endPosition, ICoordinate lastMoveOrigin, ICoordinate lastMoveFinal){
        //check if same row or same column
//        if (endPosition.getRow()!=getRow() && endPosition.getColumn()!=getColumn()){
//            return false;
//        }
        //check if anything is blocking between it
        int smallRow=getRow();
        int smallColumn=getColumn();
        int bigRow=endPosition.getRow();
        int bigColumn= endPosition.getColumn();

        if (endPosition.getRow()==getRow()){
            //same row
            //check which column is larger
//            if (endPosition.getColumn()<getColumn()){
//                //swap around
//                int dummy = bigColumn;
//                bigColumn = smallColumn;
//                smallColumn = dummy;
//            }

//            for (int column = (e))



        } else if (endPosition.getColumn()==getColumn()){
            if (endPosition.getRow()<getRow()){
                //swap around
                int dummy = bigRow;
                bigRow = smallRow;
                smallRow = dummy;
            }
        }


        for (int row =smallRow;row<bigRow;row++){
            for (int column =smallColumn;column<bigColumn;column++){

            }
        }

        System.out.println("ROOK");

        return true;
    }

    @Override
    public String getType(){
        return _type;
    }
}
