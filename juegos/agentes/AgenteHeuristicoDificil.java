package juegos.agentes;

import java.io.PrintStream;
import juego.Reversi.Tablero;
import juego.Reversi.Reversi.EstadoReversi;
import juegos.base.Estado;
import juegos.base.Jugador;
import juegos.base.Movimiento;
import juegos.base._AgenteHeuristico;

/**
 * Implementación de un agente heurístico con un nivel de 
 * dificultad "DIFÍCIL" de juego
 * 
 */
public class AgenteHeuristicoDificil extends _AgenteHeuristico {

	private Jugador jugador;
	private PrintStream output = null;
	private int niveles;	

	/**
	 * Constructor de AgenteHeuristicoDificil
	 * @param niveles: niveles de recursión
	 */
	public AgenteHeuristicoDificil(int niveles) 
	{
		super();
		this.niveles = niveles;
	}
	
	public AgenteHeuristicoDificil(int niveles, PrintStream output) 
	{
		super();
		this.niveles = niveles;
		this.output = output;
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
		if(output != null){
			output.println("\t"+ estado.toString().replace("\n", "\n\t"));
		}
	}
	
	@Override public String toString() {
		return String.format("Agente Heuristico Dificil " + jugador.toString());
	}

	@Override
	public double darHeuristica(Estado estado) {
		
		char miFicha = (jugador().toString() == "JugadorNegras" ? 'N' :'B');
		char fichaContrario = (jugador().toString() == "JugadorNegras" ? 'B' :'N');
		
		Tablero tablero = ((EstadoReversi)(estado)).getTablero();
		
		//------------------------------------------------------------------------
		// Diferencia de fichas entre jugadores
		int misFichas = tablero.cantidadFichas(miFicha);
		int fichasContrario = tablero.cantidadFichas(fichaContrario);
		
		int diferenciaFichas = misFichas - fichasContrario;
		
		//------------------------------------------------------------------------
		// Fichas del jugador actual en las esquinas
		int esquinas = 0;
		
		if (tablero.getCasilla(0, 0) == miFicha) { esquinas++; }
		if (tablero.getCasilla(0, 7) == miFicha) { esquinas++; }
		if (tablero.getCasilla(7, 0) == miFicha) { esquinas++; }
		if (tablero.getCasilla(7, 7) == miFicha) { esquinas++; }
		
		// Se le da un valor de 10 a cada esquina ocupada por las fichas del jugador actual
		esquinas *= 10;
		
		//------------------------------------------------------------------------
		// Fichas del jugador actual en las 16 posiciones
		int posicionesCentrales = 0;
		
		for(int i=2; i<=5; i++) {
			for(int j=2; j<=5; j++) {
				if(tablero.getCasilla(i, j) == miFicha) { posicionesCentrales++; }
			}
		}
		
		// Se le da un valor de 2 a cada posicion central ocupada por las fichas del jugador actual
		posicionesCentrales *= 2;
		
		//------------------------------------------------------------------------
		// Fichas del jugador actual en los bordes del tablero (menos las esquinas)
		int bordes = 0;
		
		// Fila 0
		for(int i=1; i<=6;i++){
			if(tablero.getCasilla(0, i) == miFicha){ bordes++; }
		}
		
		// Fila 7
		for(int i=1; i<=6;i++){
			if(tablero.getCasilla(7, i) == miFicha){ bordes++; }
		}
		
		// Columna 0
		for(int i=1; i<=6;i++){
			if(tablero.getCasilla(i, 0) == miFicha){ bordes++; }
		}
		
		// Columna 7
		for(int i=1; i<=6;i++){
			if(tablero.getCasilla(i, 7) == miFicha){ bordes++; }
		}
		
		bordes+=5;
		
		//------------------------------------------------------------------------
		
		return diferenciaFichas + esquinas + bordes;
	}

	@Override
	public int niveles() {
		return niveles;
	}
}
