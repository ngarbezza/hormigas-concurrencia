package ar.edu.unq.pconc.hormigas.model.herramientas;

import java.util.List;
import java.util.Vector;

public class ListaSemaforo {
	private List<Par> xs = new Vector<>();
	
	/**
	 * Agrega los 3 elementos a la lista
	 */
	public synchronized void acquire(int fila, int columna, Direccion d){
		Par p1 = new Par(fila, columna),
			p2 = p1.aug(d.getMovimiento()),
			p3 = p2.aug(d.getMovimiento());
		
		while(xs.contains(p1) && xs.contains(p2) && xs.contains(p3)){
				//mientras la lista contenga alguna de las coordenadas,
				//se duerme
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//si no las contiene, las agrega
		xs.add(p1);
		xs.add(p2);
		xs.add(p3);
	}
	
	public synchronized void free(int fila, int columna, Direccion d){
		Par p1 = new Par(fila, columna),
			p2 = p1.aug(d.getMovimiento()),
			p3 = p2.aug(d.getMovimiento());
		
		notifyAll();
	}
}
