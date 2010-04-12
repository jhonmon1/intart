package juego.Reversi;

import juegos.base.Estado;
import juegos.base.Movimiento;

public class Nodo {

	private Estado estado;
	private Movimiento movimiento;
	
	public Estado getEstado()
	{
		return estado;
	}
	
	public Movimiento getMovimiento()
	{
		return movimiento;
	}
	
	public Nodo(Estado estado, Movimiento movimiento)
	{
		this.estado = estado;
		this.movimiento = movimiento;
	}
}
