package model;

import model.herramientas.Direccion;

public interface Objeto {
	
	public boolean puedeMover(Hormiga h);
	
	public void mover(Direccion d);
}
