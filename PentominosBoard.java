import java.awt.Color;
import java.util.ArrayList;

public class PentominosBoard implements Comparable<PentominosBoard>{
	
	public ArrayList<ArrayList<Color>> board;
	
	public ArrayList<Position> positions;
	
	public int nbrPlaced;
	
	
	/**
     * This data structure represents the pieces.  There are 12 pieces, and each piece can be rotated
     * and flipped over.  Some of these motions leave the piece unchanged because of symmetry.  Each distinct 
     * position of each piece has a line in this array.
     * Each line has 9 elements. 
     * The first element is the number of the piece, from 1 to 12. 
     * The remaining 8 elements describe the shape of the piece in the following peculiar way:  One square is assumed to be at position (0,0) in a grid; the square is chosen as the "top-left" square in the piece, in the sense that all the other squares are either to the right of this square in the same row, or are in lower rows.
     * The remaining 4 squares in the piece are encoded by 8 numbers that give the row and column of each of the remaining squares.
     * If the eight numbersnthat describe the piece are (a,b,c,d,e,f,g,h) then when the piece is placed on the board with the top-left square at position (r,c),
     * the remaining squares will be at positions (r+a,c+b), (r+c,c+d), (r+e,c+f), and (r+g,c+h).
     * This representation is used in the putPiece() and removePiece() methods. 
     */
    private  static final int[][] pieces = {
        { 1, 0,1,0,2,0,3,0,4 },  // Describes piece 1 (the "I" pentomino) in its horizontal orientation.
        { 1, 1,0,2,0,3,0,4,0 },  // Describes piece 1 (the "I" pentomino) in its vertical orientation.
        { 2, 1,-1,1,0,1,1,2,0 }, // The "X" pentomino, in its only orientation.
        { 3, 0,1,1,0,2,-1,2,0 }, // etc....
        { 3, 1,0,1,1,1,2,2,2 },
        { 3, 0,1,1,1,2,1,2,2 },
        { 3, 1,-2,1,-1,1,0,2,-2 },
        { 4, 1,0,2,0,2,1,2,2 }, //7
        { 4, 0,1,0,2,1,0,2,0 },
        { 4, 1,0,2,-2,2,-1,2,0 },
        { 4, 0,1,0,2,1,2,2,2 },
        { 5, 0,1,0,2,1,1,2,1 },//11
        { 5, 1,-2,1,-1,1,0,2,0 },
        { 5, 1,0,2,-1,2,0,2,1 },
        { 5, 1,0,1,1,1,2,2,0 },
        { 6, 1,0,1,1,2,1,2,2 },//15
        { 6, 1,-1,1,0,2,-2,2,-1 },
        { 6, 0,1,1,1,1,2,2,2 },
        { 6, 0,1,1,-1,1,0,2,-1 },
        { 7, 0,1,0,2,1,0,1,2 },//19
        { 7, 0,1,1,1,2,0,2,1 },
        { 7, 0,2,1,0,1,1,1,2 },
        { 7, 0,1,1,0,2,0,2,1 },
        { 8, 1,0,1,1,1,2,1,3 },//23
        { 8, 1,0,2,0,3,-1,3,0 },
        { 8, 0,1,0,2,0,3,1,3 },
        { 8, 0,1,1,0,2,0,3,0 },
        { 8, 0,1,1,1,2,1,3,1 },
        { 8, 0,1,0,2,0,3,1,0 },
        { 8, 1,0,2,0,3,0,3,1 },
        { 8, 1,-3,1,-2,1,-1,1,0 },
        { 9, 0,1,1,-2,1,-1,1,0 },//31
        { 9, 1,0,1,1,2,1,3,1 },
        { 9, 0,1,0,2,1,-1,1,0 },
        { 9, 1,0,2,0,2,1,3,1 },
        { 9, 0,1,1,1,1,2,1,3 },
        { 9, 1,0,2,-1,2,0,3,-1 },
        { 9, 0,1,0,2,1,2,1,3 },
        { 9, 1,-1,1,0,2,-1,3,-1 },
        { 10, 1,-2,1,-1,1,0,1,1 },//39
        { 10, 1,-1,1,0,2,0,3,0 },
        { 10, 0,1,0,2,0,3,1,1 },
        { 10, 1,0,2,0,2,1,3,0 },
        { 10, 0,1,0,2,0,3,1,2 },
        { 10, 1,0,1,1,2,0,3,0 },
        { 10, 1,-1,1,0,1,1,1,2 },
        { 10, 1,0,2,-1,2,0,3,0 },
        { 11, 1,-1,1,0,1,1,2,1 },//47
        { 11, 0,1,1,-1,1,0,2,0 },
        { 11, 1,0,1,1,1,2,2,1 },
        { 11, 1,0,1,1,2,-1,2,0 },
        { 11, 1,-2,1,-1,1,0,2,-1 },
        { 11, 0,1,1,1,1,2,2,1 },
        { 11, 1,-1,1,0,1,1,2,-1 },
        { 11, 1,-1,1,0,2,0,2,1 },
        { 12, 0,1,1,0,1,1,2,1 },//55
        { 12, 0,1,0,2,1,0,1,1 },
        { 12, 1,0,1,1,2,0,2,1 },
        { 12, 0,1,1,-1,1,0,1,1 },
        { 12, 0,1,1,0,1,1,1,2 },
        { 12, 1,-1,1,0,2,-1,2,0 },
        { 12, 0,1,0,2,1,1,1,2 },
        { 12, 0,1,1,0,1,1,2,0 } //62
    };
    
