import java.util.ArrayList;

public class RandomSelector {
	public ArrayList<Float> proba;
	public float somme = 0;
	
	public RandomSelector() {
		this.proba = new ArrayList<Float>();
	}
	
	public void add(int i) {
		this.proba.add(Float.valueOf(i));
		this.somme += i;
	}
	
	public int randomChoice() {
		double rand = Math.random() * this.somme;
		float cumul = 0;
		for(int j = 0; j < this.proba.size(); j++) {
			cumul += this.proba.get(j).floatValue();
			if(rand < cumul) {
				return j;
			}
		}
		return this.proba.size() - 1;
	}
}
