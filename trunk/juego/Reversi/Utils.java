package juego.Reversi;

public class Utils {

	/**
	 * Indica si hay movimientos válidos en la diagonal con pendiente negativa entre las dos casillas
	 */
	public static boolean hayMovimientoEnDiagonalNegativa(Tablero tablero, Casilla casillaColocar, 
			Casilla casillaEnElTablero) {
		
		int newFila = 0;
		int newColumna = 0;
		
		for (int i = -8; i <= 8; i++) {
			
			newFila    = casillaColocar.fila()    + i + 1;
			newColumna = casillaColocar.columna() + i + 1;
			
			// Si la ficha que está en el tablero está en la misma diagonal que la casilla a colocar...
			if(newFila == casillaEnElTablero.fila() && newColumna == casillaEnElTablero.columna()) {
				
				int filaInicio = casillaEnElTablero.fila();
				int columnaInicio = casillaEnElTablero.columna();
				
				int filaFinal = casillaColocar.fila();
				
				// Agarro el que tiene el menor valor de columna
				if(casillaColocar.columna() < casillaEnElTablero.columna()){
					filaInicio = casillaColocar.fila();
					columnaInicio = casillaColocar.columna();
					
					filaFinal = casillaEnElTablero.fila();
				}
				
				int columnaActual = columnaInicio;
				
				// Si las dos casillas están una al lado de otra -> no hay movimiento posible
				if(filaInicio + 1 == filaFinal)
					return false;
				
				for(int filaActual = filaInicio + 1; filaActual < filaFinal; filaActual++){
					columnaInicio += 1;
					// Si no hay ficha de adversario -> no se puede mover
					if(! tablero.hayFichaAdversario(filaActual, columnaActual)){
						return false;
					}
				}
				
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Indica si hay movimientos válidos en la diagonal con pendiente positiva entre las dos casillas
	 */
	public static boolean hayMovimientoEnDiagonalPositiva(Tablero tablero, Casilla casillaColocar, 
			Casilla casillaEnElTablero) {
		
		int newFila = 0;
		int newColumna = 0;
		
		for (int i = -8; i <= 8; i++) {
			
			newFila    = casillaColocar.fila()    - i + 1;
			newColumna = casillaColocar.columna() + i - 1;
			
			if(newFila == casillaEnElTablero.fila() && newColumna == casillaEnElTablero.columna()) {
				
				int filaInicio = casillaEnElTablero.fila();
				int columnaInicio = casillaEnElTablero.columna();
				
				int filaFinal = casillaColocar.fila();
				
				// Agarro el que tiene el menor valor de columna
				if(casillaColocar.columna() < casillaEnElTablero.columna()){
					filaInicio = casillaColocar.fila();
					columnaInicio = casillaColocar.columna();
					
					filaFinal = casillaEnElTablero.fila();
				}
				
				int columnaActual = columnaInicio;
				
				// Si las dos casillas están una al lado de otra -> no hay movimiento posible
				if(filaInicio - 1 == filaFinal){
					return false;
				}
				
				for(int filaActual = filaInicio - 1; filaActual < filaFinal; filaActual--){
					columnaInicio += 1;
					// Si no hay ficha de adversario -> no se puede mover
					if(! tablero.hayFichaAdversario(filaActual, columnaActual)){
						return false;
					}
				}
				
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @param casillaColocar
	 * @param casillaEnElTablero
	 * @return true si estan las 2 casillas en la misma columna y puede comer
	 */
	public static boolean hayMovimientoEnColumna(Tablero tablero, Casilla casillaColocar, Casilla casillaEnElTablero) {
		
		if(casillaColocar.columna() == casillaEnElTablero.columna()) {
			int fil1, fil2;
			if(casillaColocar.fila() < casillaEnElTablero.fila()) {
				fil1 = casillaColocar.fila();
				fil2 = casillaEnElTablero.fila();
			} else {
				fil2 = casillaColocar.fila();
				fil1 = casillaEnElTablero.fila();
			}
			
			// Si las dos casillas están una al lado de otra -> no hay movimiento posible
			if(fil1 + 1 == fil2)
				return false;
			
			for (int i = fil1 + 1; i < fil2; i++) {
				if(! tablero.hayFichaAdversario(i, casillaColocar.columna())) // Si no hay ficha de adversario -> no se puede mover
					return false;
			}
				
			return true;
		}
		return false;
	}
	
	/**
	 * @param casillaColocar
	 * @param casillaEnElTablero
	 * @return True si las dos casillas estan en la misma fila y puede comer.
	 */
	public static boolean hayMovimientoEnFila(Tablero tablero, Casilla casillaColocar, Casilla casillaEnElTablero) {
		
		if(casillaColocar.fila() == casillaEnElTablero.fila()) {
			
			int col1, col2;
			if(casillaColocar.columna() < casillaEnElTablero.columna()) {
				col1 = casillaColocar.columna();
				col2 = casillaEnElTablero.columna();
			} else {
				col2 = casillaColocar.columna();
				col1 = casillaEnElTablero.columna();
			}
			
			// Si las dos casillas están una al lado de otra -> no hay movimiento posible
			if(col1 + 1 == col2)
				return false;
			
			for (int i = col1 + 1; i < col2; i++) {
				if(! tablero.hayFichaAdversario(casillaColocar.fila(), i)) // Si no hay ficha de adversario -> no se puede mover
					return false;
			}
			
			return true;
		}
		return false;
	}
}
