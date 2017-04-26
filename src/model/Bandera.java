package model;

import model.herramientas.Color;
import model.herramientas.Direccion;

public class Bandera extends Localizable {
	public Bandera(Color color){
		this.color = color;
	}

	@Override
	public void mover(Direccion d) {}

	/**
	 * Si se llama a este metodo,
	 * veo si la hormiga que se quiere mover es del mismo color.
	 * De ser asi, se termino el juego
	 */
	@Override
	public boolean puedeMover(Hormiga h) {
		if (h.getColor() == color)
			Juego.getInstance().terminoElJuego(color);
		return false;
	}
	
	@Override
	public String toString() {
		return " |"+color+"| ";
	}
}