    private Color pieceColor[] = {  // the colors of pieces number 1 through 12; pieceColor[0] is not used.
            Color.BLACK,
            new Color(200,0,0),
            new Color(150,150,255),
            new Color(0,200,200),
            new Color(255,150,255),
            new Color(0,200,0),
            new Color(150,255,255),
            new Color(200,200,0),
            new Color(0,0,200),
            new Color(255,150,150),
            new Color(200,0,200),
            new Color(255,255,150),
            new Color(150,255,150)
    };
	
	public PentominosBoard() {
		this.board = new ArrayList<ArrayList<Color>>(8 * 8);
		this.positions = new ArrayList<Position>();
		for(int i = 0; i < 63; i++) {
			this.positions.add(null);
		}
		this.setUpRandomBoard();
		this.nbrPlaced = 0;
	}
	
	public ArrayList<Color> getBoard(int i, int j){
		return board.get(8 * i + j);
	}
	
	public boolean removeColorBoard(int i, int j, Color c) {
		ArrayList<Color> square = this.getBoard(i, j);
		for(int k = 0; k < square.size(); k++) {
			if(square.get(k).equals(c)) {
				square.remove(k);
				return true;
			}
		}
		return false;
	}
	
	public void clear() {
		for(int i = 0; i < 8 * 8; i++) {
			this.board.add(new ArrayList<Color>());
		}
	}
	
	public void clearPositions() {
		for(int i = 0; i < 63; i++) {
			this.positions.set(i, null);
		}
	}
	
	public void clearBoard() {
		this.clear();
		this.clearPositions();
		this.nbrPlaced = 0;
	}
	
	void setUpRandomBoard() { // Set up a random board, that is, select at random the squares that will be left empty
        this.clearBoard();
        //int x, y;
        //int choice = (int)(3 * Math.random());
        /*switch (choice) {
        case 0: // totally random
            for (int i=0; i < 4; i ++) {
                do {
                    x = (int)(8 * Math.random());
                    y = (int)(8 * Math.random());
                } while (this.getBoard(y,x).size() > 0);
                this.getBoard(y,x).add(Color.BLACK);
            }
            break;
        case 1: // Symmetric random
            x = (int)(8 * Math.random());
            y = (int)(8 * Math.random());
            this.getBoard(y,x).add(Color.BLACK);
            this.getBoard(y,7-x).add(Color.BLACK);
            this.getBoard(7-y,x).add(Color.BLACK);
            this.getBoard(7-y,7-x).add(Color.BLACK);
            break;
        default: // random block
            x = (int)(7 * Math.random());
            y = (int)(7 * Math.random());
            for (int r = 0; r < 2; r++)
                for (int c = 0; c < 2; c++) {
                    this.getBoard(y + r,x + c).add(Color.BLACK);
                }
            break;
        }*/
    }
	
