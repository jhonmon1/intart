package juegos.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public abstract class _AgenteHeuristico implements Agente{

	public abstract int niveles();
	
	public abstract double darHeuristica(Estado estado);
	
	/**
	 * MiniMax con poda Alfa-Beta
	 * @param estado
	 * @param movimientos
	 * @param niveles
	 * @param indiceJugador
	 * @return
	 */
	public  Movimiento miniMax(Estado estado, Movimiento[] movimientos, int niveles, int indiceJugador)
	{		
		double eleccion = Double.NEGATIVE_INFINITY;
		double valorAux;
		List<Movimiento> mejoresMovimientos = new ArrayList<Movimiento>();

		Estado estadoAux;
		
		//Obtenemos todos los arboles resultados de aplicar cada uno de
		//los movimientos posibles para un estado
		for(Movimiento movimiento : movimientos){
			estadoAux = estado.copiar();
			valorAux = alfaBeta(estadoAux.siguiente(movimiento), Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY ,niveles, cambiar(indiceJugador), indiceJugador);
			
			if(valorAux == eleccion) {
				mejoresMovimientos.add(movimiento);				
			} else
				if(valorAux > eleccion) {
					mejoresMovimientos = new ArrayList<Movimiento>();
					mejoresMovimientos.add(movimiento);
					eleccion = valorAux;
			}			
		}
		
		int indice = getIndiceAleatorio(mejoresMovimientos);
		return mejoresMovimientos.get(indice);
	}
	
	/**
	 * Genera un índice aleatoriamente
	 * @param mejoresMovimientos
	 * @return
	 */
	private int getIndiceAleatorio(List<Movimiento> mejoresMovimientos) {
		int largoLista = mejoresMovimientos.size();
		Random rndm = new Random();
		int indice = rndm.nextInt(largoLista);
		return indice;
	}

	/**
	 * Implementa la poda Alfa-Beta para utilizarla en el MiniMax
	 * @param estado
	 * @param alfa
	 * @param beta
	 * @param niveles
	 * @param jugador
	 * @param jugadorMaximiza
	 * @return
	 */
	public double alfaBeta(Estado estado, double alfa, double beta, int niveles, int jugador, int jugadorMaximiza)
	{
		if(niveles == 0)
		{
			return darHeuristica(estado);
		}
		
		//Si es un estado final se devuelve el mayor valor para esta heurística posible
		if(estado.esFinal())
		{
			return maximoValorHeuristica();
		}
		
		Estado estadoAux = estado.copiar();

		Jugador jugadorPasada =  obtenerJugador(estado.jugadores(), jugador);
		
		Movimiento[] movimientos = estadoAux.movimientos(jugadorPasada);
		
		//El jugador actual es el que maximiza
		if(jugador == jugadorMaximiza) {
			if(movimientos != null){
				for(Movimiento movimiento : movimientos)
				{
					estadoAux = estado.copiar();
					double valor = alfaBeta(estadoAux.siguiente(movimiento), 
							alfa, beta, niveles-1,cambiar(jugador),jugadorMaximiza);
					
					if(valor>alfa)
					{ 	alfa = valor;} // Se encuentra un nuevo mejor movimiento para el jugador actual
					
					if(alfa>= beta)
					{	return alfa; }// Se hace la poda
	
				}
				return alfa; // Mejor movimiento para el jugador actual
			}
			
			return alfaBeta(estadoAux,alfa,beta,niveles,cambiar(jugador),jugadorMaximiza);

		  //El jugador actual es el que minimiza
		} else {
			if(movimientos != null){
				for(Movimiento movimiento : movimientos) {
					estadoAux = estado.copiar();
					double valor = alfaBeta(estadoAux.siguiente(movimiento), 
							alfa, beta, niveles-1,cambiar(jugador),jugadorMaximiza);
					
					if(valor<beta)
					{ 	beta = valor;} //El oponente encontró un nuevo mejor movimiento para él (peor para el jugador actual) 
					
					if(alfa>= beta)
					{	return beta; } //Se hace la poda
	
				}
				return beta; //Mejor movimiento para el oponente
			}	
			return alfaBeta(estadoAux,alfa,beta,niveles,cambiar(jugador),jugadorMaximiza);

		}
	}
	
	public abstract double maximoValorHeuristica();

	private Jugador obtenerJugador(Jugador[] jugadores, int jugador) {
		return jugadores[jugador];
	}
	
	private int obtenerJugador(Jugador[] jugadores, Jugador jugador) {
		int indice = 0;
		for(Jugador unJugador : jugadores)
		{
			if(unJugador.equals(jugador))
				return indice;
			indice++;
		}
		
		return -1;
	}

	/**
	 * Cambia el valor del jugador entre 0 y 1
	 * @param jugador: valor del jugador actual
	 * @return nuevo valor del jugador: jugador siguiente
	 */
	private int cambiar(int jugador) {
		if(jugador==0) {
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public Movimiento decision(Estado estado){
		Movimiento[] movs = estado.movimientos(jugador());
		int indiceJugador = obtenerJugador(estado.jugadores(), jugador());
		
		return miniMax(estado,movs,niveles(),indiceJugador);
	}
}
