package juegos.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import juego.Reversi.Arbol;
import juego.Reversi.Nodo;
import juego.Reversi.Reversi.EstadoReversi.MovimientoReversi;

public abstract class _AgenteHeuristico implements Agente{

	public abstract int niveles();
	
	public abstract double darHeuristica(Estado estado);
	
	public  Movimiento miniMax(Estado estado, Movimiento[] movimientos, int niveles)
	{
		List<Arbol> arboles = new ArrayList<Arbol>();
		
		//Obtenemos todos los arboles resultados de aplicar cada uno de
		//los movimientos posibles para un estado
		for(Movimiento movimiento : movimientos){
			arboles.add(obtenerArbol(estado, movimiento, niveles-1, 1));
		}
		
		double valorAux = arboles.get(0).getValorArbol();
		Movimiento movAux = arboles.get(0).getNodo().getMovimiento();
		
		//Maximizamos ya que es el primer movimiento del miniMax
		for (Arbol arbol: arboles) 
		{
			if(arbol.getValorArbol() > valorAux)
			{
				valorAux = arbol.getValorArbol();
				movAux = arbol.getNodo().getMovimiento();
			}
		}
		return movAux;
	
	}
	
	
	//Jugador 0 indica yo, 1 contrario
	public Arbol obtenerArbol(Estado estado, Movimiento movimiento, int niveles, int jugador)
	{
		jugador = cambiar(jugador);
		if(niveles != 0){	
			Estado estadoAux = estado.copiar();
			
			List<Arbol> hijos = new ArrayList<Arbol>();
			
			Estado estadoAplicaMov = estadoAux.siguiente(movimiento);	
			
			//Esta mal tenemos que cambiar de jugador para MIN o MAX y ademas me da siempre null,
			//me tiene que dar los movimientos para el jugador
			Movimiento[] movimientos = estadoAplicaMov.movimientos(movimiento.jugador());
			
			for (Movimiento unMovimiento : movimientos) 
			{
				Arbol arbolAux = obtenerArbol(estadoAplicaMov, unMovimiento, niveles-1, jugador);
				hijos.add(arbolAux);
			}
				
			Nodo nodo = new Nodo(estado, movimiento);
			return (new Arbol(nodo, hijos, minMax(hijos,jugador)));
		}
		
		Nodo nodo = new Nodo(estado.siguiente(movimiento), movimiento);
		return (new Arbol(nodo, null, darHeuristica(estado)));
	}
	
	//Cambia valor de jugador entre 0 y 1
	private int cambiar(int jugador) {
		if(jugador==0)
		{
			return 1;
		}else
		{
			return 0;
		}
	}

	//Devuelve el menor o mayor valore de una lista de arboles, si
	//jugador == 0 - > MAX
	//jugador == 1 - > MIN
	private Double minMax(List<Arbol> hijos, int jugador) {
		double min = hijos.get(0).getValorArbol();
		double max = hijos.get(0).getValorArbol();
		
		for(Arbol arbol : hijos)
		{
			if(arbol.getValorArbol() < min)
			{ min = arbol.getValorArbol();}
			
			if(arbol.getValorArbol() > max)
			{ max = arbol.getValorArbol();}
		}
		
		if(jugador == 0){
			return max;
		}else
		{
			return min;
		}
	}

	@Override
	 public Movimiento decision(Estado estado){
		Movimiento[] movs = estado.movimientos(jugador());
		return (miniMax(estado,movs,niveles()));
	}
}
