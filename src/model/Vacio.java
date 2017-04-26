package model;

import model.herramientas.Direccion;

public class Vacio implements Objeto {

	@Override
	public boolean puedeMover(Hormiga h) {
		return true;
	}

	@Override
	public void mover(Direccion d) {}

	public String toString(){
		return "     ";
	}
}
