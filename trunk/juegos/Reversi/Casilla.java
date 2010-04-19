package juegos.Reversi;

public class Casilla {

	private int fila;
	private int columna;
	
	public Casilla(int fila, int columna)
	{
		this.fila = fila;
		this.columna = columna;
	}
	
	public int fila()
	{
		return fila;
	}
	
	public int columna()
	{
		return columna;
	}
}
