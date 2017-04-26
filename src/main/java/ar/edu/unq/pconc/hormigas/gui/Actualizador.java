package ar.edu.unq.pconc.hormigas.gui;

import ar.edu.unq.pconc.hormigas.model.Objeto;
import ar.edu.unq.pconc.hormigas.model.herramientas.Color;

public class Actualizador {
	
	private static Actualizador instance;
	
	public static Actualizador getInstance() {
		if (instance == null)
			instance = new Actualizador();
		return instance;
	}
	
	private VentanaPrincipal ventanaPrincipal;
	
	private Actualizador() {}
	
	public void setVentanaPrincipal(VentanaPrincipal vp) {
		this.ventanaPrincipal = vp;
	}
	
	public synchronized void actualizar(int x, int y, Objeto o) {
		if (ventanaPrincipal != null)
			this.ventanaPrincipal.mostrarObjeto(x, y, o);
	}

	public void notificarGanador(Color color) {
		if (ventanaPrincipal != null)
			this.ventanaPrincipal.notificarGanador(color);		
	}
}
