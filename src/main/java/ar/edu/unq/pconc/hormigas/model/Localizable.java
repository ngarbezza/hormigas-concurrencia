package ar.edu.unq.pconc.hormigas.model;

import ar.edu.unq.pconc.hormigas.model.herramientas.Color;

public abstract class Localizable implements Objeto {

	protected Color color;
	protected int fila, columna;

	public Localizable() {
		super();
	}

	public int getFila() {
		return fila;
	}

	public int getColumna() {
		return columna;
	}

	public void setFila(int fila) {
		this.fila = fila;
	}

	public void setColumna(int columna) {
		this.columna = columna;
	}

	public Color getColor() {
		return color;
	}
}