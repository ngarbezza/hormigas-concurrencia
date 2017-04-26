package ar.edu.unq.pconc.hormigas.model;

import ar.edu.unq.pconc.hormigas.model.herramientas.Direccion;

public interface Objeto {
	
	boolean puedeMover(Hormiga h);
	
	void mover(Direccion d);
}
