package juegos.base;


public abstract class _AgenteHeuristico implements Agente{

	public abstract int niveles();
	
	public abstract double darHeuristica(Estado estado);
	
	public  Movimiento miniMax(Estado estado, Movimiento[] movimientos, int niveles, int indiceJugador)
	{
		Movimiento movimientoDevolver = null;		
		double eleccion = Double.MIN_VALUE;
		double valorAux;

		Estado estadoAux;
		
		//Obtenemos todos los arboles resultados de aplicar cada uno de
		//los movimientos posibles para un estado
		for(Movimiento movimiento : movimientos){
			estadoAux = estado.copiar();
			valorAux = alfaBeta(estadoAux.siguiente(movimiento), Double.MIN_VALUE, Double.MAX_VALUE ,niveles, cambiar(indiceJugador), indiceJugador);
			if(valorAux > eleccion)
			{
				eleccion = valorAux;
				movimientoDevolver = movimiento;
			}
		}
		
		return movimientoDevolver;
	
	}
	
	
	public double alfaBeta(Estado estado, double alfa, double beta, int niveles, int jugador, int jugadorMaximiza)
	{
		if(niveles == 0 || estado.esFinal())
		{
			return darHeuristica(estado);
		}
		
		Estado estadoAux = estado.copiar();

		Jugador jugadorPasada =  obtenerJugador(estado.jugadores(), jugador);
		
		Movimiento[] movimientos = estadoAux.movimientos(jugadorPasada);
		
		//El jugador actual es el que maximiza
		if(jugador == jugadorMaximiza)
		{
			for(Movimiento movimiento : movimientos)
			{
				estadoAux = estado.copiar();
				double valor = alfaBeta(estadoAux.siguiente(movimiento), 
						alfa, beta, niveles-1,cambiar(jugador),jugadorMaximiza);
				
				if(valor>alfa)
				{ 	alfa = valor;} //Encontramos un nuevo mejor movimiento 
				
				if(alfa>= beta)
				{	return alfa; }//Hacemos la poda

			}
			
			return alfa; //Nuestro mejor movimiento

		} //El jugador actual es el que minimiza
		else
		{	for(Movimiento movimiento : movimientos)
			{
				estadoAux = estado.copiar();
				double valor = alfaBeta(estadoAux.siguiente(movimiento), 
						alfa, beta, niveles-1,cambiar(jugador),jugadorMaximiza);
				
				if(valor<beta)
				{ 	beta = valor;} //El oponente encontro un nuevo mejor movimiento para el (peor para nosotros) 
				
				if(alfa>= beta)
				{	return beta; } //Hacemos la poda

			}
			return beta; //Mejor movimiento para el oponente
		}

	}
	
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


	@Override
	 public Movimiento decision(Estado estado){
		Movimiento[] movs = estado.movimientos(jugador());
		int indiceJugador = obtenerJugador(estado.jugadores(), jugador());
		
		return miniMax(estado,movs,niveles(),indiceJugador);
	}
}
