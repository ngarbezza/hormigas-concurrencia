package model;

import gui.Actualizador;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;

import model.herramientas.Color;

public class Juego {
	private static Juego instance = null;
	private Jugador jugador1, jugador2;
	private Tablero tablero;
	private boolean termino = false;

	public final int TIEMPO_A_ESPERAR = 2000;
	
	//Singleton
	public static Juego getInstance(){
		if (instance == null){
			instance = new Juego();
		}
		return instance;
	}
	
	//Setters&Getters
	public void setJugador1(Jugador j1) {
		this.jugador1 = j1;
	}
	
	public void setJugador2(Jugador j2) {
		this.jugador2 = j2;
	}
	
	public Jugador getJugador1() {
		return jugador1;
	}
	
	public Jugador getJugador2() {
		return jugador2;
	}
	
	public void setTablero(Tablero tablero) {
		this.tablero = tablero;
	}
	
	public Tablero getTablero() {
		return tablero;
	}
	
	public void terminoElJuego(){
		this.termino = true;
	}

	public boolean termino() {
		return termino;
	}

	/**
	 * Notifica de la finalizacion del juego
	 * 
	 * @param color
	 */
	public void terminoElJuego(Color color){
		System.out.println("┴┬┴┬┴┬┴┬┴┬┴┬┴┬┴┬┴┬┴┬┴┬┴┬┴┬");
		System.out.println("┬┴┬┴┬┴┬┴┬┴┬┴┬┴┬┴┬┴┬┴┬┴┬┴┬┴");
		System.out.println("Ganador el equipo: " +color);
		System.out.println("┴┬┴┬┴┬┴┬┴┬┴┬┴┬┴┬┴┬┴┬┴┬┴┬┴┬");
		System.out.println("┬┴┬┴┬┴┬┴┬┴┬┴┬┴┬┴┬┴┬┴┬┴┬┴┬┴");
		System.out.println();
		this.termino = true;
		
		// notificar a la UI
		Actualizador.getInstance().notificarGanador(color);
	}
	
	/**
	 * Inicia los threads de las hormigas, dando asi inicio al juego
	 */
	public void comenzar() {
		for (Hormiga h : getJugador1().getHormigas())
			new Thread(h).start();
		for (Hormiga h : getJugador2().getHormigas())
			new Thread(h).start();
	}
	
	/**
	 * Crea las hormigas y las ubica de forma aleatoria en el tablero.
	 * Crea y setea tambien el barrier, mecanismo que se utilizara para
	 * la sincronizacion de los turnos.
	 * 
	 * @param cantidad
	 */
	public void inicializarHormigas(int cantidad) {
		CyclicBarrier barrier = new CyclicBarrier(cantidad * 2);
		for (int i=0; i<cantidad; i++) {
			Hormiga hr = new Hormiga(barrier, Color.ROJO);
			this.getJugador1().agregarHormiga(hr);
			this.ubicarEnPosicionAleatoria(hr, Color.ROJO);
			Hormiga hn = new Hormiga(barrier, Color.NEGRO);
			this.getJugador2().agregarHormiga(hn);
			this.ubicarEnPosicionAleatoria(hn, Color.NEGRO);
		}
	}
	
	/**
	 * Coloca una cantidad aleatoria de obstaculos en posiciones
	 * tambien aleatorias.
	 */
	public void inicializarObstaculos() {
		Random rand = new Random();
		int tope = rand.nextInt((Tablero.FILAS + Tablero.COLUMNAS) / 2);
		int fila, col;
		for (int i = 0; i<tope; i++) {
			do {
				fila = rand.nextInt(Tablero.FILAS);
				col = rand.nextInt(Tablero.COLUMNAS);
			} while (!(getTablero().get(fila, col) instanceof Vacio));
			this.getTablero().put(fila, col, new Obstaculo());
		}
	}
	
	/**
	 * Coloca las banderas roja y negra en posiciones aleatorias
	 * en el tablero.
	 */
	public void inicializarBanderas() {
		Bandera bandera = new Bandera(Color.ROJO);
		this.ubicarEnPosicionAleatoria(bandera, Color.NEGRO);
		Bandera bandera2 = new Bandera(Color.NEGRO);
		this.ubicarEnPosicionAleatoria(bandera2, Color.ROJO);
	}

	/**
	 * Ubica un objeto {@link Localizable} en una posicion aleatoria
	 * del tablero (revisando primero si esa posicion no esta ocupada).
	 * 
	 * @param l
	 * @param c
	 */
	private void ubicarEnPosicionAleatoria(Localizable l, Color c) {
		Random rand = new Random();
		int fila, col;
		do {
			int filasTablero = Tablero.FILAS / 2;
			fila = (c == Color.ROJO)? rand.nextInt(filasTablero) : filasTablero - rand.nextInt(filasTablero);
			col = rand.nextInt(Tablero.COLUMNAS);
		} while (!(getTablero().get(fila, col) instanceof Vacio));
		
		this.getTablero().ponerLocalizable(fila, col, l);
	}

	/**
	 * Deja listo al juego para poder continuar jugando. PRECONDICION: el juego
	 * esta finalizado (no esta corriendo).
	 */
	public void reset() {
		this.termino = false;		
	}
} 
