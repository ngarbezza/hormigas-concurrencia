package model;

import model.herramientas.Direccion;

public class Obstaculo implements Objeto {

	@Override
	public void mover(Direccion d) {}

	@Override
	public boolean puedeMover(Hormiga h) {
		return false;
	}
	
	@Override
	public String toString() {
		return "  X  ";
	}
}
