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
	
	
	public Tablero(char[][] tablero) 
	{
		this.tablero = tablero;
		
	}
	
	public Tablero(int fila, int columna, int jugadorActual) 
	{
		tablero = new char[fila][columna];
		this.jugadorActual = jugadorActual;
		
		//Inicializamos el tablero vacio por defecto
		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero[0].length; j++) {
				tablero[i][j] = ' ';
			}
		}
	}

	/**
	 * @return cantidad de fichas para un determinado jugador, ('N' o 'B').
	 */
	public int cantidadFichas(char jugador) {
		int resultado = 0;
		char ficha;
		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero[0].length; j++) {
				ficha = getCasilla(i, j);
				if(ficha == jugador) {
					resultado++;
				}
			}
		}
		return resultado;
	}

	public int getJugadorActual()
	{
		return jugadorActual;
	}

	public void setCasilla(int fila, int columna, char charAux) 
	{
		if(fila < tablero.length && columna < tablero[0].length)
		{
		    tablero[fila][columna] = charAux;
		}
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
		if(movimiento != null)
		{
			MovimientoReversi mov = (MovimientoReversi) movimiento;
			char jugadorMovio = 'B';
			//Establecemos el jugador que mueve
			if(mov.jugador().toString().equals(Reversi.nombreJugadorNegras)) { //Si el jugador que movió son negras
				jugadorMovio = 'N';
			}
			//Pongo ficha del judagor que movió en nueva posición
			tablero[mov.newFila][mov.newColumna] = jugadorMovio; 
			
			//Actualizamos las casillas correspondientes
			List<Casilla> casillasQueCambian = mov.casillasQueCambian;
			for(Casilla unaCasilla : casillasQueCambian)
			{
				tablero[unaCasilla.fila()][unaCasilla.columna()] = jugadorMovio;
			}
		}
		
		cambiarTurno();
	}


	public void cambiarTurno() {
		if(jugadorActual == 0)
		{
			jugadorActual++;
		}else
		{
			jugadorActual--;
		}	
	}
	
	public void setJugadorActual(int jugadorActual)
	{
		this.jugadorActual = jugadorActual;
	}

	/**
	 * Imprime el tablero
	 * @return
	 */
	public String imprimirse() 
	{
		StringBuilder builder = new StringBuilder();
		builder.append('\n')
				.append(" A  B  C  D  E  F  G  H")
				.append('\n')
				.append("+--+--+--+--+--+--+--+--+")
				.append('\n');
				
		char resultado;
		
		for (int i = 0; i < tablero.length; i++) 
		{
			for (int j = 0; j < tablero[0].length; j++) 
			{
				resultado = getCasilla(i, j);
				builder.append("|" + resultado + " ");
			}
			builder.append("|"+ (i+1))
			.append('\n')
			.append("+--+--+--+--+--+--+--+--+")
			.append('\n');
		}
		return builder.toString();
	}
	
	public String salidaHTML()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("<table style='font-size:28.0pt;text-align:center'>")
			   .append("<table border cellpadding=3>");
		

		for (int i = 0; i < tablero.length; i++) 
		{
			builder.append("<tr>");
			for (int j = 0; j < tablero[0].length; j++) 
			{
				builder.append("<td>")
				.append(getCaracter(i,j))
				.append("</td>");
			}
		}
		
		builder.append("</table>");
		builder.append("<br>");
		
		return builder.toString();
	}

	private String getCaracter(int i, int j) {
		char ficha = tablero[i][j];
		if(ficha == 'N')
		{ 	return "&#9818;"; }
		else{ 
			if(ficha == 'B')
			{  return "&#9812;"; }
			else
			{ return "&nbsp;&nbsp;&nbsp;&nbsp;"; }
		}
	}

	/**
	 * @return lista de casillas en donde hay fichas del jugador actual
	 */
	public List<Casilla> getFichasJugador(char jugador) 
	{
		char fichas = jugador;
		
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

	public boolean hayFichaAdversario(int fila, int columna, char jugadorContrario) 
	{
		char resultado = getCasilla(fila, columna);
		if(resultado != ' ')
		{
			if(jugadorContrario ==  resultado)
				return true;
		}
		return false;
	}

	public Tablero copiar() {
		//return (new Tablero(tablero.clone()));
		Tablero tableroNuevo =  new Tablero(tablero.length,tablero.length, jugadorActual);
		
		for(int fila = 0; fila < tablero.length; fila++)
		{
			for(int colu = 0; colu < tablero.length; colu++)
			{
				tableroNuevo.setCasilla(fila, colu, tablero[fila][colu]);
			}
		}
		
		return tableroNuevo;
		
	}

}
