package test;

import model.*;
import model.herramientas.Direccion;
import junit.framework.TestCase;
import static org.easymock.classextension.EasyMock.*;
import static org.easymock.EasyMock.*;

public class TableroTest extends TestCase {

	Tablero tablero;
	Hormiga hmock1, hmock2, hmock3;
	Objeto objmock;	//va a representar objeto vacio, obstaculo y bandera
	
	protected void setUp() throws Exception {
		tablero = new Tablero();
		hmock1 = createMock(Hormiga.class);
		hmock2 = createMock(Hormiga.class);
		hmock3 = createMock(Hormiga.class);
		objmock = createMock(Objeto.class);
	}
	
	/**
	 * Movimientos de la hormiga.
	 * Tiene el camino libre.
	 */
	public void testHormigaSePuedeMover() throws Exception {
		tablero.put(0, 0, hmock1);
		
		expect(hmock1.esTurnoValido()).andReturn(true);
		expect(hmock1.getDireccion()).andReturn(Direccion.NORTE).atLeastOnce();
		expect(hmock1.getFila()).andReturn(0).atLeastOnce();
		expect(hmock1.getColumna()).andReturn(0).atLeastOnce();
		hmock1.mover(Direccion.NORTE);
		
		replay(hmock1);
		tablero.moverHormiga(hmock1);
		verify(hmock1);
	}
	
	/**
	 * Tiene una hormiga adelante, pero se puede mover.
	 */
	public void testHormigaConHormigaAdelantePuedeMoverse() throws Exception {
		tablero.put(0, 0, hmock1);
		tablero.put(1, 0, hmock2);
		
		expect(hmock1.esTurnoValido()).andReturn(true);
		expect(hmock1.getDireccion()).andReturn(Direccion.NORTE).atLeastOnce();
		expect(hmock1.getFila()).andReturn(0).atLeastOnce();
		expect(hmock1.getColumna()).andReturn(0).atLeastOnce();
		hmock1.mover(Direccion.NORTE);
		
		expect(hmock2.puedeMover(hmock1)).andReturn(true);
		hmock2.mover(Direccion.NORTE);
		hmock2.noPuedeMover();	//no se va a poder mover porque la empujaron
		
		replay(hmock1);
		replay(hmock2);
		tablero.moverHormiga(hmock1);
		verify(hmock1);
		verify(hmock2);
	}
	
	/**
	 * Casos en los que no se puede mover.
	 * Tiene un obstaculo adelante.
	 */
	public void testHormigaNoSePuedeMoverPorObstaculo() throws Exception {
		tablero.put(0, 0, hmock1);
		tablero.put(1, 0, objmock);
		
		expect(hmock1.esTurnoValido()).andReturn(true);
		expect(hmock1.getDireccion()).andReturn(Direccion.NORTE).atLeastOnce();
		expect(hmock1.getFila()).andReturn(0).atLeastOnce();
		expect(hmock1.getColumna()).andReturn(0).atLeastOnce();
		
		expect(objmock.puedeMover(hmock1)).andReturn(false);
		
		replay(hmock1);
		replay(objmock);
		tablero.moverHormiga(hmock1);
		verify(hmock1);
		verify(objmock);
	}
	
	/**
	 * Tiene a una hormiga con un obstaculo adelante.
	 */
	public void testHormigaTieneUnaHormigaConUnObstaculoAdelante() throws Exception {
		tablero.put(0, 0, hmock1);
		tablero.put(1, 0, hmock2);
		tablero.put(2, 0, objmock);
		
		expect(hmock1.esTurnoValido()).andReturn(true);
		expect(hmock1.getDireccion()).andReturn(Direccion.NORTE).atLeastOnce();
		expect(hmock1.getFila()).andReturn(0).atLeastOnce();
		expect(hmock1.getColumna()).andReturn(0).atLeastOnce();
		
		expect(hmock2.puedeMover(hmock1)).andReturn(false);
		
		replay(hmock1);
		replay(hmock2);
		tablero.moverHormiga(hmock1);
		verify(hmock1);
		verify(hmock2);
	}
	
