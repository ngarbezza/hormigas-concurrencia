package ar.edu.unq.pconc.hormigas.model;

import ar.edu.unq.pconc.hormigas.gui.Actualizador;
import ar.edu.unq.pconc.hormigas.gui.VentanaPrincipal;

public class Main {
	public static void main(String[] args) {
		VentanaPrincipal vp = new VentanaPrincipal();
		Actualizador.getInstance().setVentanaPrincipal(vp);
	}
}
