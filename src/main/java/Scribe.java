import Enums.PieceType;
import Enums.Side;
import Interfaces.IPiece;
import Interfaces.IScribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Scribe implements IScribe {
    private static Scribe instance = null;
    private List<String> _whiteMoves = new ArrayList<>();

    private List<String> _blackMoves = new ArrayList<>();

    @Override
    public void write(IPiece piece, PieceType[][] pieceLocation, Map<Side, List<IPiece>> location, boolean take, String promoType, int startColumnInt, String endMove) {
        String input= "";

        //get piece type
        PieceType type =piece.getType();
        if (!type.equals(PieceType.PAWN)) {
            input+=type.getRepresentation();
        }

        //check if clarification is necessary

        //check if a piece is taken
        if (take){
            if (type.equals(PieceType.PAWN)){
                //original location
                input+=(char)(65+startColumnInt);
            }
            input+="x";
        }

        //input end position
        input+=""+(char)(65+piece.getColumn())+ (piece.getRow()+1);

        //check if promoted
        if (!promoType.equals("")){
            input+="="+promoType;
        }

        //check if check or mate
        if (endMove.length()==3){
            input+=String.valueOf(endMove.charAt(2));
        }

        input = input.toLowerCase();
        System.out.println(input);

        //add to list
        if (piece.getSide().equals(Side.WHITE)){
            _whiteMoves.add(input);
        } else {
            _blackMoves.add(input);
        }

    }

    public void show(){
        System.out.println("\nMoves of this game ");
        System.out.print("\tWhite     Black");

        int bLength = _blackMoves.size();
        int wLength = _whiteMoves.size();
        for (int num=0;num<wLength;num++){

            System.out.print("\n"+ (num+1<10 ? "0":"")+(num+1) +": ");
            System.out.print(_whiteMoves.get(num));

            //create gap
            for (int space=0;space<10-_whiteMoves.get(num).length();space++){
                System.out.print(" ");
            }

            if (num!=bLength) {
                System.out.print(_blackMoves.get(num));
            }
        }
    }

    public static Scribe getInstance() {
        if (instance == null) {
            instance = new Scribe();
        }
        return instance;
    }


}
