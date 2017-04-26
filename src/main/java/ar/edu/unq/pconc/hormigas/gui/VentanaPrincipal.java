package ar.edu.unq.pconc.hormigas.gui;

import ar.edu.unq.pconc.hormigas.model.*;
import ar.edu.unq.pconc.hormigas.model.herramientas.Color;
import ar.edu.unq.pconc.hormigas.model.herramientas.Direccion;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class VentanaPrincipal extends JFrame {
	
	private JTable tablero;
	private JButton botonComenzar, botonReset;
	private JList hormigasJugador1, hormigasJugador2;
	private JButton surButton, oesteButton, esteButton, norteButton;

	public VentanaPrincipal() {
		SwingUtilities.invokeLater(() -> {
            setTitle("Guerra de hormigas");
            setSize(new Dimension(800, 500));
            setLocationRelativeTo(null);
            setVisible(true);
            initialize();
            crearTabla();
            crearBotones();
            crearListas();
            crearDirecciones();
            agregarComponentes();
        });
	}

	protected void crearListas() {
		hormigasJugador1 = new JList(new Vector<>(Juego.getInstance().getJugador1().getHormigas()));
		hormigasJugador2 = new JList(new Vector<>(Juego.getInstance().getJugador2().getHormigas()));
		
		hormigasJugador1.addListSelectionListener(e -> {
            if (hormigasJugador1.getSelectedValue() != null) {
                hormigasJugador2.clearSelection();
                activarBotones(true);
            } else {
                activarBotones(hormigasJugador2.getSelectedValue() == null);
            }
        });
		
		hormigasJugador2.addListSelectionListener(e -> {
            if (hormigasJugador2.getSelectedValue() != null) {
                hormigasJugador1.clearSelection();
                activarBotones(true);
            } else {
                activarBotones(hormigasJugador1.getSelectedValue() == null);
            }
        });
	}

	protected void activarBotones(boolean b) {
		norteButton.setEnabled(b);
		esteButton.setEnabled(b);
		oesteButton.setEnabled(b);
		surButton.setEnabled(b);
	}

	protected void crearDirecciones() {
		norteButton = new JButton("^^");
		norteButton.setEnabled(false);
		esteButton = new JButton(">>");
		esteButton.setEnabled(false);
		oesteButton = new JButton("<<");
		oesteButton.setEnabled(false);
		surButton = new JButton("vv");
		surButton.setEnabled(false);
	
		norteButton.addActionListener(e -> getHormigaActual().setDireccion(Direccion.NORTE));
		esteButton.addActionListener(e -> getHormigaActual().setDireccion(Direccion.ESTE));
		oesteButton.addActionListener(e -> getHormigaActual().setDireccion(Direccion.OESTE));
		surButton.addActionListener(e -> getHormigaActual().setDireccion(Direccion.SUR));
	}

	/**
	 * @return la hormiga que se encuentra seleccionada, o null 
	 * si no hay nada seleccionado
	 */
	protected Hormiga getHormigaActual() {
		if (hormigasJugador1.getSelectedValue() != null)
			return (Hormiga) hormigasJugador1.getSelectedValue();
		else
			return (Hormiga) hormigasJugador2.getSelectedValue();
	}

	protected void crearTabla() {
		tablero = new JTable(Tablero.FILAS, Tablero.COLUMNAS);
		tablero.setTableHeader(null);
		tablero.setColumnSelectionAllowed(true);
		tablero.setRowSelectionAllowed(true);
		tablero.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		llenarTablero();
	}

	protected void llenarTablero() {
		for (int i = 0; i<Tablero.FILAS; i++) {
			tablero.setRowHeight(i, 50);
			for (int j = 0; j<Tablero.COLUMNAS; j++) {
				tablero.getColumnModel().getColumn(j).setWidth(30);
				this.mostrarObjeto(i, j, Juego.getInstance().getTablero().get(i, j));
			}
		}
	}
	
	protected void agregarComponentes() {
		JPanel main = new JPanel();
		JPanel jugadoresPanel = new JPanel();
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.X_AXIS));
		this.add(main);
		this.add(jugadoresPanel);
		main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
		JPanel tableroPanel = new JPanel();
		JPanel botonesPanel = new JPanel();
		main.add(tableroPanel);
		main.add(botonesPanel);
		tableroPanel.setLayout(new FlowLayout());
		tableroPanel.add(new JScrollPane(tablero));
		botonesPanel.setLayout(new FlowLayout());
		botonesPanel.add(botonComenzar);
		botonesPanel.add(botonReset);
		
		jugadoresPanel.setLayout(new BoxLayout(jugadoresPanel, BoxLayout.Y_AXIS));
		JPanel j1Panel = new JPanel();
		JPanel directionsPanel = new JPanel();
		JPanel j2Panel = new JPanel();
		jugadoresPanel.add(j1Panel);
		jugadoresPanel.add(directionsPanel);
		jugadoresPanel.add(j2Panel);
		
		j1Panel.setLayout(new FlowLayout());
		j1Panel.setBorder(BorderFactory.createTitledBorder("Jugador 1 - Equipo ROJO"));
		j1Panel.add(new JScrollPane(hormigasJugador1));
		
		j2Panel.setLayout(new FlowLayout());
		j2Panel.setBorder(BorderFactory.createTitledBorder("Jugador 2 - Equipo NEGRO"));
		j2Panel.add(new JScrollPane(hormigasJugador2));
		
		directionsPanel.setLayout(new BorderLayout());
		directionsPanel.setBorder(BorderFactory.createTitledBorder("Asignar direccion"));
		directionsPanel.add(norteButton, BorderLayout.NORTH);
		directionsPanel.add(esteButton, BorderLayout.EAST);
		directionsPanel.add(oesteButton, BorderLayout.WEST);
		directionsPanel.add(surButton, BorderLayout.SOUTH);
	}
	
	protected void crearBotones() {
		botonComenzar = new JButton("Comenzar");
		botonComenzar.addActionListener(e -> {
            Juego.getInstance().comenzar();
            botonReset.setEnabled(false);
        });
		botonReset = new JButton("Reset");
		botonReset.addActionListener(e -> reset());
	}
	
	/**
	 * Muestra el objeto deseado en el tablero
	 * 
	 * @param x
	 * @param y
	 * @param o
	 */
	public void mostrarObjeto(int x, int y, Objeto o) {
		if (this.tablero != null)
			// porque puede no haberse inicializado aun
			this.tablero.setValueAt(o.toString(), Tablero.FILAS - x - 1, y);		
	}

	public void notificarGanador(Color color) {
		JOptionPane.showMessageDialog(this.getContentPane(), 
				"GANÓ el equipo " + color.name(), "Fin del juego!", 
				JOptionPane.INFORMATION_MESSAGE);
		botonReset.setEnabled(true);
	}

	/**
	 * Deja el juego listo para iniciarse otra vez.
	 */
	protected void reset() {
		initialize();
		llenarTablero();
		Juego.getInstance().reset();
	}

	/**
	 * Crea todos los parametros (jugadores, banderas, hormigas) necesarios
	 * para el juego.
	 */
	protected void initialize() {
		Tablero t = new Tablero();
		Juego juego = Juego.getInstance();
		juego.setTablero(t);
		Jugador
			j1 = new Jugador(),
			j2 = new Jugador();
		juego.setJugador1(j1);
		juego.setJugador2(j2);
		juego.inicializarBanderas();
		juego.inicializarHormigas(5);
		juego.inicializarObstaculos();
	}
}