	boolean putPiece(int p, int row, int col) {  // try to place a piece on the board, return true if it fits
		for (int i = 1; i < 8; i += 2) {
            if (row+pieces[p][i] < 0 || row+pieces[p][i] >= 8 || col+pieces[p][i+1] < 0 || col+pieces[p][i+1] >= 8)
                return false;
        }
        int pos = this.orientation(pieces[p][0]);
        if(pos != -1) {
        	this.removePiece(pos);
        }
        this.getBoard(row,col).add(pieceColor[pieces[p][0]]);
        for (int i = 1; i < 8; i += 2)
            this.getBoard(row + pieces[p][i], col + pieces[p][i+1]).add(pieceColor[pieces[p][0]]);
        Position posPair = new Position(row,col);
        this.positions.set(p, posPair);
        this.nbrPlaced++;
        return true;
    }
	
	boolean putPiece(int p, Position pos) {
		return this.putPiece(p, pos.ligne, pos.colonne);
	}
	
	public boolean removePiece(int p) { // Remove piece p from the board, at position (row,col)
        if(this.positions.get(p) == null)
        	return false;
        int row = this.positions.get(p).ligne, col = this.positions.get(p).colonne;
		this.removeColorBoard(row,col,pieceColor[pieces[p][0]]);
        for (int i = 1; i < 9; i += 2) {
            this.removeColorBoard(row + pieces[p][i], col + pieces[p][i+1], pieceColor[pieces[p][0]]);
        }
        this.positions.set(p,null);
        this.nbrPlaced--;
        return true;
    }
	
	public int orientation(int c) {
		switch(c) {
		case 1: for(int i = 0; i < 2; i++) {
			if(this.positions.get(i) != null)
				return i;
		} break;
		case 2: if(this.positions.get(2) != null)
			return 2; break;
		case 3: for(int i = 3; i < 7; i++) {
			if(this.positions.get(i) != null)
				return i;
		} break;
		case 4: for(int i = 7; i < 11; i++) {
			if(this.positions.get(i) != null)
				return i;
		} break;
		case 5: for(int i = 11; i < 15; i++) {
			if(this.positions.get(i) != null)
				return i;
		} break;
		case 6: for(int i = 15; i < 19; i++) {
			if(this.positions.get(i) != null)
				return i;
		} break;
		case 7: for(int i = 19; i < 23; i++) {
			if(this.positions.get(i) != null)
				return i;
		} break;
		case 8: for(int i = 23; i < 31; i++) {
			if(this.positions.get(i) != null)
				return i;
		} break;
		case 9: for(int i = 31; i < 39; i++) {
			if(this.positions.get(i) != null)
				return i;
		} break;
		case 10: for(int i = 39; i < 47; i++) {
			if(this.positions.get(i) != null)
				return i;
		} break;
		case 11: for(int i = 47; i < 55; i++) {
			if(this.positions.get(i) != null)
				return i;
		} break;
		case 12: for(int i = 55; i < 63; i++) {
			if(this.positions.get(i) != null)
				return i;
		}
	}
		/*for(int i = 0; i < 63; i++) {
			if(pieces[i][0] == c && this.positions.get(i) != null)
				return i;
		}*/
		return -1;
	}
	
	public Position position(int c) {
		int p = this.orientation(c);
		return this.positions.get(p);
	}
	
	public boolean isPlacedHere(int i, int j, int c) {
		ArrayList<Color> liste = this.getBoard(i, j);
		for(int k = 0; k < liste.size(); k++) {
			if(liste.get(k).equals(pieceColor[c]))
				return true;
		}
		return false;
	}
	
	public static int color(int piece) {
		return pieces[piece][0];
	}
	
