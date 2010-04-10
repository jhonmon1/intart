package juego.Reversi;

import java.util.ArrayList;
import java.util.List;

import juego.Reversi.Reversi.EstadoReversi.MovimientoReversi;
import juegos.base.Movimiento;

/**
 * Clase que representa el tablero del Juego Reversi.
 */
public class Tablero {

	private char[][] tablero;
	private int jugadorActual;
	
	public Tablero(int fila, int columna) 
	{
		tablero = new char[fila][columna];
		jugadorActual = 0;
		
		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero[0].length; j++) {
				tablero[i][j] = ' ';
			}
		}
	}

	/**
	 * @return cantidad de fichas negras en el tablero.
	 */
	public int fichasNegras() {
		int resultado = 0;
		char ficha;
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
		MovimientoReversi mov = (MovimientoReversi) movimiento;
		char jugadorMovio = 'B';
		if(mov.jugador().toString().equals(Reversi.nombreJugadorNegras)) { //Si el jugador que movió son negras...
			jugadorMovio = 'N';
		}
		tablero[mov.newFila][mov.newColumna] = jugadorMovio; //Pongo ficha del judagor que movió en nueva posición
		
		// Paso al otro jugador como el actual
		if(jugadorActual == 0) {
			jugadorActual++;
		} else {
			jugadorActual--;
		}
	}
	
	/**
	 * @return Jugador a Mover.
	 */
	public int getJugadorActual() 
	{
		return jugadorActual;
	}

	/**
	 * Imprime el tablero
	 * @return
	 */
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

	/**
	 * @return lista de casillas en donde hay fichas del jugador actual
	 */
	public List<Casilla> getFichasJugador() 
	{
		char fichas = ' ';
		if(jugadorActual == 0) 
		{
			fichas = 'N';
		} 
		else if (jugadorActual == 1) {
			fichas = 'B';
		}
		
		Casilla casilla;
		List<Casilla> listaCasillas = new ArrayList<Casilla>();
		for (int i = 0; i < tablero.length; i++) 
		{
			for (int j = 0; j < tablero[0].length; j++) 
			{
				if(fichas == tablero[i][j])
				{
					casilla = new Casilla(i, j);
					listaCasillas.add(casilla);
				}
			}
		}
		return listaCasillas;
	}


	/**
	 * @return lista de casillas donde hay fichas adyacentes, por lo tanto son posiciones tentativas donde se podria colocar una ficha.
	 */
	public List<Casilla> getFichasDondeColocar() 
	{
		Casilla casilla;
		List<Casilla> listaCasillas = new ArrayList<Casilla>();
		for (int i = 0; i < tablero.length; i++) 
		{
			for (int j = 0; j < tablero[0].length; j++) 
			{
				if(tablero[i][j] == ' ' && hayFichaAdyacente(i,j))
				{
					casilla = new Casilla(i, j);
					listaCasillas.add(casilla);
				}
			}
		}
		return listaCasillas;
	}

	/**
	 * @param i: fila
	 * @param j: columna
	 * @return true si hay una ficha adyacente a la ficha en la posicion (i,j)
	 */
	private boolean hayFichaAdyacente(int fila, int columna) 
	{
		int newFila;
		int newColumna;
		for (int x = -1; x <= 1; x++) 
		{
			for (int y = -1; y <= 1; y++) 
			{
				newFila = fila + x;
				newColumna = columna + y;
				if(newFila >= 0 && newFila < 8 && newColumna >=0 && newColumna <8 &&
						tablero[newFila][newColumna] != ' ')
					return true;
			}
		}
		return false;
	}

	public boolean hayFichaAdversario(int fila, int columna) 
	{
		char resultado = getCasilla(fila, columna);
		if(resultado != ' ')
		{
			if(jugadorActual == 0 && resultado == 'B')
				return true;
			if(jugadorActual == 1 && resultado == 'N')
				return true;
		}
		return false;
	}

}