	/**
	 * Tiene a una hormiga con otra hormiga adelante.
	 */
	public void testHormigaTieneUnaHormigaConOtraHormigaAdelante() throws Exception {
		tablero.put(0, 0, hmock1);
		tablero.put(1, 0, hmock2);
		tablero.put(2, 0, hmock3);
		
		expect(hmock1.esTurnoValido()).andReturn(true);
		expect(hmock1.getDireccion()).andReturn(Direccion.NORTE).atLeastOnce();
		expect(hmock1.getFila()).andReturn(0).atLeastOnce();
		expect(hmock1.getColumna()).andReturn(0).atLeastOnce();
		
		expect(hmock2.puedeMover(hmock1)).andReturn(false);
		
		replay(hmock1);
		replay(hmock2);
		tablero.moverHormiga(hmock1);
		verify(hmock1);
		verify(hmock2);
	}
	
	/**
	 * Tiene a una hormiga con y el limite del tablero adelante.
	 */
	public void testHormigaTieneUnaHormigaConElVacioAdelante() throws Exception {
		tablero.put(1, 0, hmock1);
		tablero.put(0, 0, hmock2);
		
		expect(hmock1.esTurnoValido()).andReturn(true);
		expect(hmock1.getDireccion()).andReturn(Direccion.SUR).atLeastOnce();
		expect(hmock1.getFila()).andReturn(1).atLeastOnce();
		expect(hmock1.getColumna()).andReturn(0).atLeastOnce();
		
		expect(hmock2.puedeMover(hmock1)).andReturn(false);
		
		replay(hmock1);
		replay(hmock2);
		tablero.moverHormiga(hmock1);
		verify(hmock1);
		verify(hmock2);
	}
	
	/**
	 * Esta en el limite del tablero.
	 * Limite Norte.
	 */
	public void testHormigaEnElLimiteNorteDelTablero() throws Exception {
		tablero.put(7, 0, hmock1);
		
		expect(hmock1.getDireccion()).andReturn(Direccion.NORTE).atLeastOnce();
		expect(hmock1.getFila()).andReturn(7).atLeastOnce();	//no llega a preguntar por columna
		
		replay(hmock1);
		tablero.moverHormiga(hmock1);
		verify(hmock1);
	}
	
	/**
	 * Limite Sur.
	 */
	public void testHormigaEnElLimiteSurDelTablero() throws Exception {
		tablero.put(0, 0, hmock1);
		
		expect(hmock1.getDireccion()).andReturn(Direccion.SUR).atLeastOnce();
		expect(hmock1.getFila()).andReturn(0).atLeastOnce();	//no llega a preguntar por columna
		
		replay(hmock1);
		tablero.moverHormiga(hmock1);
		verify(hmock1);
	}
	
	/**
	 * Limite Este.
	 */
	public void testHormigaEnElLimiteEsteDelTablero() throws Exception {
		tablero.put(0, 7, hmock1);
		
		expect(hmock1.getDireccion()).andReturn(Direccion.ESTE).atLeastOnce();
		expect(hmock1.getFila()).andReturn(0).atLeastOnce();
		expect(hmock1.getColumna()).andReturn(7).atLeastOnce();
		
		replay(hmock1);
		tablero.moverHormiga(hmock1);
		verify(hmock1);
	}
	
	/**
	 * Limite Oeste.
	 */
	public void testHormigaEnElLimiteOesteDelTablero() throws Exception {
		tablero.put(0, 0, hmock1);
		
		expect(hmock1.getDireccion()).andReturn(Direccion.OESTE).atLeastOnce();
		expect(hmock1.getFila()).andReturn(0).atLeastOnce();
		expect(hmock1.getColumna()).andReturn(0).atLeastOnce();
		
		
		replay(hmock1);
		tablero.moverHormiga(hmock1);
		verify(hmock1);
	}
}
