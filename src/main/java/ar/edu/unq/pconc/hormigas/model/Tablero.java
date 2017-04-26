package ar.edu.unq.pconc.hormigas.model;

import ar.edu.unq.pconc.hormigas.gui.Actualizador;
import ar.edu.unq.pconc.hormigas.model.herramientas.Direccion;
import ar.edu.unq.pconc.hormigas.model.herramientas.ListaSemaforo;

public class Tablero {
	public static final int FILAS = 8,	    //cantidad de columnas que tiene el tablero
							COLUMNAS = 8;	//cantidad de filas que tiene el tablero
	
	private Objeto[][] tablero;
	
	/**
	 * La estructura usada para sincronizar el acceso al tablero.
	 */
	private ListaSemaforo mutex = new ListaSemaforo();
	
	public Tablero(){
		tablero = new Objeto[FILAS][COLUMNAS];
		for (int x = 0; x<FILAS; x++)
			for (int y = 0; y<COLUMNAS; y++)
				tablero[x][y] = new Vacio();
	}
	
	public Objeto get(int columna, int fila) {
		return tablero[columna][fila];
	}
	
	public void put(int fila, int columna, Objeto obj) {
		tablero[fila][columna] = obj;
	}
	
	public void remove(int fila, int columna) {
		tablero[fila][columna] = null;
	}
	
	/**
	 * Ubica un objeto localizable, y le setea la fila y columna correspondiente.
	 * 
	 * @param fila
	 * @param columna
	 * @param l
	 */
	public void ponerLocalizable(int fila, int columna, Localizable l) {
		this.put(fila, columna, l);
		l.setFila(fila);
		l.setColumna(columna);
	}
	
	/**
	 * Realiza el movimiento de una hormiga, si es posible
	 */
	public synchronized void moverHormiga(Hormiga h){
		if (movimientoDentroDelLimite (h, h.getDireccion())){	//si estoy dentro de los limites del tablero
			Direccion curDir = h.getDireccion();
			int curFila = h.getFila(),
				curColumna = h.getColumna(),
				filaSig = h.getFila()+curDir.getMovimiento().getX(),
				columnaSig = h.getColumna()+curDir.getMovimiento().getY();
			
			adquirirLock(curFila, curColumna, h.getDireccion());
			
			if (h.esTurnoValido() && tablero[filaSig][columnaSig].puedeMover(h)){	//pregunto si se puede mover
				tablero[filaSig][columnaSig].mover(h.getDireccion());		//se mueve el otro objeto
				h.mover(h.getDireccion());	//se mueve la hormiga
				
				Vacio vacio = new Vacio();
				tablero[curFila][curColumna] = vacio;	//la posicion inicial esta vacia
				
				// notifico de cambios a la UI
				Actualizador.getInstance().actualizar(curFila, curColumna, vacio);
				
				if(tablero[filaSig][columnaSig] instanceof Hormiga){	//si el otro objeto era una hormiga
					((Hormiga)tablero[filaSig][columnaSig]).noPuedeMover();	//no se va a poder mover
				}
			}
			soltarLock(curFila, curColumna, curDir);
		}
	}
	
	/**
	 * Indica si el movimiento de la hormiga esta dentro del tablero
	 */
	public boolean movimientoDentroDelLimite(Hormiga h, Direccion d) {
		return 	h.getFila()+d.getMovimiento().getX() < FILAS &&
				h.getFila()+d.getMovimiento().getX() >= 0 &&
				h.getColumna()+d.getMovimiento().getY() >= 0 &&
				h.getColumna()+d.getMovimiento().getY() < COLUMNAS;
	}
	
	
	public synchronized void adquirirLock(int fila, int columna, Direccion d){
		mutex.acquire(fila, columna, d);
	}
	
	public void soltarLock(int fila, int columna, Direccion d){
		mutex.free(fila, columna, d);
	}
	
	public void print(){
		for (int i = 0; i<COLUMNAS; i++)
			System.out.print("------");
		System.out.println();
		
		for (int x = COLUMNAS-1; x>=0; x--){
			for (int y = 0; y<FILAS; y++){
				System.out.print("|");
				System.out.print(tablero[x][y]);
			}
			System.out.println("|");
		}
		
		for (int i = 0; i<COLUMNAS; i++)
			System.out.print("------");
		System.out.println();
	}
}
