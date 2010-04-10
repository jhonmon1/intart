package juego.Reversi;

public class Utils {

	/**
	 * Indica si las casillas están alineadas en una diagonal con pendiente negativa
	 * @param casillaColocar
	 * @param casillaEnElTablero
	 * @return Si están en la misma diagonal con pendiente negativa
	 */
	public static boolean mismaDiagonalNegativa(Casilla casillaColocar, Casilla casillaEnElTablero) {
		
		int newFila = 0;
		int newColumna = 0;
		
		for (int i = -8; i <= 8; i++) {
			
			newFila    = casillaColocar.fila()    + i + 1;
			newColumna = casillaColocar.columna() + i + 1;
			
			// Si la ficha que está en el tablero está en la misma diagonal que la casilla a colocar...
			if(newFila == casillaEnElTablero.fila() && newColumna == casillaEnElTablero.columna())
				return true;
		}
		
		return false;
	}
	
	/**
	 * Indica si las casillas están alineadas en una diagonal con pendiente positiva
	 * @param casillaColocar
	 * @param casillaEnElTablero
	 * @return Si están en la misma diagonal con pendiente positiva
	 */
	public static boolean mismaDiagonalPositiva(Casilla casillaColocar, Casilla casillaEnElTablero) {
		
		int newFila = 0;
		int newColumna = 0;
		
		for (int i = -8; i <= 8; i++) {
			
			newFila    = casillaColocar.fila()    - i + 1;
			newColumna = casillaColocar.columna() + i - 1;
			
			// Si la ficha que está en el tablero está en la misma diagonal que la casilla a colocar...
			if(newFila == casillaEnElTablero.fila() && newColumna == casillaEnElTablero.columna())
				return true;
		}
		
		return false;
	}

	/**
	 * Indica si las casillas están alineadas en una misma columna
	 * @param casillaColocar
	 * @param casillaEnElTablero
	 * @return Si están en la misma columna
	 */
	public static boolean mismaColumna(Casilla casillaColocar, Casilla casillaEnElTablero) {
		
		// Si la ficha que está en el tablero está en la misma columna que la casilla a colocar...
		if(casillaColocar.columna() == casillaEnElTablero.columna()) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Indica si las casillas están alineadas en una misma fila
	 * @param casillaColocar
	 * @param casillaEnElTablero
	 * @return Si están en la misma fila
	 */
	public static boolean mismaFila(Casilla casillaColocar, Casilla casillaEnElTablero) {
		
		// Si la ficha que está en el tablero está en la misma fila que la casilla a colocar...
		if(casillaColocar.fila() == casillaEnElTablero.fila()) {			
			return true;
		}
		
		return false;
	}

	
}
