package juegos.agentes;


import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import juego.Reversi.Reversi.EstadoReversi.MovimientoReversi;
import juegos.base.*;


public class AgenteHeuristico1 extends _AgenteHeuristico
{
	private Jugador jugador;
	private PrintStream output = null;
	private int niveles;
	
	/** Agente aleatorio, iniciando el generador de números pseudoaleatorios con
	 *  la semilla dada.  
	 */
	public AgenteHeuristico1(int niveles) 
	{
		super();
		this.niveles = niveles;
	}
	
	@Override public Jugador jugador() {
		return jugador;
	}
	
	@Override public void comienzo(Jugador jugador, Estado estado) {
		this.jugador = jugador;	
	}
	
	@Override public void fin(Estado estado) {
		// No hay implementación.
	}
	
	@Override public void movimiento(Movimiento movimiento, Estado estado) {
		if(output != null){
			output.println(String.format("Jugador %s mueve %s.", movimiento.jugador(), movimiento));
			printEstado(estado);
		}
	}
	
	protected void printEstado(Estado estado) {
		output.println("\t"+ estado.toString().replace("\n", "\n\t"));
	}
	
	@Override public String toString() {
		return String.format("Agente Heuristico 1", " - ");
	}

	@Override
	public double darHeuristica(Estado estado) {
		return 1;
	}

	@Override
	public int niveles() {
		return niveles;
	}


}
