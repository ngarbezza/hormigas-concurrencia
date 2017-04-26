package ar.edu.unq.pconc.hormigas.model;

import ar.edu.unq.pconc.hormigas.model.herramientas.Direccion;

public interface Objeto {
	
	public boolean puedeMover(Hormiga h);
	
	public void mover(Direccion d);
}
