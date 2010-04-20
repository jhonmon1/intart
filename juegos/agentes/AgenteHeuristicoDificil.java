package juegos.agentes;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintStream;
import java.io.PrintWriter;

import juegos.Reversi.Tablero;
import juegos.Reversi.Reversi.EstadoReversi;
import juegos.base.*;

/**
 * Implementación de un agente heurístico con un nivel de 
 * dificultad "DIFÍCIL" de juego
 * 
 */
public class AgenteHeuristicoDificil extends _AgenteHeuristico {

	private Jugador jugador;
	private PrintStream output = null;
	private int niveles;	
	private String stringJugada;

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
		
		StringBuilder builder = new StringBuilder();
		builder.append("<html><head><title>Reversi ( Balsa - Chabkinian - Rosso)</title></head><body><p><h1>Partida: </h1></p><p/>");
		
		stringJugada = builder.toString();
	}
	
	@Override public void fin(Estado estado) {
		StringBuilder builder = new StringBuilder();
		builder.append("</body></html>");
		stringJugada += builder.toString();
		
		try {
		    FileWriter fw = new FileWriter("Reversi.html");
		    BufferedWriter bw = new BufferedWriter(fw);
		    PrintWriter salida = new PrintWriter(bw);
		    salida.println(stringJugada);
		    salida.close();
		}
		catch(Exception ioex) {
		  System.out.println("Error: "+ioex.toString());
		}
	}
	
	
	
	@Override public void movimiento(Movimiento movimiento, Estado estado) {
		if(output != null){
			output.println(String.format("Jugador %s mueve %s.", movimiento.jugador(), movimiento));
			printEstado(estado);
		}
		stringJugada += String.format("<p/><p><b> Jugador %s mueve %s. </b></p><p/>", movimiento.jugador(), movimiento);
		stringJugada += ((EstadoReversi)estado).tablero.salidaHTML();
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

	@Override
	public double maximoValorHeuristica() {
		return 64;
	}
}
