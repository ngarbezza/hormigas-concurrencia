package model;

import gui.Actualizador;
import gui.VentanaPrincipal;

public class Main {
	public static void main(String[] args) {
		VentanaPrincipal vp = new VentanaPrincipal();
		Actualizador.getInstance().setVentanaPrincipal(vp);
	}
}
