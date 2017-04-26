package model;

import java.util.LinkedList;
import java.util.List;

public class Jugador {

	private List<Hormiga> hormigas = new LinkedList<Hormiga>();
	
	public List<Hormiga> getHormigas() {
		return hormigas;
	}

	public void agregarHormiga(Hormiga h) {
		this.getHormigas().add(h);
	}
}
