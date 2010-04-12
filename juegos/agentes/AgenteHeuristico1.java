package juegos.agentes;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import juego.Reversi.Reversi.EstadoReversi.MovimientoReversi;
import juegos.base.Agente;
import juegos.base.Estado;
import juegos.base.Jugador;
import juegos.base.Movimiento;

public class AgenteHeuristico1 implements Agente
{
	private Jugador jugador;
	private PrintStream output = null;
	
	/** Agente aleatorio, iniciando el generador de números pseudoaleatorios con
	 *  la semilla dada.  
	 */
	public AgenteHeuristico1() 
	{
		super();
	}
	
	@Override public Jugador jugador() {
		return jugador;
	}
	
	/**
	 * Método encargado de decidir que movimiento realizar a partir del estado actual.
	 * Este agente en particular eligirá el movimiento que "coma" mas fichas del contrario.
	 */
	@Override public Movimiento decision(Estado estado) 
	{
		Movimiento[] movs = estado.movimientos(jugador);
		
		// El valor del movimiento es el número de fichas que va a comer
		int valorMax = 0;
		int valorAux = 0;
		MovimientoReversi mov = null;
		List<Movimiento> lista = new ArrayList<Movimiento>();
		
		for (int i = 0; i < movs.length; i++) 
		{
			mov = (MovimientoReversi)movs[i];
			valorAux = mov.casillasQueCambian.size();
			if(valorAux > valorMax)
			{
				lista = new ArrayList<Movimiento>();
				lista.add(mov);
			}
			if(valorAux == valorMax)
			{
				lista.add(mov);
			}
		}
		Random random = new Random();
		int index = random.nextInt(lista.size());
		return lista.get(index);
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

}