	public int nbrConflict(int c) {
		int pos = this.orientation(c);
		if(pos == -1) {
			return -1;
		}
		else {
			Position position = this.positions.get(pos);
			int row = position.ligne, col = position.colonne, diff = 0;
			diff += this.getBoard(row,col).size() - 1;
	        for (int i = 1; i < 9; i += 2) {
	            diff += this.getBoard(row + pieces[pos][i], col + pieces[pos][i+1]).size() - 1;
	        }
	        return diff;
		}
	}
	
	public int nbrConflict() {
		int sum = 0;
		for(int i = 1; i < 13; i++) {
			sum += this.nbrConflict(i);
		}
		return sum;
	}
	
	public int nbrFreePlaces(){
		int nbr = 0;
		for(int i = 0; i < this.board.size(); i++) {
			if(this.board.get(i).size() == 0)
				nbr++;
		}
		return nbr;
	}
	
	public int nbrFilledPlaces() {
		return 64 - this.nbrFreePlaces();
	}
	
	public PentominosBoard copy() {
		PentominosBoard copy = new PentominosBoard();
		for(int i = 0; i < 8 * 8; i++) {
			copy.board.set(i, (ArrayList<Color>)this.board.get(i).clone());
		}
		for(int i = 0; i < 63; i++) {
			if(this.positions.get(i) != null)
			copy.positions.set(i, this.positions.get(i).clone());
		}
		copy.nbrPlaced = this.nbrPlaced;
		return copy;
	}
	
	public String toString() {
		String s = "";
		for(int i = 0; i < Math.sqrt(this.board.size()); i++) {
			for(int j = 0; j < Math.sqrt(this.board.size()); j++) {
				ArrayList<Color> alc = this.getBoard(i, j);
				if(alc.size() == 0)
					s += "_";
				else {
					if(alc.size() > 2)
						s += "X";
					else {
						Color couleur = alc.get(0);
						if(couleur.equals(Color.black))
							s += "D";
						else {
							if(couleur.equals(new Color(200,0,0)))
								s += "a";
							else {
								if(couleur.equals(new Color(150,150,255)))
									s += "b";
								else {
									if(couleur.equals(new Color(0,200,200)))
										s += "c";
									else {
										if(couleur.equals(new Color(255,150,255)))
											s += "d";
										else {
											if(couleur.equals(new Color(0,200,0)))
												s += "e";
											else {
												if(couleur.equals(new Color(150,255,255)))
													s += "f";
												else {
													if(couleur.equals(new Color(200,200,0)))
														s += "g";
													else {
														if(couleur.equals(new Color(0,0,200)))
															s += "h";
														else {
															if(couleur.equals(new Color(255,150,150)))
																s += "i";
															else {
																if(couleur.equals(new Color(200,0,200)))
																	s += "j";
																else {
																	if(couleur.equals(new Color(255,255,150)))
																		s += "k";
																	else {
																		if(couleur.equals(new Color(150,255,150)))
																			s += "l";
																	}
																	
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
			s += "\n";
		}
		return s;
	}
	
	public int compareTo(PentominosBoard pb) {
		if(this == pb || this.samePositions(pb))
			return 0;
		int nbrConflict1 = this.nbrFreePlaces() /*this.nbrConflict()*/, nbrConflict2 = pb.nbrFreePlaces()/*pb.nbrConflict()*/;
		if(nbrConflict1 > nbrConflict2)
			return -1;
		else {
			if(nbrConflict1 < nbrConflict2)
				return 1;
			else {
				return this.positions.toString().compareTo(pb.positions.toString());
			}			
		}
	}
	
	public boolean samePositions(PentominosBoard pb) {
		boolean isSame = true;
		for(int k = 0; k < 63 && isSame; k++) {
			Position position1 = this.positions.get(k), position2 = pb.positions.get(k);
			if(position1 != null || position2 != null)
				if(position1 != null && position2 != null)
					isSame = position1.ligne == position2.ligne && position1.colonne == position2.colonne;
				else
					isSame = false;
		}
		return isSame;
	}
	
}
