package ar.edu.unq.pconc.hormigas.model.herramientas;

public class Par {
	private int x, y;

	public Par(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public Par aug(Par p){
		return new Par(x+p.getX(), y+p.getY());
	}
	
	public Par dim(Par p){
		return new Par(x-p.getX(), y-p.getY());
	}
}
