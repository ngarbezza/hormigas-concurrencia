package ar.edu.unq.pconc.hormigas.model;

import ar.edu.unq.pconc.hormigas.gui.Actualizador;
import ar.edu.unq.pconc.hormigas.model.herramientas.Color;
import ar.edu.unq.pconc.hormigas.model.herramientas.Direccion;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Hormiga extends Localizable implements Objeto, Runnable {

	private static boolean seImprimio = false;
	private Direccion direccionActual;
	private boolean turnoValido;
	private boolean direccionSeteadaPorJugador;

	// para que todas las hormigas muevan de a un paso por vez
	private CyclicBarrier barrier;

	public Hormiga(CyclicBarrier barrier, Color c) {
		this.direccionActual = Direccion.getDireccionAleatoria();
		this.barrier = barrier;
		this.color = c;
		this.turnoValido = true;
		this.direccionSeteadaPorJugador = false;
	}

	public Direccion getDireccion() {
		return direccionActual;
	}

	/**
	 * Metodo usado por el jugador para setear la direccion de la hormiga
	 * @param direccionActual
	 */
	public void setDireccion(Direccion direccionActual) {
		this.cambiarDireccion(direccionActual);
		this.direccionSeteadaPorJugador = true;
	}

	@Override
	public boolean puedeMover(Hormiga h) {
		Direccion d = h.getDireccion();
		if (Juego.getInstance().getTablero().movimientoDentroDelLimite(this,d)) {
				//si a donde quiere mover esta adentro del tablero
			Objeto objeto = Juego.getInstance().getTablero().get(	//objeto que esta en donde me moveria
					fila + d.getMovimiento().getX(),
					columna + d.getMovimiento().getY());
			if (objeto instanceof Bandera && ((Bandera) objeto).getColor() == getColor())
					//si es bandera y es del mismo color, el juego termina
				Juego.getInstance().terminoElJuego(color);
			return objeto instanceof Vacio;
		}
		return false;
	}

	/**
	 * Se mueve a la celda siguiente. Tambien se setea la nueva fila y columna
	 */
	@Override
	public void mover(Direccion d) {
		int nuevaFila = getFila() + d.getMovimiento().getX();
		int nuevaColumna = getColumna() + d.getMovimiento().getY();
		Juego.getInstance().getTablero().put(nuevaFila, nuevaColumna, this);
		this.setFila(nuevaFila);
		this.setColumna(nuevaColumna);
		
		// notifico de cambios a la UI
		Actualizador.getInstance().actualizar(nuevaFila, nuevaColumna, this);
	}

	public void permitirMover() {
		this.turnoValido = true;
	}

	public void noPuedeMover() {
		this.turnoValido = false;
	}

	public boolean esTurnoValido() {
		return turnoValido && !Juego.getInstance().termino();
	}

	@Override
	public void run() {
		Juego juego = Juego.getInstance();
		while (!juego.termino()) {

			// con esto sincroniza a todas las hormigas
			try {
				barrier.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				e.printStackTrace();
			}
						
			juego.getTablero().moverHormiga(this);

			Hormiga.imprimirTablero();
			// tiempo para elegir movimientos
			try {
				Thread.sleep(juego.TIEMPO_A_ESPERAR);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			seImprimio = false;
			this.permitirMover();
			
			// si el jugador eligio una direccion, entonces no se genera la aleatoria
			if (!direccionSeteadaPorJugador) {
				this.cambiarDireccion(Direccion.getDireccionAleatoria());
				this.direccionSeteadaPorJugador = false;
			}
		}
	}

	public void cambiarDireccion(Direccion dir) {
		this.direccionActual = dir;
		
		Actualizador.getInstance().actualizar(getFila(), getColumna(), this);
	}

	public synchronized static void imprimirTablero() {
		if (!seImprimio) {
			seImprimio = true;
			Juego.getInstance().getTablero().print();
		}
	}

	@Override
	public String toString() {
		return "  " + color + direccionActual + " ";
	}
}
