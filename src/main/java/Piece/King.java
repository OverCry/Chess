package Piece;

import Enums.Side;
import Interfaces.ICoordinate;

public class King extends Piece{
    private String _type = "K";
    private boolean _moved = false;

    public King(Side side, int row, int column) {
        super(side, row, column);
    }

    public boolean legal(Side[][] locations, ICoordinate endPosition, ICoordinate lastMoveOrigin, ICoordinate lastMoveFinal, String lastType){

        int smallRow=getRow();
        int smallColumn=getColumn();
        int bigRow=endPosition.getRow();
        int bigColumn= endPosition.getColumn();

        int rowDiff = Math.abs(smallRow-bigRow);
        int colDiff = Math.abs(smallColumn-bigColumn);


        // check if castleable
        if (_moved){
            if (bigRow==0 || bigRow==7){
                //short castle
                if (bigColumn==6){
                    if (locations[bigRow][5]!=null || locations[bigRow][6]!=null  ){
                        return false;
                    }
                    //long castle
                } else if (bigColumn==2){

                } else {
                    return false;
                }
            }
        }
        //TODO if wanted, check if this move will result in a check, otherwise, be happy
        if (rowDiff>1 || colDiff>1 ){
            return false;
        }
        System.out.println("KING");
        _moved=true;
        return true;
    }

    @Override
    public String getType(){
        return _type;
    }
}
