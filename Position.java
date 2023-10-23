
public class Position{
	public int ligne, colonne;
	
	public Position(int row, int column) {
		this.ligne = row;
		this.colonne = column;
	}
	
	public Position clone() {
		return new Position(this.ligne,this.colonne);
	}
	
	public String toString() {
		return "[ " + this.ligne + ", " + this.colonne + " ]";
	}
}
