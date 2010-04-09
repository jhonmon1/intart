package juego.Reversi;

import juegos.base.Movimiento;

/**
 * Clase que representa el tablero del Juego Reversi.
 * @author JuanJoséChabkinian
 */
public class Tablero {

	private char[][] tablero;
	private int jugadorActual;
	
	public Tablero(int fila, int columna) 
	{
		tablero = new char[fila][columna];
		jugadorActual = 0;
	}

	/**
	 * @return cantidad de fichas negras en el tablero.
	 */
	public int fichasNegras() {
		int resultado = 0;
		char ficha = ' ';
		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero[0].length; j++) {
				ficha = getCasilla(i, j);
				if(ficha == 'N') {
					resultado++;
				}
			}
		}
		return resultado;
	}

	/**
	 * @return cantidad de fichas blancas en el tablero.
	 */
	public int fichasBlancas() {
		int resultado = 0;
		char ficha = ' ';
		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero[0].length; j++) {
				ficha = getCasilla(i, j);
				if(ficha == 'B') {
					resultado++;
				}
			}
		}
		return resultado;
	}
	
	/**
	 * @return true si el tablero esta lleno, es decir, nos encontramos en un tablero final.
	 */
	public boolean esFinal() {
		// TODO Auto-generated method stub
		return false;
	}

	public char getCasilla(int fila, int columna) 
	{
		if(fila < tablero.length && columna < tablero[0].length)
		{
			return tablero[fila][columna];
		}
		return ' ';
	}

	/**
	 * Método que se encarga se realizar el movimiento en el tablero.
	 * @param movimiento
	 */
	public void actualizarTablero(Movimiento movimiento) 
	{
		
	}

	/**
	 * @return Jugador a Mover.
	 */
	public int getJugadorActual() 
	{
		return jugadorActual;
	}

	public String imprimirse() 
	{
		String tabla = "+--+--+--+--+--+--+--+--+ \n";
		char resultado;
		
		for (int i = 0; i < tablero.length; i++) 
		{
			for (int j = 0; j < tablero[0].length; j++) 
			{
				resultado = getCasilla(i, j);
				tabla = tabla + "|" + resultado + " ";
			}
			tabla = tabla + "|\n";
			tabla = tabla + "+--+--+--+--+--+--+--+--+ \n";
		}
		return tabla;
	}

}
