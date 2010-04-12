package juego.Reversi;

import java.util.List;

public class Arbol {

	private Nodo nodo;
	private List<Arbol> hijos;
	private double valorArbol;
	
	public double getValorArbol()
	{
		return valorArbol;
	}
	
	public Nodo getNodo()
	{
		return nodo;
	}
	
	public Arbol(Nodo nodo, List<Arbol> hijos, double valorArbol)
	{
		this.nodo = nodo;
		this.hijos = hijos;
		this.valorArbol = valorArbol;
	}
}
