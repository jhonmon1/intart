package juegos.agentes;


import java.io.PrintStream;


import juego.Reversi.Tablero;
import juego.Reversi.Reversi.EstadoReversi;
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
		char miFicha = (jugador().toString() == "JugadorNegras" ? 'N' :'B');
		char fichaContrario = (jugador().toString() == "JugadorNegras" ? 'B' :'N');
		
		Tablero tablero = ((EstadoReversi)(estado)).getTablero();
		int misFichas = tablero.cantidadFichas(miFicha);
		int fichasContrario = tablero.cantidadFichas(fichaContrario);
		return misFichas - fichasContrario;
	}

	@Override
	public int niveles() {
		return niveles;
	}


}
