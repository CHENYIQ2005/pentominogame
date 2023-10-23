
public class Couple {
	public PentominosBoard mother, father;
	
	public Couple(PentominosBoard m, PentominosBoard f) {
		this.mother = m.copy();
		this.father = f.copy();
	}
}
